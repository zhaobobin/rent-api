package com.rent.common.enums.mq;

import com.rent.common.constant.QueuesConst;
import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * @author zhaowenchao
 */
@Getter
@AllArgsConstructor
public enum OrderMsgEnum {


    EXPIRATION("order_exchange", QueuesConst.orderExpiration, QueuesConst.orderExpiration, "用户下单，超时支付关单队列"),
    SMS_SUCCESS("order_exchange", QueuesConst.orderSmsSuccess, QueuesConst.orderSmsSuccess, "用户下单，支付成功3分钟后短信"),
    SUBMIT_ORDER("order_exchange", QueuesConst.orderSubmit, QueuesConst.orderSubmit, "用户下单，生成订单合同"),
    DELIVERY_ORDER("order_exchange", QueuesConst.orderDelivery, QueuesConst.orderDelivery, "商家发货，更新订单合同");;

    public String exchange;

    public String routingKey;

    public String queenName;

    public String describe;


}
