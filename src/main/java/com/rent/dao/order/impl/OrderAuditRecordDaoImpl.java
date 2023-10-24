    
package com.rent.dao.order.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.order.OrderAuditRecordDao;
import com.rent.mapper.order.OrderAuditRecordMapper;
import com.rent.model.order.OrderAuditRecord;
import org.springframework.stereotype.Repository;

/**
 * OrderAuditRecordDao
 *
 * @author xiaoyao
 * @Date 2020-10-22 10:29
 */
@Repository
public class OrderAuditRecordDaoImpl extends AbstractBaseDaoImpl<OrderAuditRecord, OrderAuditRecordMapper> implements OrderAuditRecordDao{


    @Override
    public OrderAuditRecord getOrderAuditRecordByOrderId(String orderId) {
        return this.getOne(new QueryWrapper<>(new OrderAuditRecord()).eq("order_id", orderId).last("limit 1"));
    }

    @Override
    public OrderAuditRecord getMinAssignedAuditUser(String type, String shopId) {
        return baseMapper.getMinAssignedAuditUser(type,shopId);
    }
}
