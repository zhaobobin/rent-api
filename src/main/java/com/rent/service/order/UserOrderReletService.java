package com.rent.service.order;

import com.rent.common.dto.order.UserOrderReletSubmitRequest;
import com.rent.common.dto.order.response.OrderReletConfirmResponse;
import com.rent.common.dto.order.response.OrderReletSubmitResponse;
import com.rent.common.dto.order.response.UserOrderReletPageResponse;
import com.rent.common.enums.order.PaymentMethod;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author xiaoyao
 * @version V1.0
 * @date 2020-8-10 上午 11:56:42
 * @since 1.0
 */
public interface UserOrderReletService {

    /**
     * 根据订单号查询续租信息
     * @param orderId 订单id
     * @return 续租信息
     */
    UserOrderReletPageResponse userOrderReletPage(String orderId);

    /**
     * 续租订单确认
     * @param originalOrderId 原订单号
     * @param duration 租期
     * @param skuId skuId
     * @param uid
     * @param price
     * @param additionalServicesIds
     * @return
     */
    OrderReletConfirmResponse userOrderReletConfirm(String originalOrderId, Integer duration, Long skuId, String uid,
                                                    BigDecimal price, List<String> additionalServicesIds);

    /**
     *
     * @param request
     * @return
     */
    OrderReletSubmitResponse liteUserOrderReletSubmit(UserOrderReletSubmitRequest request);

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
    void payedReletOrderSuccess(String orderId, String tradeNo, Date paymentTime, String outTradeNo, BigDecimal totalAmount, String payerUserId, PaymentMethod paymentMethod);


}
