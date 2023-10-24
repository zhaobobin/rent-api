package com.rent.dao.order.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.order.UserOrderItemsDao;
import com.rent.mapper.order.UserOrderItemsMapper;
import com.rent.model.order.UserOrderItems;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * UserOrderItemsDao
 *
 * @author xiaoyao
 * @Date 2020-06-16 10:10
 */
@Repository
public class UserOrderItemsDaoImpl extends AbstractBaseDaoImpl<UserOrderItems, UserOrderItemsMapper>
    implements UserOrderItemsDao {

    @Override
    public UserOrderItems selectOneByOrderId(String orderId) {
        return this.getOne(new QueryWrapper<>(UserOrderItems.builder()
            .orderId(orderId)
            .build()).orderByDesc("id").last("limit 1"));

    }

    @Override
    public List<UserOrderItems> queryListByOrderIds(List<String> orderIdList) {
        QueryWrapper<UserOrderItems> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("order_id", orderIdList);
        return list(queryWrapper);
    }

    @Override
    public List<String> queryOrderIdsByProductIdList(List<String> productIds) {
        List<UserOrderItems> userOrderItemsList = list(new QueryWrapper<UserOrderItems>().select("order_id").in("product_id",productIds));
        return userOrderItemsList.stream().map(UserOrderItems::getOrderId).collect(Collectors.toList());
    }
}
