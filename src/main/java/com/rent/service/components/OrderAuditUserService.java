package com.rent.service.components;

import com.rent.model.order.OrderAuditUser;

/**
 * 订单审核人员
 */
public interface OrderAuditUserService {

    /**
     * 选择审核人员
     * @param shopId
     * @param orderId
     * @return
     */
    OrderAuditUser selectAuditUser(String shopId, String orderId);

}
