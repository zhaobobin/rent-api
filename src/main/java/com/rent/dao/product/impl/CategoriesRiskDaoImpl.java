package com.rent.dao.product.impl;

import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.product.CategoriesRiskDao;
import com.rent.mapper.product.CategoriesRiskMapper;
import com.rent.model.product.CategoriesRisk;
import org.springframework.stereotype.Repository;

/**
 * CategoriesRiskDao
 *
 * @author youruo
 * @Date 2020-06-16 14:11
 */
@Repository
public class CategoriesRiskDaoImpl extends AbstractBaseDaoImpl<CategoriesRisk, CategoriesRiskMapper>
    implements CategoriesRiskDao {

}
