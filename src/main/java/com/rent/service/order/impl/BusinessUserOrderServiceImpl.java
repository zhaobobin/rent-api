package com.rent.service.order.impl;

import com.rent.common.converter.order.OrderSettlementConverter;
import com.rent.common.dto.backstage.BusinessOrderStaticsDto;
import com.rent.common.dto.components.dto.SendMsgDto;
import com.rent.common.dto.order.resquest.BusinessConfirmReturnReqDto;
import com.rent.common.dto.order.resquest.BusinessIssuedStatementsReqDto;
import com.rent.common.dto.product.ProductShopCateReqDto;
import com.rent.common.dto.user.UserCertificationDto;
import com.rent.common.enums.components.OrderCenterStatus;
import com.rent.common.enums.mq.OrderMsgEnum;
import com.rent.common.enums.order.*;
import com.rent.common.util.AmountUtil;
import com.rent.common.util.AsyncUtil;
import com.rent.common.util.SequenceUtil;
import com.rent.dao.order.*;
import com.rent.exception.HzsxBizException;
import com.rent.handler.ordersettle.bean.OrderSettlementBean;
import com.rent.handler.ordersettle.delegate.OrderSettlementHandleDelegate;
import com.rent.model.order.*;
import com.rent.model.product.PlatformExpress;
import com.rent.service.components.OrderCenterService;
import com.rent.service.components.SendSmsService;
import com.rent.service.order.BusinessUserOrdersService;
import com.rent.service.order.OrderOperateCore;
import com.rent.service.order.SwipingActivityOrderService;
import com.rent.service.product.PlatformExpressService;
import com.rent.service.product.ProductService;
import com.rent.service.user.UserCertificationService;
import com.rent.util.LoginUserUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * @author xiaoyao
 * @version V1.0
 * @date 2020-6-18 10:47
 * @since 1.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class BusinessUserOrderServiceImpl implements BusinessUserOrdersService {
    private final UserOrdersDao userOrdersDao;
    private final OrderSettlementDao orderSettlementDao;
    private final OrderOperateCore orderOperateCore;
    private final OrderPayDepositDao orderPayDepositDao;
    private final OrderContractDao orderContractDao;
    private final PlatformExpressService platformExpressService;
    private final UserCertificationService userCertificationService;
    private final OrderAddressDao orderAddressDao;
    private final OrderByStagesDao orderByStagesDao;
    private final ProductService productService;
    private final OrderCenterService orderCenterService;
    private final SendSmsService sendSmsService;
    private final UserOrderCashesDao userOrderCashesDao;
    private final OrderSettlementHandleDelegate orderSettlementHandleDelegate;
    private final RabbitTemplate rabbitTemplate;
    private final SwipingActivityOrderService swipingActivityOrderService;


    @Override
    public String orderDelivery(String orderId, Long expressId, String expressNo, String operatorName,BigDecimal costPrice,String serialNum) {
        UserOrders userOrders = userOrdersDao.selectOneByOrderId(orderId);
        //校验订单
        if (null == userOrders) {
            throw new HzsxBizException(EnumOrderError.ORDER_NOT_EXISTS.getCode(),EnumOrderError.ORDER_NOT_EXISTS.getMsg(), this.getClass());
        }
        if (!(EnumOrderStatus.PENDING_DEAL.equals(userOrders.getStatus())|| EnumOrderStatus.WAITING_USER_RECEIVE_CONFIRM.equals(userOrders.getStatus()))) {
            log.error("订单:{}状态错误，期望的状态是:[{}],实际状态是:[{}]", orderId, EnumOrderStatus.PENDING_DEAL.getDescription(),userOrders.getStatus() .getDescription() + "|" + EnumOrderStatus.WAITING_USER_RECEIVE_CONFIRM.getDescription());
            throw new HzsxBizException(EnumOrderError.ORDER_STATUS_ERROR.getCode(),EnumOrderError.ORDER_STATUS_ERROR.getMsg(), this.getClass());
        }
        //判断用户是否已完成实名认证
        String returnMsg = "";
        int index = 1;
        if(!swipingActivityOrderService.isHasSwipingActivity(orderId)){
            UserCertificationDto user =  userCertificationService.getByUid(userOrders.getUid());
            if(null != user){
                if(StringUtils.isEmpty(user.getIdCardBackUrl()) || StringUtils.isEmpty(user.getIdCardFrontUrl())){
                    returnMsg = index+++".用户尚未完成实名认证，存在风险,请通知用户上传身份证后，再发货\n";
                }
            }else{
                returnMsg =  index+++".用户没有实名认证信息，请通知用户补录相关信息\n";
            }
            OrderPayDeposit orderPayDeposit = orderPayDepositDao.getByOrderId(orderId);
            if(!orderPayDeposit.getStatus().equals(EnumOrderPayDepositStatus.PAID) && orderPayDeposit.getAmount().compareTo(BigDecimal.ZERO)>0){
                returnMsg = returnMsg+index+++".押金未支付\n";
            }
            OrderContract orderContract = orderContractDao.getByOrderId(orderId);
            if(orderContract==null || StringUtils.isEmpty(orderContract.getSignedPdf())){
                returnMsg = returnMsg+index+++".未签署订单合同\n";
            }
        }
        OrderByStages earliestPeriod = orderByStagesDao.getEarliestPeriod(orderId);
        if(!EnumOrderByStagesStatus.PAYED.equals(earliestPeriod.getStatus())){
            returnMsg = returnMsg+index+++".首期租金未支付\n";
        }
        if(StringUtils.isNotEmpty(returnMsg)){
            throw new HzsxBizException("-1",returnMsg);
        }
        //更新订单状态
        Date now = new Date();
        UserOrders updateOrder = UserOrders.builder()
                .id(userOrders.getId())
                .orderId(orderId)
                .updateTime(now)
                .deliveryTime(new Date())
                .expressId(expressId)
                .expressNo(expressNo)
                .costPrice(costPrice)
                .serialNumber(serialNum)
                .status(EnumOrderStatus.WAITING_USER_RECEIVE_CONFIRM)
                .build();
        userOrdersDao.updateById(updateOrder);
        //添加订单操作记录 add at 2020年10月27日13:58:41
        String backstageUserId = LoginUserUtil.getLoginUser().getId().toString();
        AsyncUtil.runAsync( () -> orderOperateCore.orderOperationRegister(userOrders.getOrderId(), userOrders.getStatus(), updateOrder.getStatus(), backstageUserId, operatorName, "商家发货"));
        PlatformExpress platformExpress = platformExpressService.queryPlatformExpressDetailById(expressId);
        ProductShopCateReqDto productShopCateReqDto = productService.selectProductCateByProductId(userOrders.getProductId());
        //商家发货短信提醒 改为异步任务
        AsyncUtil.runAsync(() -> deliverySendSms(platformExpress.getName(), productShopCateReqDto.getName(), orderId, expressId,expressNo, userOrders), "商家发货短信");
        //同步订单状态到小程序订单中心
        AsyncUtil.runAsync(() -> orderCenterService.merchantOrderSync(orderId, OrderCenterStatus.IN_DELIVERY));
        rabbitTemplate.convertAndSend(OrderMsgEnum.DELIVERY_ORDER.getExchange(),OrderMsgEnum.DELIVERY_ORDER.getRoutingKey(),orderId);
        return "已发货";
    }

    @Override
        public String checkOrderIsAuth(String orderId) {
        String returnMsg = "";
        int index = 1;
        OrderByStages earliestPeriod = orderByStagesDao.getEarliestPeriod(orderId);
        if(!EnumOrderByStagesStatus.PAYED.equals(earliestPeriod.getStatus())){
            returnMsg = returnMsg+index+++".首期租金未支付\n";
        }
        if (!swipingActivityOrderService.isHasSwipingActivity(orderId)) {
            //判断用户是否已完成实名认证
            UserOrders userOrders = userOrdersDao.selectOneByOrderId(orderId);
            UserCertificationDto user =  userCertificationService.getByUid(userOrders.getUid());
            if(null != user){
                if(StringUtils.isEmpty(user.getIdCardBackUrl()) || StringUtils.isEmpty(user.getIdCardFrontUrl())){
                    returnMsg = index+++".用户尚未完成实名认证，存在风险,请通知用户上传身份证后，再发货\n";
                }
            }else{
                returnMsg =  index+++".用户没有实名认证信息，请通知用户补录相关信息\n";
            }
            OrderPayDeposit orderPayDeposit = orderPayDepositDao.getByOrderId(orderId);
            if(!orderPayDeposit.getStatus().equals(EnumOrderPayDepositStatus.PAID) && orderPayDeposit.getAmount().compareTo(BigDecimal.ZERO)>0){
                returnMsg = returnMsg+index+++".押金未支付\n";
            }
            OrderContract orderContract = orderContractDao.getByOrderId(orderId);
            if(orderContract==null || StringUtils.isEmpty(orderContract.getSignedPdf())){
                returnMsg = returnMsg+index+++".未签署订单合同\n";
            }
        }
        return returnMsg;
    }

    /**
     * 商家发货发送短信
     * @param orderId 订单id
     * @param expressId 物流id
     * @param expressNo 物流编号
     * @param userOrders 订单信息
     */
    public void deliverySendSms(String logisticsName, String productName, String orderId, Long expressId,String expressNo, UserOrders userOrders) {
        SendMsgDto sendMsgDto = new SendMsgDto();
        sendMsgDto.setExpressNo(expressNo);
        sendMsgDto.setProductName(productName);
        sendMsgDto.setType(1);
        sendMsgDto.setLogisticsName(logisticsName);

        OrderAddress orderAddress = orderAddressDao.queryByOrderId(orderId);
        if(orderAddress.getTelephone()==null){
            UserCertificationDto userCertification = userCertificationService.getByUid(userOrders.getUid());
            sendMsgDto.setTelephone(userCertification.getTelephone());
        }else {
            sendMsgDto.setTelephone(orderAddress.getTelephone());
        }
        sendSmsService.businessDelivery(sendMsgDto);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void merchantsIssuedStatements(BusinessIssuedStatementsReqDto reqDto) {
        String orderId = reqDto.getOrderId();
        UserOrders userOrders = userOrdersDao.selectOneByOrderId(orderId);
        //校验订单
        if (null == userOrders) {
            throw new HzsxBizException(EnumOrderError.ORDER_NOT_EXISTS.getCode(),
                EnumOrderError.ORDER_NOT_EXISTS.getMsg(), this.getClass());
        }
        if (!EnumOrderStatus.WAITING_SETTLEMENT.equals(userOrders.getStatus())) {
            log.error("订单:{}状态错误，期望的状态是:[{}],实际状态是:[{}]", orderId, EnumOrderStatus.WAITING_SETTLEMENT.getDescription(),
                userOrders.getStatus()
                    .getDescription());
            throw new HzsxBizException(EnumOrderError.ORDER_STATUS_ERROR.getCode(),
                EnumOrderError.ORDER_STATUS_ERROR.getMsg(), this.getClass());
        }
        if (!EnumOrderSettlementType.INTACT.equals(reqDto.getSettlementType()) && BigDecimal.ZERO.compareTo(
            AmountUtil.safeAdd(reqDto.getPenaltyAmount(), reqDto.getDamageAmount(), reqDto.getLossAmount())) >= 0) {
            throw new HzsxBizException(EnumOrderError.SETTLEMENT_NOT_INTACT_AMOUNT_ERROR.getCode(),
                EnumOrderError.SETTLEMENT_NOT_INTACT_AMOUNT_ERROR.getMsg(), this.getClass());
        }
        //是否为修改标识
        boolean isModify = true;
        OrderSettlement orderSettlement = orderSettlementDao.selectOneByOrderId(orderId);
        if (null != orderSettlement && !EnumOrderSettlementStatus.APPLY_MODIFY.equals(orderSettlement.getSettlementStatus())) {
            throw new HzsxBizException(EnumOrderError.ORDER_SETTLEMENT_STATUS_ERROR.getCode(),
                EnumOrderError.ORDER_SETTLEMENT_STATUS_ERROR.getMsg(), this.getClass());
        }
        if (null == orderSettlement) {
            isModify = false;
            orderSettlement = OrderSettlementConverter.assemblyOrderSettlementModel(new Date(), orderId,
                reqDto.getSettlementType(), reqDto.getLossAmount(), reqDto.getDamageAmount(),
                reqDto.getPenaltyAmount());
        } else {
            orderSettlement.setUpdateTime(new Date());
            orderSettlement.setSettlementStatus(EnumOrderSettlementStatus.UNPAID);
            orderSettlement.setSettlementType(reqDto.getSettlementType());
            orderSettlement.setApplyModifyTimes(1);
            orderSettlement.setLoseAmount(reqDto.getLossAmount());
            orderSettlement.setDamageAmount(reqDto.getDamageAmount());
            orderSettlement.setPenaltyAmount(reqDto.getPenaltyAmount());
        }
        orderSettlement.setOutTrandNo(SequenceUtil.nextId());
        //处理结算数据
        OrderSettlementBean orderSettlementBean = OrderSettlementBean.builder()
            .userOrders(userOrders)
            .damageAmount(reqDto.getDamageAmount())
            .lossAmount(reqDto.getLossAmount())
            .penaltyAmount(reqDto.getPenaltyAmount())
            .settlementType(reqDto.getSettlementType())
            .isModify(isModify)
            .orderSettlement(orderSettlement)
            .build();
        //查询订单金额信息
        UserOrderCashes userOrderCashes = this.userOrderCashesDao.selectOneByOrderId(userOrders.getOrderId());
        if (userOrderCashes != null) {
            userOrderCashes.setUpdateTime(new Date());
            userOrderCashes.setPenaltyAmount(reqDto.getPenaltyAmount());
            userOrderCashes.setLostPrice(reqDto.getLossAmount());
            userOrderCashes.setDamagePrice(reqDto.getDamageAmount());
            userOrderCashes.setSettlementRent(
                AmountUtil.safeAdd(reqDto.getDamageAmount(), reqDto.getLossAmount(), reqDto.getPenaltyAmount()));
            this.userOrderCashesDao.updateById(userOrderCashes);
        }
        //添加订单操作记录 add at 2020年10月27日13:58:41
        String backstageUserId = LoginUserUtil.getLoginUser().getId().toString();
        AsyncUtil.runAsync(
            () -> orderOperateCore.orderOperationRegister(userOrders.getOrderId(), userOrders.getStatus(), userOrders.getStatus(),backstageUserId, reqDto.getOperatorName(), "商家结算"));
        orderSettlementHandleDelegate.orderSettlement(orderSettlementBean);
    }


    @Override
    public void businessConfirmReturnOrder(BusinessConfirmReturnReqDto reqDto) {
        UserOrders userOrders = userOrdersDao.selectOneByOrderId(reqDto.getOrderId());
        if (null == userOrders) {
            throw new HzsxBizException(EnumOrderError.ORDER_NOT_EXISTS.getCode(),EnumOrderError.ORDER_NOT_EXISTS.getMsg());
        }
        //todo 验证固定状态下可以商家确认收货
        if(!EnumOrderStatus.RETURN_SET.contains(userOrders.getStatus())){
            throw new HzsxBizException(EnumOrderError.ORDER_RETURN_ERROR.getCode(),EnumOrderError.ORDER_RETURN_ERROR.getMsg());
        }
        //添加订单操作记录
        String backstageUserId = LoginUserUtil.getLoginUser().getId().toString();
        AsyncUtil.runAsync(() -> orderOperateCore.orderOperationRegister(userOrders.getOrderId(),userOrders.getStatus(), EnumOrderStatus.WAITING_SETTLEMENT,
                backstageUserId, reqDto.getOperatorName(), "商家确认归还"));
        if (EnumOrderStatus.WAITING_SETTLEMENT.equals(userOrders.getStatus())) {
            userOrders.setUnrentExpressId(reqDto.getExpressId());
            userOrders.setUnrentExpressNo(reqDto.getExpressNo());
            Date now = new Date();
            userOrders.setUpdateTime(now);
            userOrders.setReturnTime(now);
            userOrdersDao.updateById(userOrders);
            return;
        }
        Date now = new Date();
        userOrders.setUpdateTime(now);
        userOrders.setReturnTime(reqDto.getReturnTime());
        userOrders.setStatus(EnumOrderStatus.WAITING_SETTLEMENT);
        userOrders.setSettlementTime(now);
        userOrders.setUnrentExpressId(reqDto.getExpressId());
        userOrders.setUnrentExpressNo(reqDto.getExpressNo());
        userOrdersDao.updateById(userOrders);
    }

    @Override
    public BusinessOrderStaticsDto businessOrderStatistics(String shopId) {
        BusinessOrderStaticsDto statisticsDto = new BusinessOrderStaticsDto();
        //查询当日订单总量
        List<UserOrders> userOrdersTotal = userOrdersDao.selectCountByDate(null, null, null, shopId);

        //待支付订单数量
        statisticsDto.setUnPayOrderCount((int) userOrdersTotal.stream()
                .filter(a -> a.getStatus()
                        .equals(EnumOrderStatus.WAITING_PAYMENT))
                .count());
        //租用中
        statisticsDto.setRentingOrderCount((int) userOrdersTotal.stream()
                .filter(a -> a.getStatus()
                        .equals(EnumOrderStatus.RENTING))
                .count());
        //待审核
        statisticsDto.setWaitingAuditOrderCount((int) userOrdersTotal.stream()
                .filter(a -> a.getStatus()
                        .equals(EnumOrderStatus.TO_AUDIT))
                .count());
        //待发货
        statisticsDto.setPendingOrderCount((int) userOrdersTotal.stream()
                .filter(a -> a.getStatus()
                        .equals(EnumOrderStatus.PENDING_DEAL))
                .count());
        //待确认收货
        statisticsDto.setWaitingConfirmOrderCount((int) userOrdersTotal.stream()
                .filter(a -> a.getStatus()
                        .equals(EnumOrderStatus.WAITING_USER_RECEIVE_CONFIRM))
                .count());
        //租用中
        statisticsDto.setWaitingConfirmOrderCount((int) userOrdersTotal.stream()
                .filter(a -> a.getStatus()
                        .equals(EnumOrderStatus.RENTING))
                .count());
        //待归还
        statisticsDto.setWaitingConfirmOrderCount((int) userOrdersTotal.stream()
                .filter(a -> a.getStatus()
                        .equals(EnumOrderStatus.TO_GIVE_BACK))
                .count());
        //待结算
        statisticsDto.setWaitingSettlementOrderCount((int) userOrdersTotal.stream()
                .filter(a -> a.getStatus()
                        .equals(EnumOrderStatus.WAITING_SETTLEMENT))
                .count());
        //已完成
        statisticsDto.setFinishOrderCount((int) userOrdersTotal.stream()
                .filter(a -> a.getStatus()
                        .equals(EnumOrderStatus.FINISH) || a.getStatus()
                        .equals(EnumOrderStatus.CLOSED))
                .count());
        return statisticsDto;
    }
}
