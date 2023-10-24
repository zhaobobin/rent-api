package com.rent.service.order.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.cache.product.ProductSalesCache;
import com.rent.common.constant.RedisKey;
import com.rent.common.converter.order.ChannelUserOrdersConverter;
import com.rent.common.converter.order.OrderByStagesConverter;
import com.rent.common.converter.order.UserOrdersConverter;
import com.rent.common.dto.backstage.request.AddEmergencyContactReq;
import com.rent.common.dto.components.dto.SendMsgDto;
import com.rent.common.dto.components.response.AliPayFreezeResponse;
import com.rent.common.dto.mq.OrderDeadMessage;
import com.rent.common.dto.order.*;
import com.rent.common.dto.order.response.OrderCloseResponse;
import com.rent.common.dto.order.response.OrderFreezeAgainResponse;
import com.rent.common.dto.order.resquest.ChannelUserOrdersReqDto;
import com.rent.common.dto.order.resquest.OrderGiveBackRequest;
import com.rent.common.dto.product.ChannelSplitBillDto;
import com.rent.common.dto.product.OrderProductDetailDto;
import com.rent.common.dto.product.ShopDto;
import com.rent.common.dto.user.UserEmergencyContactDto;
import com.rent.common.enums.components.EnumAliPayStatus;
import com.rent.common.enums.components.OrderCenterStatus;
import com.rent.common.enums.mq.EnumOrderDeadOperate;
import com.rent.common.enums.mq.OrderMsgEnum;
import com.rent.common.enums.order.*;
import com.rent.common.enums.product.ProductStatus;
import com.rent.common.util.*;
import com.rent.dao.order.*;
import com.rent.exception.HzsxBizException;
import com.rent.model.order.*;
import com.rent.service.components.AliPayCapitalService;
import com.rent.service.components.OrderCenterService;
import com.rent.service.components.SendSmsService;
import com.rent.service.order.*;
import com.rent.service.product.ChannelSplitBillService;
import com.rent.service.product.ShopService;
import com.rent.service.user.UserEmergencyContactService;
import com.rent.util.DateUtil;
import com.rent.util.RedisUtil;
import com.rent.util.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserOrdersServiceImpl implements UserOrdersService {

    private final UserOrdersDao userOrdersDao;
    private final UserOrderCashesDao userOrderCashesDao;
    private final OrderCloseCore orderCloseCore;
    private final OrderByStagesService orderByStagesService;
    private final UserOrderItemsDao userOrderItemsDao;
    private final OrderAddressDao orderAddressDao;
    private final OrderAuditService orderAuditService;
    private final OrderOperateCore orderOperateCore;
    private final AliPayCapitalService aliPayCapitalService;
    private final OrderCenterService orderCenterService;
    private final UserOrdersLiteService userOrdersLiteService;
    private final ShopService shopService;
    private final SendSmsService sendSmsService;
    private final OrderPayDepositDao orderPayDepositDao;
    private final RabbitTemplate rabbitTemplate;
    private final ChannelUserOrdersDao channelUserOrdersDao;
    private final ChannelSplitBillService channelSplitBillService;
    private final SwipingActivityOrderService swipingActivityOrderService;
    private final OSSFileUtils ossFileUtils;

    private final UserEmergencyContactService userEmergencyContactService;

    @Override
    public UserOrdersDto queryUserOrdersDetail(UserOrdersReqDto request) {
        UserOrders userOrders = userOrdersDao.getOne(
                new QueryWrapper<>(UserOrdersConverter.reqDto2Model(request)).last("limit 1"));
        return UserOrdersConverter.model2Dto(userOrders);
    }

    @Override
    public Boolean addEmergencyContact(AddEmergencyContactReq request) {
        UserOrders userOrders = userOrdersDao.getOne(
                new QueryWrapper<UserOrders>().eq("order_id", request.getOrderId()).last("limit 1"));
        if (Objects.isNull(userOrders)) {
            throw new HzsxBizException("-1", "订单不存在");
        }
        UserEmergencyContactDto dto = new UserEmergencyContactDto();
        dto.setName(request.getName());
        dto.setMobile(request.getMobile());
        dto.setRelationship(request.getRelationship());
        return userEmergencyContactService.addUserEmergencyContact(dto, userOrders.getUid());
    }

    @Override
    public OrderFreezeAgainResponse liteOrderFreezeAgain(String orderId, Long templateId) {
        log.info("订单:{}开始进行重新预授权", orderId);
        String FREEZE_AGAIN_LOCK_PREFIX = "order:freeze:again:lock:";
        try {
            if (RedisUtil.hasKey(RedisKey.ORDER_CHANGE_PRICE_LOCK_KEY + orderId)) {
                throw new HzsxBizException("-1", "订单正在改价,请稍后重试");
            }

            if (!RedisUtil.tryLock(FREEZE_AGAIN_LOCK_PREFIX + orderId, 30)) {
                throw new HzsxBizException("-1", "不要重复提交");
            }
            // TODO bug 当用户在支付页支付

            UserOrders userOrders = userOrdersDao.selectOneByOrderId(orderId);
            OrderFreezeAgainResponse response = new OrderFreezeAgainResponse();
            UserOrderCashes userOrderCashes = userOrderCashesDao.getOne(new QueryWrapper<>(UserOrderCashes.builder()
                    .orderId(orderId)
                    .build()));
            UserOrderItems userOrderItems = userOrderItemsDao.selectOneByOrderId(orderId);

            AliPayFreezeResponse aliPayFreezeResponse = aliPayCapitalService.aliPayFreeze(orderId, userOrders.getUid(),
                    userOrderCashes.getDeposit(), userOrderItems.getSkuId(), userOrders.getProductId(), 1,
                    userOrders.getType(), userOrderCashes.getTotalRent(), userOrders.getRentDuration());
            response.setFreezeAgainUrl(aliPayFreezeResponse.getFreezeUrl());
            response.setSerialNo(aliPayFreezeResponse.getSerialNo());
            response.setFreeze(Boolean.TRUE);
            return response;
        } finally {
            RedisUtil.unLock(FREEZE_AGAIN_LOCK_PREFIX + orderId);
        }
    }

    @Override
    public OrderCloseResponse closeOrder(String orderId, EnumOrderCloseType closeType, String cancelReason) {
        //查询校验订单
        UserOrders userOrders = userOrdersDao.selectOneByOrderId(orderId);
        OrderCheckUtil.checkUserOrdersStatus(userOrders, EnumOrderStatus.WAITING_PAYMENT, "关闭订单");
        //关闭订单
        orderCloseCore.closeOrderCommon(userOrders, EnumOrderStatus.CLOSED, closeType, cancelReason);
        return OrderCloseResponse.builder()
                .orderId(orderId)
                .build();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void userOrdersPaying(String orderId, String payerUserId) {
        UserOrders userOrders = userOrdersDao.selectOneByOrderId(orderId);
        if (null == userOrders) {
            throw new HzsxBizException(EnumOrderError.ORDER_NOT_EXISTS.getCode(),
                    EnumOrderError.ORDER_NOT_EXISTS.getMsg(), this.getClass());
        }
        if (!EnumOrderStatus.WAITING_PAYMENT.equals(userOrders.getStatus())) {
            log.error("订单:{}状态错误，期望的状态是:[{}],实际状态是:[{}]", orderId, EnumOrderStatus.WAITING_PAYMENT.getDescription(),
                    userOrders.getStatus()
                            .getDescription());
            throw new HzsxBizException(EnumOrderError.ORDER_STATUS_ERROR.getCode(),
                    EnumOrderError.ORDER_STATUS_ERROR.getMsg(), this.getClass());
        }
        userOrders.setStatus(EnumOrderStatus.PAYING);
        userOrders.setPayerUserId(payerUserId);
        userOrders.setUpdateTime(new Date());
        userOrdersDao.updateByOrderId(userOrders);
    }

    @Override
    public void payedCloseOrder(String orderId, EnumOrderCloseType closeType, String cancelReason) {
        //查询订单
        UserOrders userOrders = userOrdersDao.selectOneByOrderId(orderId);
        //校验订单
        if (null == userOrders) {
            throw new HzsxBizException(EnumOrderError.ORDER_NOT_EXISTS.getCode(),
                    EnumOrderError.ORDER_NOT_EXISTS.getMsg());
        }
        if (!EnumOrderStatus.TO_AUDIT.equals(userOrders.getStatus()) && !EnumOrderStatus.PENDING_DEAL.equals(
                userOrders.getStatus())) {
            log.error("订单:{}状态错误，期望的状态是:[{}],实际状态是:[{}]", userOrders.getOrderId(),
                    EnumOrderStatus.TO_AUDIT.getDescription() + "||" + EnumOrderStatus.PENDING_DEAL.getDescription(),
                    userOrders.getStatus()
                            .getDescription());
            throw new HzsxBizException(EnumOrderError.ORDER_STATUS_NOT_ALLOW_APPLY.getCode(), "订单当前状态不允许进行[已支付取消订单]操作");
        }

        if (EnumOrderCloseType.PAYED_USER_APPLY.equals(closeType)) {
            Date payTime = null == userOrders.getPaymentTime() ? userOrders.getCreateTime() :
                    userOrders.getPaymentTime();
            Boolean rentCredit = userOrders.getRentCredit();
            if (RedisUtil.hHasKey("SKIP_CERT", userOrders.getProductId())) {
                rentCredit = Boolean.TRUE;
            }
            Boolean isShowCancelButton = this.getShowCancelButton(payTime, userOrders.getStatus(), rentCredit);
            if (isShowCancelButton) {
                throw new HzsxBizException(EnumOrderError.ORDER_REFUND_TIME_OUT.getCode(),
                        EnumOrderError.ORDER_REFUND_TIME_OUT.getMsg(), this.getClass());
            }
        }
        //关闭订单
        orderCloseCore.payedCloseOrder(userOrders, closeType, cancelReason);
    }

    @Override
    @Transactional
    public void paySuccessUserOrderHandle(EnumOrderType orderType, String orderId, String tradeNo, Date paymentTime,
                                          String outTradeNo, BigDecimal totalAmount, String payerUserId, PaymentMethod paymentMethod) {
        this.payedGeneralOrderSuccess(orderId, tradeNo, paymentTime, outTradeNo, totalAmount, payerUserId, paymentMethod);
    }

    /**
     * 常规订单已支付处理
     *
     * @param orderId     订单编号
     * @param tradeNo     交易号
     * @param paymentTime 支付时间
     * @param outTradeNo  商户订单号
     * @param totalAmount 总金额
     * @param payerUserId 支付方id
     */
    private void payedGeneralOrderSuccess(String orderId, String tradeNo, Date paymentTime, String outTradeNo, BigDecimal totalAmount, String payerUserId, PaymentMethod paymentMethod) {
        PaymentMethod method = Optional.ofNullable(paymentMethod).orElse(PaymentMethod.ZFB);

        //查询订单
        UserOrders userOrders = userOrdersDao.selectOneByOrderId(orderId);
        //修改订单状态
        userOrders.setPaymentNo(tradeNo);
        userOrders.setPaymentTime(paymentTime);
        userOrders.setStatus(EnumOrderStatus.TO_AUDIT);
        userOrders.setUpdateTime(new Date());
        userOrders.setPayerUserId(payerUserId);
        userOrdersDao.updateById(userOrders);
        //修改订单状态
        if (StringUtil.isNotEmpty(tradeNo)) {
            orderByStagesService.payedOrderByStages(orderId, tradeNo, outTradeNo, Collections.singletonList("1"), totalAmount, EnumAliPayStatus.SUCCESS, method);
        }

        //增加待审核记录
        orderAuditService.initAuditRecord(orderId);
        UserOrderCashes userOrderCashes = userOrderCashesDao.selectOneByOrderId(orderId);
        BigDecimal toPayDeposit = BigDecimal.ZERO;
        orderPayDepositDao.initDepositRecord(orderId, userOrderCashes.getTotalDeposit(), toPayDeposit, userOrders.getChannelId(), userOrders.getUid());
        //订单操作节点
        AsyncUtil.runAsync(() -> orderOperateCore.orderOperationRegister(orderId, EnumOrderStatus.WAITING_PAYMENT,
                EnumOrderStatus.TO_AUDIT, userOrders.getUid(), userOrders.getUserName(), "买家支付"));
        AsyncUtil.runAsync(() -> orderCenterService.merchantOrderSync(orderId, OrderCenterStatus.APPROVAL));
        //3分钟后发支付成功短信
        rabbitTemplate.convertAndSend(OrderMsgEnum.SMS_SUCCESS.getExchange(), OrderMsgEnum.SMS_SUCCESS.getRoutingKey(), new OrderDeadMessage(orderId, EnumOrderDeadOperate.ORDER_SMS_SUCCESS));
    }

    @Override
    public void payFailedCloseOrder(String orderId, EnumOrderCloseType closeType, String cancelReason) {
        //查询订单
        UserOrders userOrders = userOrdersDao.selectOneByOrderId(orderId);
        //校验订单
        if (null == userOrders) {
            throw new HzsxBizException(EnumOrderError.ORDER_NOT_EXISTS.getCode(), EnumOrderError.ORDER_NOT_EXISTS.getMsg());
        }
        if (!EnumOrderStatus.PAYING.equals(userOrders.getStatus())) {
            log.error("订单:{}状态错误，期望的状态是:[{}],实际状态是:[{}]", userOrders.getOrderId(), EnumOrderStatus.PAYING.getDescription(), userOrders.getStatus().getDescription());
            throw new HzsxBizException(EnumOrderError.ORDER_STATUS_NOT_ALLOW_APPLY.getCode(), EnumOrderError.ORDER_STATUS_NOT_ALLOW_APPLY.getMsg());
        }
        //关闭订单
        orderCloseCore.payedCloseOrder(userOrders, closeType, cancelReason);
    }

    @Override
    public void userOrderBackSubmitConfirm(OrderGiveBackRequest request) {
        UserOrders userOrders = userOrdersDao.selectOneByOrderId(request.getOrderId());
        if (null == userOrders) {
            throw new HzsxBizException(EnumOrderError.ORDER_NOT_EXISTS.getCode(), EnumOrderError.ORDER_NOT_EXISTS.getMsg());
        }
        String orderId = request.getOrderId();
        //归还规则校验
        OrderProductDetailDto productDetailDto = userOrdersLiteService.selectOrderProductDetail(Collections.singletonList(orderId)).get(orderId);
        if (null != productDetailDto) {
            Integer returnRule = productDetailDto.getReturnRule();
            if (ProductStatus.RETURN_RULE_MATURE.getCode().equals(returnRule)) {
                if (null == userOrders.getUnrentTime()) {
                    throw new HzsxBizException(EnumOrderError.BUY_OUT_UNRENT_ERROR.getCode(), EnumOrderError.BUY_OUT_UNRENT_ERROR.getMsg());
                }
                Boolean isBefore = DateUtil.isBefore(DateUtil.dateStr4(userOrders.getUnrentTime()));
                if (!isBefore) {
                    throw new HzsxBizException(EnumOrderError.ORDER_RETURN_RULE_ERROR.getCode(), EnumOrderError.ORDER_RETURN_RULE_ERROR.getMsg());
                }
            }

        }

        //添加订单操作记录 add at 2020年10月27日13:58:41
        AsyncUtil.runAsync(
                () -> orderOperateCore.orderOperationRegister(userOrders.getOrderId(), userOrders.getStatus(),
                        userOrders.getStatus(), userOrders.getUid(), userOrders.getUserName(), "买家归还"));
        if (EnumOrderStatus.WAITING_SETTLEMENT.equals(userOrders.getStatus())) {
            userOrders.setUnrentExpressId(request.getExpressId());
            userOrders.setUnrentExpressNo(request.getExpressNo());
            Date now = new Date();
            userOrders.setUpdateTime(now);
            userOrders.setReturnTime(now);
            userOrders.setGiveBackAddressId(request.getAddressId());
            userOrdersDao.updateById(userOrders);
            return;
        }

        Date now = new Date();
        userOrders.setUpdateTime(now);
        userOrders.setReturnTime(now);
        userOrders.setStatus(EnumOrderStatus.WAITING_SETTLEMENT);
        userOrders.setSettlementTime(now);
        userOrders.setGiveBackAddressId(request.getAddressId());
        userOrders.setUnrentExpressId(request.getExpressId());
        userOrders.setUnrentExpressNo(request.getExpressNo());
        userOrders.setGiveBackRemark(request.getRemark());
        userOrdersDao.updateById(userOrders);
        AsyncUtil.runAsync(() -> orderCenterService.merchantOrderSync(userOrders.getOrderId(), OrderCenterStatus.IN_THE_BACK));
    }

    @Override
    public Integer getProductSales(String productId) {
        Integer sales = userOrdersDao.getProductSales(productId);
        ProductSalesCache.setProductSales(productId, sales);
        return sales;
    }

    @Override
    public void smsSuccess(String orderId, String shopId) {
        String lockKey = "smsSuccess:" + orderId;
        if (!RedisUtil.setNx(lockKey, 60)) {
            log.warn("【支付成功短信发送】已有任务执行 orderId:{}", orderId);
            return;
        }
        //订单地址
        OrderAddress orderAddress = orderAddressDao.getOne(new QueryWrapper<>(OrderAddress.builder()
                .orderId(orderId)
                .build()));
        //发送短信（异步任务)
        ShopDto shopDto = shopService.queryByShopId(shopId);
        SendMsgDto sendMsgDto = new SendMsgDto();
        sendMsgDto.setTelephone(orderAddress.getTelephone());
        sendMsgDto.setOrderId(orderId);
        sendMsgDto.setShopServiceTel(shopDto.getServiceTel());
        sendSmsService.payedOrder(sendMsgDto);
    }

    public Boolean getShowCancelButton(Date paymentTime, EnumOrderStatus status, Boolean rentCredit) {
        Boolean isShowCancelButton = Boolean.FALSE;
        switch (status) {
            case TO_AUDIT:
            case PENDING_DEAL:
                if (null != rentCredit && rentCredit && DateUtil.compare(DateUtil.addDateMinut(paymentTime, 5),
                        DateUtil.getNowDate()) < 0) {
                    isShowCancelButton = Boolean.TRUE;
                }
                if (null == rentCredit) {
                    isShowCancelButton = Boolean.TRUE;
                }
                break;
            case PAYING:
            case WAITING_USER_RECEIVE_CONFIRM:
            case RENTING:
            case TO_GIVE_BACK:
            case WAITING_SETTLEMENT:
            case WAITING_SETTLEMENT_PAYMENT:
            case FINISH:
                isShowCancelButton = Boolean.TRUE;
            default:
                break;

        }
        return isShowCancelButton;
    }

    @Override
    public Page<ChannelUserOrdersDto> queryChannelUserOrdersPage(ChannelUserOrdersReqDto request) {
        List<String> orderIdList = new ArrayList<>();
        if (request.getStatus() != null) {
            List<UserOrders> list = userOrdersDao.list(new QueryWrapper<UserOrders>().select("order_id").eq("status", request.getStatus()));
            orderIdList = list.stream().map(UserOrders::getOrderId).collect(Collectors.toList());
        }
        List<String> nameList = new ArrayList<>();
        if (StringUtils.isNotEmpty(request.getName())) {
            nameList = channelSplitBillService.getUidList(request.getName());
            if (CollectionUtil.isEmpty(nameList)) {
                return new Page<>(request.getPageNumber(), request.getPageSize());
            }
        }
        Page<ChannelUserOrders> page = channelUserOrdersDao.page(new Page<>(request.getPageNumber(), request.getPageSize()),
                new QueryWrapper<ChannelUserOrders>()
                        .eq(StringUtil.isNotEmpty(request.getMarketingChannelId()), "marketing_channel_id", request.getMarketingChannelId())
                        .eq(StringUtil.isNotEmpty(request.getChannelName()), "channel_name", request.getChannelName())
                        .in(request.getStatus() != null, "order_id", orderIdList)
                        .in(CollectionUtil.isNotEmpty(nameList), "marketing_channel_id", nameList)
                        .between(request.getTimeBegin() != null, "create_time", request.getTimeBegin(), request.getTimeEnd())
                        .orderByDesc("create_time")
        );
        List<ChannelUserOrdersDto> records = ChannelUserOrdersConverter.modelList2DtoList(page.getRecords());
        if (CollectionUtil.isNotEmpty(records)) {
            records = records.stream().map(item -> {
                        List<OrderByStagesDto> orderByStagesDtoList = orderByStagesService.queryOrderByStagesByOrderId(item.getOrderId());
                        BigDecimal payedRent = BigDecimal.ZERO;
                        int payedPeriods = 0;
                        List<OrderByStages> orderByStages = OrderByStagesConverter.dtoList2ModelList(orderByStagesDtoList);
                        if (CollectionUtil.isNotEmpty(orderByStages)) {
                            for (OrderByStages stage1 : orderByStages) {
                                if (EnumOrderByStagesStatus.PAYED.equals(stage1.getStatus())
                                        || EnumOrderByStagesStatus.PAYED.equals(stage1.getStatus())
                                ) {
                                    payedRent = AmountUtil.safeAdd(payedRent, stage1.getCurrentPeriodsRent());
                                    payedPeriods++;
                                }
                            }
                        }
                        ChannelUserOrders channelUserOrders = channelUserOrdersDao.getByOrderId(item.getOrderId());
                        UserOrders userOrders = userOrdersDao.selectOneByOrderId(item.getOrderId());
                        ChannelSplitBillDto channelSplitBillDto = channelSplitBillService.getOne(channelUserOrders.getMarketingChannelId());
                        item.setTotalRent(channelUserOrders.getTotalAmount());
                        if (StringUtils.isEmpty(request.getMarketingChannelId())) {
                            item.setShopName(channelUserOrders.getShopName());
                        }
                        item.setStatus(userOrders.getStatus().getCode());
                        item.setCurrentPeriods(payedPeriods);
                        item.setPayRent(payedRent);
                        item.setSettleAmount(payedRent.multiply(channelSplitBillDto.getScale()));
                        item.setChannelName(channelSplitBillDto.getName());
                        return item;
                    }
            ).collect(Collectors.toList());
        }
        return new Page<ChannelUserOrdersDto>(page.getCurrent(), page.getSize(), page.getTotal()).setRecords(records);
    }


    @Override
    public String exportChannelUserOrdersPage(ChannelUserOrdersReqDto request) {
        List<String> orderIdList = new ArrayList<>();
        if (request.getStatus() != null) {
            List<UserOrders> list = userOrdersDao.list(new QueryWrapper<UserOrders>().select("order_id").eq("status", request.getStatus()));
            orderIdList = list.stream().map(UserOrders::getOrderId).collect(Collectors.toList());
        }
        List<ChannelUserOrders> channelUserOrdersDtos = channelUserOrdersDao.list(
                new QueryWrapper<ChannelUserOrders>()
                        .eq(StringUtil.isNotEmpty(request.getMarketingChannelId()), "marketing_channel_id", request.getMarketingChannelId())
                        .eq(StringUtil.isNotEmpty(request.getChannelName()), "channel_name", request.getChannelName())
                        .in(request.getStatus() != null, "order_id", orderIdList)
                        .between(request.getTimeBegin() != null, "create_time", request.getTimeBegin(), request.getTimeEnd())
                        .orderByDesc("create_time")
        );
        List<ChannelOrdersExportDto> dtoList = new ArrayList<>();
        for (ChannelUserOrders channelUserOrders : channelUserOrdersDtos) {
            ChannelOrdersExportDto channelOrdersExportDto = new ChannelOrdersExportDto();
            channelOrdersExportDto.setOrderId(channelUserOrders.getOrderId());
            ChannelSplitBillDto channelSplitBillDto = channelSplitBillService.getOne(channelUserOrders.getMarketingChannelId());
            channelOrdersExportDto.setChannelName(channelSplitBillDto.getName());
            channelOrdersExportDto.setProductName(channelUserOrders.getProductName());
            List<OrderByStagesDto> orderByStagesDtoList = orderByStagesService.queryOrderByStagesByOrderId(channelUserOrders.getOrderId());
            BigDecimal payedRent = BigDecimal.ZERO;
            int payedPeriods = 0;
            List<OrderByStages> orderByStages = OrderByStagesConverter.dtoList2ModelList(orderByStagesDtoList);
            if (CollectionUtil.isNotEmpty(orderByStages)) {
                for (OrderByStages stage1 : orderByStages) {
                    if (EnumOrderByStagesStatus.PAYED.equals(stage1.getStatus())
                            || EnumOrderByStagesStatus.PAYED.equals(stage1.getStatus())
                    ) {
                        payedRent = AmountUtil.safeAdd(payedRent, stage1.getCurrentPeriodsRent());
                        payedPeriods++;
                    }
                }
            }
            channelOrdersExportDto.setCurrentPeriods(payedPeriods);
            channelOrdersExportDto.setTotalPeriods(channelUserOrders.getTotalPeriods());
            channelOrdersExportDto.setTotalRent(channelUserOrders.getTotalAmount());
            channelOrdersExportDto.setPayRent(payedRent);
            channelOrdersExportDto.setUserName(channelUserOrders.getUserName());
            channelOrdersExportDto.setTelephone(channelUserOrders.getPhone());
            channelOrdersExportDto.setCreateTime(channelUserOrders.getCreateTime());
            channelOrdersExportDto.setStatus(channelUserOrders.getStatus().getDescription());
            channelOrdersExportDto.setSettleAmount(payedRent.multiply(channelSplitBillDto.getScale()));
            dtoList.add(channelOrdersExportDto);
        }
        String fileName = SequenceTool.nextId() + ".xls";
        String savePath = System.getProperty("user.dir") + File.separator + "temp" + File.separator + fileName;
        EasyExcel.write(savePath, ChannelOrdersExportDto.class).sheet("渠道订单").doWrite(dtoList);
        return ossFileUtils.uploadExportFile("export", savePath, fileName);
    }
}