        
package com.rent.dao.product;


import com.rent.config.mybatis.IBaseDao;
import com.rent.model.product.OpeCategory;

/**
 * OpeCategoryDao
 *
 * @author youruo
 * @Date 2020-06-15 11:07
 */
public interface OpeCategoryDao extends IBaseDao<OpeCategory> {

    Integer getOneCategoryId(Integer categoryId);


}
