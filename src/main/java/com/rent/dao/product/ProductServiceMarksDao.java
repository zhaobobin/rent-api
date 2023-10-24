        
package com.rent.dao.product;

import com.rent.config.mybatis.IBaseDao;
import com.rent.model.product.ProductServiceMarks;

/**
 * ProductServiceMarksDao
 *
 * @author youruo
 * @Date 2020-06-22 10:39
 */
public interface ProductServiceMarksDao extends IBaseDao<ProductServiceMarks> {

    String getServiceMark(String itemId);

}
