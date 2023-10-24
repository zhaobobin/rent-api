package com.rent.dao.order.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.enums.order.EnumOrderRemarkSource;
import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.order.OrderRemarkDao;
import com.rent.mapper.order.OrderRemarkMapper;
import com.rent.model.order.OrderRemark;
import org.springframework.stereotype.Repository;

/**
 * OrderRemarkDao
 *
 * @author xiaoyao
 * @Date 2020-06-16 10:09
 */
@Repository
public class OrderRemarkDaoImpl extends AbstractBaseDaoImpl<OrderRemark, OrderRemarkMapper> implements OrderRemarkDao {

    @Override
    public Page<OrderRemark> pageByOrderId(Page<OrderRemark> page, String orderId, EnumOrderRemarkSource remarkSource) {
        return this.page(page, new QueryWrapper<>(OrderRemark.builder()
            .orderId(orderId)
            .source(remarkSource)
            .build()).orderByDesc("create_time"));
    }
}
