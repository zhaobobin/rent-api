    
package com.rent.dao.product.impl;

import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.product.ProductCyclePricesDao;
import com.rent.mapper.product.ProductCyclePricesMapper;
import com.rent.model.product.ProductCyclePrices;
import org.springframework.stereotype.Repository;

/**
 * ProductCyclePricesDao
 *
 * @author youruo
 * @Date 2020-06-16 15:08
 */
@Repository
public class ProductCyclePricesDaoImpl extends AbstractBaseDaoImpl<ProductCyclePrices, ProductCyclePricesMapper> implements
    ProductCyclePricesDao {


}
