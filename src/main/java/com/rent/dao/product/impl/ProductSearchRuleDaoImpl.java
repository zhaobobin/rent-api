    
package com.rent.dao.product.impl;

import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.product.ProductSearchRuleDao;
import com.rent.mapper.product.ProductSearchRuleMapper;
import com.rent.model.product.ProductSearchRule;
import org.springframework.stereotype.Repository;

/**
 * ProductSearchRuleDao
 *
 * @author youruo
 * @Date 2021-07-26 11:47
 */
@Repository
public class ProductSearchRuleDaoImpl extends AbstractBaseDaoImpl<ProductSearchRule, ProductSearchRuleMapper> implements
    ProductSearchRuleDao {


}
