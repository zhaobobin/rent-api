    
package com.rent.dao.product.impl;


import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.product.CategoryDao;
import com.rent.mapper.product.CategoryMapper;
import com.rent.model.product.Category;
import org.springframework.stereotype.Repository;

/**
 * CategoryDao
 *
 * @author youruo
 * @Date 2020-06-15 10:56
 */
@Repository
public class CategoryDaoImpl extends AbstractBaseDaoImpl<Category, CategoryMapper> implements CategoryDao {


}
