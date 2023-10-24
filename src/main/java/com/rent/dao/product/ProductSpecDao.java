        
package com.rent.dao.product;

import com.rent.config.mybatis.IBaseDao;
import com.rent.model.product.ProductSpec;

import java.util.List;
import java.util.Map;

/**
 * ProductSpecDao
 *
 * @author youruo
 * @Date 2020-06-16 15:32
 */
public interface ProductSpecDao extends IBaseDao<ProductSpec> {


    List<Map> selectByItemId (String itemId);


    List<Map> selectProductopeSpecId(String itemId);

    List<Integer> selectProductopeSpecInfo(String itemId,Integer opeSpecId);


    List<ProductSpec> selectRepairProductInfo(String itemId);

    List<Map> selectRepairProductInfoV1();


}
