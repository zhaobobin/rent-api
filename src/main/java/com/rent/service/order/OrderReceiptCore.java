package com.rent.service.order;


import com.rent.model.order.UserOrders;

import java.util.Date;

/**
 * @author zhaowenchao
 */
public interface OrderReceiptCore {

    /**
     * 订单确认收货
     * @param orderId
     * @param confirmDate
     * @param operatorId
     * @param operatorName
     */
    void confirmReceipt(String orderId, Date confirmDate, String operatorId, String operatorName);

    /**
     * 用户自提
     * @param orderId
     * @param operatorName
     */
    void pickUp(String orderId,String operatorName);

    /**
     * 定时任务确认收货
     * @param userOrders
     * @param confirmDate
     */
    void taskConfirm(UserOrders userOrders, Date confirmDate);

    /**
     * 用户确认收货
     * @param orderId
     */
    void userConfirm(String orderId);

    /**
     * 刷单商品自动确认收货
     * @param userOrders
     */
    void swipingAutoConfirm(UserOrders userOrders);
}
