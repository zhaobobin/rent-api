    
package com.rent.dao.product.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.product.ShopGiveBackAddressesDao;
import com.rent.mapper.product.ShopGiveBackAddressesMapper;
import com.rent.model.product.ShopGiveBackAddresses;
import org.springframework.stereotype.Repository;

/**
 * ShopGiveBackAddressesDao
 *
 * @author youruo
 * @Date 2020-06-17 10:47
 */
@Repository
public class ShopGiveBackAddressesDaoImpl extends AbstractBaseDaoImpl<ShopGiveBackAddresses, ShopGiveBackAddressesMapper> implements ShopGiveBackAddressesDao{

    @Override
    public ShopGiveBackAddresses getShopGiveBackAddressesByShopId(String shopId) {
        return this.getOne(new QueryWrapper<ShopGiveBackAddresses>().eq("shop_id", shopId).isNull("delete_time").orderByDesc("id").last("limit 1"));
    }


}
