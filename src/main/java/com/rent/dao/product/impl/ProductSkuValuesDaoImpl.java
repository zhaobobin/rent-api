    
package com.rent.dao.product.impl;

import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.product.ProductSkuValuesDao;
import com.rent.mapper.product.ProductSkuValuesMapper;
import com.rent.model.product.ProductSkuValues;
import org.springframework.stereotype.Repository;

/**
 * ProductSkuValuesDao
 *
 * @author youruo
 * @Date 2020-06-16 15:28
 */
@Repository
public class ProductSkuValuesDaoImpl extends AbstractBaseDaoImpl<ProductSkuValues, ProductSkuValuesMapper> implements
    ProductSkuValuesDao {

}
