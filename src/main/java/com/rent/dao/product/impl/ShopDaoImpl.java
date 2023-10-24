    
package com.rent.dao.product.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.product.ShopDao;
import com.rent.mapper.product.ShopMapper;
import com.rent.model.product.Shop;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * ShopDao
 *
 * @author youruo
 * @Date 2020-06-17 10:33
 */
@Repository
public class ShopDaoImpl extends AbstractBaseDaoImpl<Shop, ShopMapper> implements ShopDao{


    @Override
    public List<String> getShopIdListByName(String name) {
        List<Shop> shops = list(new QueryWrapper<Shop>().select("shop_id").like("name",name));
        return shops.stream().map(Shop::getShopId).collect(Collectors.toList());
    }

    @Override
    public Shop getByShopId(String shopId) {
        return getOne(new QueryWrapper<Shop>().eq("shop_id",shopId).isNull("delete_time"));
    }
}
