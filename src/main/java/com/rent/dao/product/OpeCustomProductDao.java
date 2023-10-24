        
package com.rent.dao.product;


import com.rent.config.mybatis.IBaseDao;
import com.rent.model.product.OpeCustomProduct;

/**
 * OpeCustomProductDao
 *
 * @author youruo
 * @Date 2020-06-16 10:00
 */
public interface OpeCustomProductDao extends IBaseDao<OpeCustomProduct> {

    /**
     * 设置商品排序分数值为0
     */
    void cleanSortScore();

    /**
     * 设置商品排序分数值
     * @param productId
     * @param score
     * @return
     */
    void setProductSortScore(String productId, Integer score);
}
