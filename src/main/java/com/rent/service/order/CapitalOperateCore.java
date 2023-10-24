package com.rent.service.order;

import com.rent.common.dto.components.response.AliPayTradeCreateResponse;
import com.rent.common.enums.components.EnumTradeType;
import com.rent.model.order.OrderByStages;
import com.rent.model.order.UserOrders;

import java.math.BigDecimal;
import java.util.List;

/**
 * 资金操作核心类，预授权，冻结，解冻，支付
 *
 * @author xiaoyao
 * @version V1.0
 * @date 2020-6-19 14:28
 * @since 1.0
 */
public interface CapitalOperateCore {
    /**
     * 解冻
     *
     * @param userOrders
     * @param type
     * @param remark
     * @param amount
     * @param tradeType
     */
    void alipayUnfreeze(UserOrders userOrders, Integer type, String remark, BigDecimal amount, EnumTradeType tradeType);

    /**
     * 退款
     *
     * @param orderByStages
     * @param userOrders
     * @param remark
     * @param amount
     * @param tradeType
     * @param uid
     */
    void alipayTradeRefund(OrderByStages orderByStages, UserOrders userOrders, String remark, BigDecimal amount,
                           EnumTradeType tradeType, String uid);

    /**
     * app支付接口
     *
     * @param tradeType
     * @param subject
     * @param orderId
     * @param outTradeNo
     * @param totalAmount
     * @param uid
     * @param hbPeriodNum 花呗分期数，不分期传null
     * @param productId   商品id
     * @return
     */
    AliPayTradeCreateResponse alipayTradeCreate(EnumTradeType tradeType, List<String> periodList, String subject, String orderId,
                                                String outTradeNo, BigDecimal totalAmount, String uid, String hbPeriodNum, Boolean shopPayHbFee,
                                                String productId);
}
