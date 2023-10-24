        
package com.rent.dao.product;

import com.rent.config.mybatis.IBaseDao;
import com.rent.model.product.ShopGiveBackAddresses;

/**
 * ShopGiveBackAddressesDao
 *
 * @author youruo
 * @Date 2020-06-17 10:47
 */
public interface ShopGiveBackAddressesDao extends IBaseDao<ShopGiveBackAddresses> {

    ShopGiveBackAddresses getShopGiveBackAddressesByShopId(String shopId);

}

