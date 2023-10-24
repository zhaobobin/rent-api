package com.rent.dao.order.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Maps;
import com.rent.common.enums.order.EnumOrderRemarkSource;
import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.order.OrderHastenDao;
import com.rent.mapper.order.OrderHastenMapper;
import com.rent.model.order.OrderHasten;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * OrderHastenDao
 *
 * @author
 * @Date
 */
@Repository
public class OrderHastenDaoImpl extends AbstractBaseDaoImpl<OrderHasten, OrderHastenMapper> implements OrderHastenDao {

    @Override
    public Page<OrderHasten>pageByOrderId(Page<OrderHasten>page, String orderId, EnumOrderRemarkSource source){
        return this.page(page, new QueryWrapper<>(OrderHasten.builder()
            .orderId(orderId)
            .source(source)
            .build()).orderByDesc("create_time"));

    }

    @Override
    public Map<String,List<OrderHasten>> queryListByOrderIds(List<String> orderIdList) {
        List<OrderHasten> hastenList = this.list(new QueryWrapper<>(new OrderHasten()).in(CollectionUtil.isNotEmpty(orderIdList), "order_id",
                orderIdList));
        if (CollectionUtil.isEmpty(hastenList)) {
            return Maps.newHashMap();
        }
        return hastenList.stream().collect(Collectors.groupingBy(OrderHasten::getOrderId));
    }

}
