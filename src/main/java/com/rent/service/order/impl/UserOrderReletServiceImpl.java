package com.rent.service.order.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Lists;
import com.rent.common.dto.components.dto.SendMsgDto;
import com.rent.common.dto.components.response.AliPayFreezeResponse;
import com.rent.common.dto.mq.OrderDeadMessage;
import com.rent.common.dto.order.OrderByStagesDto;
import com.rent.common.dto.order.OrderPricesDto;
import com.rent.common.dto.order.ReletCyclePricesDto;
import com.rent.common.dto.order.UserOrderReletSubmitRequest;
import com.rent.common.dto.order.response.OrderReletConfirmResponse;
import com.rent.common.dto.order.response.OrderReletSubmitResponse;
import com.rent.common.dto.order.response.UserOrderReletPageResponse;
import com.rent.common.dto.product.OrderProductDetailDto;
import com.rent.common.dto.product.ProductAdditionalServicesDto;
import com.rent.common.dto.product.ShopAdditionalServicesDto;
import com.rent.common.dto.product.ShopDto;
import com.rent.common.dto.user.UserCertificationDto;
import com.rent.common.enums.components.EnumAliPayStatus;
import com.rent.common.enums.mq.EnumOrderDeadOperate;
import com.rent.common.enums.mq.OrderMsgEnum;
import com.rent.common.enums.order.*;
import com.rent.common.util.*;
import com.rent.dao.order.OrderByStagesDao;
import com.rent.dao.order.UserOrderItemsDao;
import com.rent.dao.order.UserOrdersDao;
import com.rent.dao.user.UserDao;
import com.rent.exception.HzsxBizException;
import com.rent.model.order.OrderByStages;
import com.rent.model.order.UserOrderItems;
import com.rent.model.order.UserOrders;
import com.rent.model.user.User;
import com.rent.service.components.AliPayCapitalService;
import com.rent.service.components.SendSmsService;
import com.rent.service.order.*;
import com.rent.service.product.ProductAdditionalServicesService;
import com.rent.service.product.ShopService;
import com.rent.service.user.UserCertificationService;
import com.rent.util.DateUtil;
import com.rent.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author xiaoyao
 * @version V1.0
 * @date 2020-8-10 下午 2:51:50
 * @since 1.0
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserOrderReletServiceImpl implements UserOrderReletService {

    private final UserOrdersDao userOrdersDao;
    private final UserOrderItemsDao userOrderItemsDao;
    private final UserOrdersQueryService userOrdersQueryService;
    private final OrderByStagesDao orderByStagesDao;
    private final UserDao userDao;
    private final ProductAdditionalServicesService productAdditionalServicesService;
    private final OrderRepayPlanFactory orderRepayPlanFactory;
    private final UserCertificationService userCertificationService;
    private final OrderSubmitCore orderSubmitCore;
    private final ShopService shopService;
    private final OrderOperateCore orderOperateCore;
    private final OrderByStagesService orderByStagesService;
    private final OrderFinishCore orderFinishCore;
    private final SendSmsService sendSmsService;
    private final AliPayCapitalService aliPayCapitalService;
    private final RabbitTemplate rabbitTemplate;

    @Override
    public UserOrderReletPageResponse userOrderReletPage(String orderId) {
        //校验订单状态
        UserOrders userOrders = userOrdersDao.selectOneByOrderId(orderId);
        this.checkOriginalOrder(orderId, userOrders);
        UserOrderItems userOrderItems = userOrderItemsDao.selectOneByOrderId(orderId);
        String originalOrderId = EnumOrderType.GENERAL_ORDER.equals(userOrders.getType()) ? userOrders.getOrderId() : userOrders.getOriginalOrderId();
        //查询商品信息
        OrderProductDetailDto detailDto = userOrdersQueryService.selectOrderProductDetail(Collections.singletonList(originalOrderId)).get(originalOrderId);
        if (CollectionUtil.isEmpty(detailDto.getCyclePrices())) {
            throw new HzsxBizException("99999", "未查询到商品信息");
        }
        List<ReletCyclePricesDto> cyclePricesDtoList = Lists.newArrayList();
        detailDto.getCyclePrices()
            .forEach(a -> {
                ReletCyclePricesDto cyclePricesDto = new ReletCyclePricesDto();
                cyclePricesDto.setDays(a.getDays());
                cyclePricesDto.setPrice(a.getPrice());
                cyclePricesDto.setTotalPrice(AmountUtil.safeMultiply(new BigDecimal(a.getDays()), a.getPrice()));
                cyclePricesDtoList.add(cyclePricesDto);
            });
        //返回出参
        UserOrderReletPageResponse response = new UserOrderReletPageResponse();
        response.setOrderId(orderId);
        response.setRentStartDate(DateUtil.dateAddDay(userOrders.getUnrentTime(), 1));
        response.setProductId(userOrderItems.getProductId());
        response.setSkuId(userOrderItems.getSkuId());
        response.setOriginalOrderId(orderId);
        response.setMainImageUrl(detailDto.getMainImageUrl());
        response.setProductName(detailDto.getProductName());
        response.setSkuTitle(detailDto.getSkuTitle());
        response.setReletCyclePricesDtoList(cyclePricesDtoList);
        return response;
    }


    @Override
    public OrderReletConfirmResponse userOrderReletConfirm(String originalOrderId, Integer duration, Long skuId,
                                                           String uid, BigDecimal price, List<String> additionalServicesIds) {
        String rentOrderId = null;
        UserOrders userOrders = userOrdersDao.selectOneByOrderId(originalOrderId);
        if (EnumOrderType.GENERAL_ORDER.equals(userOrders.getType())) {
            rentOrderId = userOrders.getOrderId();
        } else if (EnumOrderType.RELET_ORDER.equals(userOrders.getType())) {
            rentOrderId = userOrders.getOriginalOrderId();
        }
        List<OrderByStages> orderByStagesList = this.checkOriginalOrder(originalOrderId, userOrders);
        //生成订单id
        String orderId = SequenceUtil.getTypeSerialNo(EnumSerialModalName.RELET_ORDER_ID);
        OrderReletConfirmResponse reletConfirmResponse = new OrderReletConfirmResponse();

        //需要返回实名认证状态
        User user = userDao.getUserByUid(uid);
        if (null == user) {
            throw new HzsxBizException(EnumOrderError.USER_NOT_EXISTS.getCode(),EnumOrderError.USER_NOT_EXISTS.getMsg(), this.getClass());
        }

        //查询产品详情
        OrderProductDetailDto detailDto = userOrdersQueryService.selectOrderProductDetail(Collections.singletonList(originalOrderId)).get(originalOrderId);
        //计算租期
        Date start = DateUtil.addDate(userOrders.getUnrentTime(), 1);
        Date end = DateUtil.addDate(userOrders.getUnrentTime(), duration);

        //查询安心服务列表
        List<ShopAdditionalServicesDto> additionalServicesDtoList = Lists.newArrayList();
        if (CollectionUtil.isNotEmpty(additionalServicesIds)) {
            List<ProductAdditionalServicesDto> productAdditionalServicesDtos = productAdditionalServicesService.queryProductAdditionalServicesByProductId(userOrders.getProductId());
            if (CollectionUtil.isEmpty(productAdditionalServicesDtos)) {
                throw new HzsxBizException("99999", "所选商品无增值服务", this.getClass());
            }
            additionalServicesDtoList = productAdditionalServicesDtos.stream()
                    .filter(a -> additionalServicesIds.contains(a.getShopAdditionalServicesId().toString()))
                    .map(ProductAdditionalServicesDto::getShopAdditionalServices)
                    .collect(Collectors.toList());
        }

        //计算账单信息 押金
        OrderPricesDto orderRepayPlan = orderRepayPlanFactory.createOrderRepayPlan(duration, start, end,
                BigDecimal.ZERO, additionalServicesDtoList, 1, price, null, detailDto.getMarketPrice(),null, Boolean.TRUE);
        //组装商品相关，商品信息，是否支持买断，sku信息
        if (detailDto.getBuyOutSupport() > 0) {
            //累计支付租金 todo
            BigDecimal payedRent = orderByStagesList.stream()
                    .filter(a -> EnumOrderByStagesStatus.OVERDUE_PAYED.equals(a.getStatus()) || EnumOrderByStagesStatus.PAYED.equals(a.getStatus()))
                    .map(OrderByStages::getCurrentPeriodsRent)
                    .reduce(BigDecimal::add)
                    .orElse(BigDecimal.ZERO);
            //使用租期 取原订单与续租订单较长租期的销售价
            int useDuration = userOrders.getRentDuration() > duration ? userOrders.getRentDuration() : duration;
            BigDecimal salePrice = detailDto.getCyclePrices()
                    .stream()
                    .filter(a -> a.getDays().equals(useDuration))
                    .findFirst()
                    .orElseThrow(() -> new HzsxBizException("99999", "未查询到租期信息"))
                    .getSalePrice();
            BigDecimal totolRent = orderRepayPlan.getTotalRent();
            //如果为续租订单则需要算之前订单的总租金
            if(orderId.contains(EnumSerialModalName.RELET_ORDER_ID.getCode())){
                BigDecimal totolAllRent = userOrdersDao.getOrderTotolRent(orderId);
                totolRent = totolRent.add(null != totolAllRent ? totolAllRent:BigDecimal.ZERO);
            }
            //买断价格计算
            BigDecimal buyOutPrice = salePrice.subtract(payedRent).subtract(totolRent);
            BigDecimal buyOutAmount = buyOutPrice.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : buyOutPrice;
            //计算可0元买断期数
            BigDecimal zeroBuyOutPrice = salePrice.subtract(payedRent);
            for (OrderByStagesDto orderByStagesDto : orderRepayPlan.getOrderByStagesDtoList()) {
                zeroBuyOutPrice = zeroBuyOutPrice.subtract(orderByStagesDto.getCurrentPeriodsRent());
                if (zeroBuyOutPrice.compareTo(BigDecimal.ZERO) < 0) {
                    reletConfirmResponse.setFreeBuyOutPeriod(orderByStagesDto.getCurrentPeriods());
                    break;
                }
            }
            reletConfirmResponse.setExpireBuyOutPrice(buyOutAmount);
        }
        //返回参数
        reletConfirmResponse.setIsBuyOutSupported(detailDto.getBuyOutSupport() > 0);
        reletConfirmResponse.setStart(DateUtil.date2String(start, DateUtil.DATE_FORMAT_1));
        reletConfirmResponse.setEnd(DateUtil.date2String(end, DateUtil.DATE_FORMAT_1));
        reletConfirmResponse.setDuration(duration);
        reletConfirmResponse.setOrderPricesDto(orderRepayPlan);
        reletConfirmResponse.setUserName(userOrders.getUserName());
        reletConfirmResponse.setRealNameStatus(user.getIsAuth());
        reletConfirmResponse.setOrderId(orderId);
        reletConfirmResponse.setAdditionalServicesDtoList(additionalServicesDtoList);
        reletConfirmResponse.setOriginalOrderId(originalOrderId);
        reletConfirmResponse.setDetailDto(detailDto);
        return reletConfirmResponse;
    }

    @Override
    public OrderReletSubmitResponse liteUserOrderReletSubmit(UserOrderReletSubmitRequest request) {
        //防止订单重复提交的判断
        String jedisOrderIdKey = "orderReletSubmitLock:" + request.getOrderId();
        Object jedisOrderIdRslt = RedisUtil.get(jedisOrderIdKey);
        if (jedisOrderIdRslt != null) {
            log.warn("ordersSubmit.submit orderId exist orderId =" + request.getOrderId());
            throw new HzsxBizException(EnumOrderError.ORDER_IDEMPOTENT_EXCEPTION.getCode(),
                    EnumOrderError.ORDER_IDEMPOTENT_EXCEPTION.getMsg(), this.getClass());
        }
        RedisUtil.set(jedisOrderIdKey, request.getOrderId());
        RedisUtil.expire(jedisOrderIdKey, 60 * 30);
        //校验订单是否已经存在
        if (userOrdersDao.existsWithOrderId(request.getOrderId())) {
            log.error("ordersSubmit orderId exist orderId =" + request.getOrderId());
            throw new HzsxBizException(EnumOrderError.ORDER_EXISTS.getCode(), EnumOrderError.ORDER_EXISTS.getMsg(), this.getClass());
        }
        //校验原订单信息
        String rentOrderId = null;
        UserOrders originalUserOrders = userOrdersDao.selectOneByOrderId(request.getOriginalOrderId());
        if (EnumOrderType.GENERAL_ORDER.equals(originalUserOrders.getType())) {
            rentOrderId = originalUserOrders.getOrderId();
        } else if (EnumOrderType.RELET_ORDER.equals(originalUserOrders.getType())) {
            rentOrderId = originalUserOrders.getOriginalOrderId();
        }
        this.checkOriginalOrder(request.getOriginalOrderId(), originalUserOrders);
        //查询租赁订单
        UserOrders rentUserOrders = userOrdersDao.selectOneByOrderId(rentOrderId);

        //查询产品详情
        OrderProductDetailDto detailDto = userOrdersQueryService.selectOrderProductDetail(
                        Collections.singletonList(rentOrderId))
                .get(rentOrderId);
        //查询店铺信息
        ShopDto shopDto = shopService.queryByShopId(detailDto.getShopId());
        //需要返回实名认证状态
        User userDto = userDao.getUserByUid(request.getUid());
        UserCertificationDto userCertificationDto = userCertificationService.getByUid(request.getUid());

        // 获得增值服务id
        log.info("提交订单的安心服务:{} ", GsonUtil.objectToJsonString(request.getAdditionalServicesIds()));
        //查询安心服务列表
        List<ShopAdditionalServicesDto> additionalServicesDtoList = Lists.newArrayList();
        if (CollectionUtil.isNotEmpty(request.getAdditionalServicesIds())) {
            List<ProductAdditionalServicesDto> productAdditionalServicesDtos = productAdditionalServicesService.queryProductAdditionalServicesByProductId(request.getProductId());
            if (CollectionUtil.isEmpty(productAdditionalServicesDtos)) {
                throw new HzsxBizException("99999", "所选商品无增值服务", this.getClass());
            }
            additionalServicesDtoList = productAdditionalServicesDtos.stream()
                    .filter(a -> request.getAdditionalServicesIds()
                            .contains(a.getShopAdditionalServicesId()
                                    .toString()))
                    .map(ProductAdditionalServicesDto::getShopAdditionalServices)
                    .collect(Collectors.toList());
        }

        //计算账单信息
        OrderPricesDto orderPricesDto = orderRepayPlanFactory.createOrderRepayPlan(request.getDuration(),
                request.getStart(), request.getEnd(), BigDecimal.ZERO, additionalServicesDtoList, request.getNum(),
                request.getPrice(), null, detailDto.getMarketPrice(),null,Boolean.TRUE);

        //快照信息--续租快照取最开始订单商品快照
        Integer snapshotsId = userOrdersDao.getSnapShotIdOrder(request.getOriginalOrderId());
        // 保存订单数据
        orderSubmitCore.saveReletOrderData(shopDto, snapshotsId, additionalServicesDtoList, request,orderPricesDto, userCertificationDto, rentUserOrders);

        OrderReletSubmitResponse orderSubmitResponse = new OrderReletSubmitResponse();
        AliPayFreezeResponse aliPayFreezeResponse = aliPayCapitalService.aliPayFreeze(request.getOrderId(),
                request.getUid(), orderPricesDto.getDeposit(), request.getSkuId(), detailDto.getProductId(), 0,
                EnumOrderType.RELET_ORDER, orderPricesDto.getTotalRent(), request.getDuration());
        orderSubmitResponse.setFreezeUrl(aliPayFreezeResponse.getFreezeUrl());
        orderSubmitResponse.setSerialNo(aliPayFreezeResponse.getSerialNo());
        //过期时间修改为1800s
        rabbitTemplate.convertAndSend(OrderMsgEnum.EXPIRATION.getExchange(),OrderMsgEnum.EXPIRATION.getRoutingKey(),new OrderDeadMessage(request.getOrderId(), EnumOrderDeadOperate.EXPIRATION));
        rabbitTemplate.convertAndSend(OrderMsgEnum.SUBMIT_ORDER.getExchange(),OrderMsgEnum.SUBMIT_ORDER.getRoutingKey(),request.getOrderId());
        return orderSubmitResponse;
    }

    @Override
    public void payedReletOrderSuccess(String orderId, String tradeNo, Date paymentTime, String outTradeNo,
                                       BigDecimal totalAmount, String payerUserId, PaymentMethod paymentMethod) {
        PaymentMethod method = Optional.ofNullable(paymentMethod).orElse(PaymentMethod.ZFB);
        //查询订单
        UserOrders userOrders = userOrdersDao.selectOneByOrderId(orderId);
        OrderCheckUtil.checkUserOrdersStatus(userOrders, EnumOrderStatus.PAYING, "支付成功回调");
        //修改订单状态
        userOrders.setPaymentNo(tradeNo);
        userOrders.setPaymentTime(paymentTime);
        userOrders.setStatus(EnumOrderStatus.RENTING);
        userOrders.setUpdateTime(new Date());
        userOrders.setPayerUserId(payerUserId);
        userOrdersDao.updateById(userOrders);
        AsyncUtil.runAsync( () -> orderOperateCore.orderOperationRegister(userOrders.getOrderId(), EnumOrderStatus.WAITING_PAYMENT,EnumOrderStatus.RENTING, userOrders.getUid(), userOrders.getUserName(), "买家续租支付"));
        //修改订单状态
        orderByStagesService.payedOrderByStages(orderId, tradeNo, outTradeNo, Collections.singletonList("1"), totalAmount, EnumAliPayStatus.SUCCESS, method);
        //添加订单操作记录 add at 2020年10月27日13:58:41
        AsyncUtil.runAsync( () -> orderOperateCore.orderOperationRegister(userOrders.getOriginalOrderId(), EnumOrderStatus.RENTING, EnumOrderStatus.RENTING, userOrders.getUid(), userOrders.getUserName(), "买家续租支付"));
        //原订单完结
        //订单完成处理
        UserOrders originalUserOrder = userOrdersDao.selectOneByOrderId(userOrders.getOriginalOrderId());
        orderFinishCore.orderFinishCommHandle(originalUserOrder);
        //发送短信
        AsyncUtil.runAsync(() -> {
            UserCertificationDto userCertificationDto = userCertificationService.getByUid(userOrders.getUid());
            SendMsgDto sendMsgDto = new SendMsgDto();
            sendMsgDto.setShopServiceTel(RedisUtil.get("zuyouji:sysConfig:CONSUMER_HOTLINE").toString());
            sendMsgDto.setTelephone(userCertificationDto.getTelephone());
            sendSmsService.payedReletOrder(sendMsgDto);

            ShopDto shopDto = shopService.queryByShopId(userOrders.getShopId());
            SendMsgDto payedReletBusiness = new SendMsgDto();
            payedReletBusiness.setUserName(userCertificationDto.getUserName());
            payedReletBusiness.setOrderId(userOrders.getOriginalOrderId());
            payedReletBusiness.setReletOrderId(userOrders.getOrderId());
            payedReletBusiness.setTelephone(shopDto.getUserTel());
            sendSmsService.payedReletBusiness(payedReletBusiness);
        });
    }

    /**
     * 校验源订单状态
     *
     * @param orderId
     * @param userOrders
     */
    public List<OrderByStages> checkOriginalOrder(String orderId, UserOrders userOrders) {
        OrderCheckUtil.checkUserOrdersStatus(userOrders, EnumOrderStatus.RENTING, "续租");
        List<OrderByStages> orderByStagesList = orderByStagesDao.queryAllOrderByOrderId(orderId);
        //未支付账单
        List<OrderByStages> notPayList = orderByStagesList.stream()
            .filter(a -> !EnumOrderByStagesStatus.PAYED.equals(a.getStatus())&& !EnumOrderByStagesStatus.OVERDUE_PAYED.equals(a.getStatus()))
            .collect(Collectors.toList());
        if (CollectionUtil.isNotEmpty(notPayList)) {
            throw new HzsxBizException(EnumOrderError.ORDER_NOT_SETTLE.getCode(),EnumOrderError.ORDER_NOT_SETTLE.getMsg(), this.getClass());
        }
        return orderByStagesList;
    }
}
