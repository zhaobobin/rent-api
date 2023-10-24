package com.rent.dao.order.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.order.OrderAuditUserDao;
import com.rent.mapper.order.OrderAuditUserMapper;
import com.rent.model.order.OrderAuditUser;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class OrderAuditUserDaoImpl extends AbstractBaseDaoImpl<OrderAuditUser, OrderAuditUserMapper> implements OrderAuditUserDao {


    @Override
    public OrderAuditUser getByOrderId(String orderId) {
        return getOne(new QueryWrapper<OrderAuditUser>().eq("order_id", orderId));
    }

    @Override
    public List<OrderAuditUser> getOrderByBackstageUserId(Long backstageUserId) {
        return list(new QueryWrapper<OrderAuditUser>().eq("backstage_user_id", backstageUserId));
    }
}
