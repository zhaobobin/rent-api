package com.rent.service.order;

import com.rent.common.dto.backstage.OrderByStagesForValetDto;
import com.rent.common.dto.order.OrderByStagesDto;
import com.rent.common.dto.order.response.OrderByStagesPayResponse;
import com.rent.common.dto.order.resquest.OrderByStagesPayRequest;
import com.rent.common.enums.components.EnumAliPayStatus;
import com.rent.common.enums.order.PaymentMethod;
import com.rent.model.order.OrderByStages;

import java.math.BigDecimal;
import java.util.List;

/**
 * 用户订单分期Service
 *
 * @author xiaoyao
 * @Date 2020-06-11 17:25
 */
public interface OrderByStagesService {

    /**
     * 分期订单支付
     *
     * @param orderId     订单号
     * @param tradeNo     支付宝交易号
     * @param outTradeNo  商户订单号
     * @param period      期数
     * @param totalAmount 金额
     * @param status      z支付状态
     */
    void payedOrderByStages(String orderId, String tradeNo, String outTradeNo, List<String> period,
                            BigDecimal totalAmount, EnumAliPayStatus status, PaymentMethod paymentMethod);

    /**
     * 查看代客支付凭证
     * @param orderId
     * @param currentPeriod
     * @return
     */
    OrderByStagesForValetDto selectValetPayment(String orderId, Integer currentPeriod);

    /**
     * 查询订单分期账单
     *
     * @param orderId 订单编号
     * @return
     */
    List<OrderByStagesDto> queryOrderByStagesByOrderId(String orderId);

    /**
     * 分期账单主动支付
     * @param request
     * @return
     */
    OrderByStagesPayResponse liteOrderByStagesPay(OrderByStagesPayRequest request);

    /**
     * 获取到最早的一期
     * @param orderId
     * @return
     */
    OrderByStages getEarliestPeriod(String orderId);
}