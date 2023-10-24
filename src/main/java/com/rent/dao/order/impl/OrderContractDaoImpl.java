package com.rent.dao.order.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.order.OrderContractDao;
import com.rent.mapper.order.OrderContractMapper;
import com.rent.model.order.OrderContract;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * OrderContractDao
 *
 * @author zhao
 * @Date 2020-11-09 15:41
 */
@Repository
public class OrderContractDaoImpl extends AbstractBaseDaoImpl<OrderContract, OrderContractMapper> implements OrderContractDao {


    @Override
    public OrderContract getByOrderId(String orderId) {
        return getOne(new QueryWrapper<OrderContract>().eq("order_id", orderId));
    }

    @Override
    public void saveOrUpdateContract(OrderContract orderContract) {
        Date now = new Date();
        if(orderContract.getId()==null){
            orderContract.setCreateTime(now);
            orderContract.setUpdateTime(now);
            save(orderContract);
        }else {
            orderContract.setUpdateTime(new Date());
            updateById(orderContract);
        }
    }
}