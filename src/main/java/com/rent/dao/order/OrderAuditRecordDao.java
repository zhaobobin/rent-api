        
package com.rent.dao.order;

import com.rent.config.mybatis.IBaseDao;
import com.rent.model.order.OrderAuditRecord;

/**
 * OrderAuditRecordDao
 *
 * @author xiaoyao
 * @Date 2020-10-22 10:29
 */
public interface OrderAuditRecordDao extends IBaseDao<OrderAuditRecord> {


    OrderAuditRecord getOrderAuditRecordByOrderId(String orderId);

    /**
     * 获取分配审核单子最少的审核用户ID
     * @param type
     * @param shopId
     * @return
     */
    OrderAuditRecord getMinAssignedAuditUser(String type, String shopId);

}
