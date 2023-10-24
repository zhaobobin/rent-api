    
package com.rent.dao.order.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.order.OrderLocationAddressDao;
import com.rent.mapper.order.OrderLocationAddressMapper;
import com.rent.model.order.OrderLocationAddress;
import org.springframework.stereotype.Repository;

/**
 * OrderLocationAddressDao
 *
 * @author youruo
 * @Date 2021-01-14 15:15
 */
@Repository
public class OrderLocationAddressDaoImpl extends AbstractBaseDaoImpl<OrderLocationAddress, OrderLocationAddressMapper> implements OrderLocationAddressDao{


    @Override
    public OrderLocationAddress getByOrderId(String orderId) {
        return getOne(new QueryWrapper<OrderLocationAddress>().eq("order_id",orderId));
    }
}
