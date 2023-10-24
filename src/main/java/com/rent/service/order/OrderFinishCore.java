package com.rent.service.order;

import com.rent.model.order.UserOrders;

/**
 * @author xiaoyao
 * @version V1.0
 * @date 2020-6-22 11:46
 * @since 1.0
 */
public interface OrderFinishCore {
    /**
     * 订单完成共用处理
     *
     * @param userOrders 订单信息
     */
    void orderFinishCommHandle(UserOrders userOrders);
}
