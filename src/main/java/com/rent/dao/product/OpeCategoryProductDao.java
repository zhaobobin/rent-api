        
package com.rent.dao.product;


import com.rent.config.mybatis.IBaseDao;
import com.rent.model.product.OpeCategoryProduct;

import java.util.List;

/**
 * OpeCategoryProductDao
 *
 * @author youruo
 * @Date 2020-06-15 10:28
 */
public interface OpeCategoryProductDao extends IBaseDao<OpeCategoryProduct> {

    /**
     * 获取一级类目下所有商品的ID
     * @param parentCategoryIds 一级类目数组
     * @return
     */
    List<String> getProductIdInParentId(String[] parentCategoryIds);

    Boolean copyTransferCategoryProduct(String originalProductId, String newestProductId, String transferShopId);

    /**
     * 设置商品排序分数值为0
     */
    void cleanSortScore();

    /**
     * 设置商品排序分数值
     *
     * @param productId
     * @param score
     * @return
     */
    void setProductSortScore(String productId, Integer score);
}
