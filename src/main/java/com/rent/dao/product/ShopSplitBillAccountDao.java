        
package com.rent.dao.product;

import com.rent.config.mybatis.IBaseDao;
import com.rent.model.product.ShopSplitBillAccount;

import java.util.List;

/**
 * ShopSplitBillAccountDao
 *
 * @author youruo
 * @Date 2020-06-17 10:49
 */
public interface ShopSplitBillAccountDao extends IBaseDao<ShopSplitBillAccount> {

    /**
     * 创建或者修改店铺的时候，初始化或者修改店铺分账里的冗余信息
     * @param shopId
     * @param shopName
     * @param shopFirmInfo
     */
    void init(String shopId,String shopName,String shopFirmInfo);

    /**
     * 根据店铺ID获取到分账账户信息
     * @param shopId
     * @return
     */
    List<ShopSplitBillAccount> getByShopId(String shopId);

    /**
     * 根据店铺ID获取到 【已经审核通过的】 分账账户信息
     * @param shopId
     * @return
     */
    List<ShopSplitBillAccount> getByPassedShopId(String shopId);


    /**
     * 添加页面 获取店铺
     * @param shopName
     * @return
     */
    List<ShopSplitBillAccount> getShopList(String shopName);

    /**
     * 保存分账账户信息
     * @param accountModel
     */
    void saveAccountInfo(ShopSplitBillAccount accountModel);
}
