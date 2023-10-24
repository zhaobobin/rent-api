package com.rent.service.order;

import com.rent.common.enums.order.EnumOrderStatus;

/**
 * @author xiaoyao
 * @version V1.0
 * @date 2020-10-27 下午 1:47:27
 * @since 1.0
 */
public interface OrderOperateCore {

    /**
     * 订单操作记录
     * @param orderId 订单号
     * @param oldStatus 原状态
     * @param newStatus 新状态
     * @param operatorId 操作人姓名
     * @param operatorName 操作人姓名
     * @param operate 操作
     */
    void orderOperationRegister(String orderId, EnumOrderStatus oldStatus, EnumOrderStatus newStatus, String operatorId,
        String operatorName, String operate);

}
