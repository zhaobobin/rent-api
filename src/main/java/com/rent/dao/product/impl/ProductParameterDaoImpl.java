    
package com.rent.dao.product.impl;

import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.product.ProductParameterDao;
import com.rent.mapper.product.ProductParameterMapper;
import com.rent.model.product.ProductParameter;
import org.springframework.stereotype.Repository;

/**
 * ProductParameterDao
 *
 * @author youruo
 * @Date 2020-06-29 18:33
 */
@Repository
public class ProductParameterDaoImpl extends AbstractBaseDaoImpl<ProductParameter, ProductParameterMapper> implements
    ProductParameterDao {


}
