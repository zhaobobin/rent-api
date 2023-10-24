package com.rent.common.constant;

/**
 * @author zhaowenchao
 */
public class QueuesConst {

    /**
     * 过期订单队列，ttl= 1800 000 ms，过期进入orderDead死信队列
     */
    public static final String orderExpiration = "order.expiration";
    /**
     * 过期订单队列，ttl= 180 000 ms，过期进入orderDead死信队列
     */
    public static final String orderSmsSuccess = "order.smsSuccess";

    /**
     * 用户提交订单
     */
    public static final String orderSubmit = "order.submit";

    /**
     * 商家发货
     */
    public static final String orderDelivery = "order.delivery";

    /**
     * 死信队列
     */
    public static final String orderDead = "order.dead";


}
