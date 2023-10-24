package com.rent.service.order;

import com.rent.common.enums.order.EnumOrderCloseType;
import com.rent.common.enums.order.EnumOrderStatus;
import com.rent.model.order.UserOrders;

/**
 * @author xiaoyao
 * @version V1.0
 * @date 2020-6-17 14:56
 * @since 1.0
 */
public interface OrderCloseCore {

    /**
     * 关闭订单通用逻辑
     *  @param userOrders 订单编号
     * @param status 订单状态
     * @param orderCloseType 关单类型
     * @param cancelReason 取消原因
     */
    void closeOrderCommon(UserOrders userOrders, EnumOrderStatus status, EnumOrderCloseType orderCloseType,
        String cancelReason);

    /**
     * 已支付关闭订单
     *
     * @param userOrders 订单信息
     * @param closeType 关单类型
     * @param cancelReason 取消原因
     */
    void payedCloseOrder(UserOrders userOrders, EnumOrderCloseType closeType, String cancelReason);
}
