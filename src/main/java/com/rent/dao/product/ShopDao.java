        
package com.rent.dao.product;

import com.rent.config.mybatis.IBaseDao;
import com.rent.model.product.Shop;

import java.util.List;

/**
 * ShopDao
 *
 * @author youruo
 * @Date 2020-06-17 10:33
 */
public interface ShopDao extends IBaseDao<Shop> {

    /**
     * 根据shopName获取店铺ID列表
     * @param name
     * @return
     */
    List<String> getShopIdListByName(String name);

    /**
     * 根据店铺ID获取店铺信息
     * @param shopId
     * @return
     */
    Shop getByShopId(String shopId);



}
