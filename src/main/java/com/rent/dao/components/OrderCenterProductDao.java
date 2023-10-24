package com.rent.dao.components;

import com.rent.config.mybatis.IBaseDao;
import com.rent.model.components.OrderCenterProduct;

/**
 * OrderCenterProductDao
 *
 * @author xiaoyao
 * @Date 2020-06-24 16:12
 */
public interface OrderCenterProductDao extends IBaseDao<OrderCenterProduct> {

    /**
     * 根据商品ID查询同步过的商品信息
     * @param productId
     * @return
     */
    OrderCenterProduct getByProductId(String productId);

}
