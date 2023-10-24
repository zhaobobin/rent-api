package com.rent.dao.order.impl;

import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.order.OrderShopCloseDao;
import com.rent.mapper.order.OrderShopCloseMapper;
import com.rent.model.order.OrderShopClose;
import org.springframework.stereotype.Repository;

/**
 * OrderShopCloseDao
 *
 * @author xiaoyao
 * @Date 2020-06-17 16:54
 */
@Repository
public class OrderShopCloseDaoImpl extends AbstractBaseDaoImpl<OrderShopClose, OrderShopCloseMapper>
    implements OrderShopCloseDao {

}
