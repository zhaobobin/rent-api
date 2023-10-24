        
package com.rent.dao.product;

import com.rent.config.mybatis.IBaseDao;
import com.rent.model.product.ProductLabel;

import java.util.List;
import java.util.Map;

/**
 * ProductLabelDao
 *
 * @author youruo
 * @Date 2020-06-29 18:32
 */
public interface ProductLabelDao extends IBaseDao<ProductLabel> {

    /**
     * 查询商品标签
     * @param productIdList
     * @return
     */
    Map<String,List<String>> getProductLabelList(List<String> productIdList);


}
