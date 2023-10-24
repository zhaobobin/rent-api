package com.rent.dao.components.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.components.OrderCenterProductDao;
import com.rent.mapper.components.OrderCenterProductMapper;
import com.rent.model.components.OrderCenterProduct;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * OrderCenterProductDao
 *
 * @author xiaoyao
 * @Date 2020-06-24 16:12
 */
@Repository
public class OrderCenterProductDaoImpl extends AbstractBaseDaoImpl<OrderCenterProduct, OrderCenterProductMapper> implements OrderCenterProductDao {

    @Override
    public OrderCenterProduct getByProductId(String productId) {
        List<OrderCenterProduct> orderCenterProductList = list(new QueryWrapper<OrderCenterProduct>().eq("product_id",productId).orderByDesc("id"));
        if(CollectionUtil.isNotEmpty(orderCenterProductList)){
            return orderCenterProductList.get(0);
        }
        return null;
    }
}
