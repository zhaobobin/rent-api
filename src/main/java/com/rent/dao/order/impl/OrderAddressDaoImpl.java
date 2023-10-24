package com.rent.dao.order.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.order.OrderAddressDao;
import com.rent.mapper.order.OrderAddressMapper;
import com.rent.model.order.OrderAddress;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * OrderAddressDao
 *
 * @author xiaoyao
 * @Date 2020-06-16 10:09
 */
@Repository
public class OrderAddressDaoImpl extends AbstractBaseDaoImpl<OrderAddress, OrderAddressMapper>
    implements OrderAddressDao {

    @Override
    public OrderAddress queryByOrderId(String orderId) {
        return getOne(new QueryWrapper<>(OrderAddress.builder()
            .orderId(orderId)
            .build()));
    }

    @Override
    public List<OrderAddress> queryByOrderIds(Collection<String> orderIds) {
        return list(new QueryWrapper<OrderAddress>().in("order_id",orderIds));
    }

    @Override
    public List<String> getOrderIdByTelephone(String addressUserPhone) {
        return list(new QueryWrapper<OrderAddress>().in("telephone",addressUserPhone))
                .stream().map(OrderAddress::getOrderId).collect(Collectors.toList());
    }
}
