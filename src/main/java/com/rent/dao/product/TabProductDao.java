        
package com.rent.dao.product;

import com.rent.config.mybatis.IBaseDao;
import com.rent.model.product.TabProduct;

import java.util.List;


/**
 * @author zhaowenchao
 */
public interface TabProductDao extends IBaseDao<TabProduct> {

    /**
     * 查询tab下一个sort字段值
     * @param tabId
     * @return
     */
    Integer getNextSort(Long tabId);


    /**
     * 检查tab下是否有某个商品
     * @param tabId
     * @param productId
     * @return 有返回true
     */
    Boolean checkTabProductExits(Long tabId,String productId);

    /**
     * 查询tab下对应的商品ID
     * @param tabId
     * @return
     */
    List<String> getProductIds(Long tabId);




}
