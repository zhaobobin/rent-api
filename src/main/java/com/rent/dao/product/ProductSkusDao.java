        
package com.rent.dao.product;

import com.rent.config.mybatis.IBaseDao;
import com.rent.model.product.ProductSkus;

import java.util.List;
import java.util.Map;

/**
 * ProductSkusDao
 *
 * @author youruo
 * @Date 2020-06-16 15:26
 */
public interface ProductSkusDao extends IBaseDao<ProductSkus> {

    Map<Integer, Integer> getInventoryById(List<Integer> ids);

}
