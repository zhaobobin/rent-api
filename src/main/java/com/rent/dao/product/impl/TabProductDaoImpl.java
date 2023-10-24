
package com.rent.dao.product.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.product.TabProductDao;
import com.rent.mapper.product.TabProductMapper;
import com.rent.model.product.TabProduct;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;


/**
 * @author zhaowenchao
 */
@Repository
public class TabProductDaoImpl extends AbstractBaseDaoImpl<TabProduct, TabProductMapper> implements TabProductDao {


    @Override
    public Integer getNextSort(Long tabId) {
        TabProduct tabProduct = getOne(new QueryWrapper<TabProduct>().select("index_sort").eq("tab_id",tabId).orderByDesc("index_sort").last("limit 1").isNull("delete_time"));
        return tabProduct==null ? 1 : tabProduct.getIndexSort()+1;
    }

    @Override
    public Boolean checkTabProductExits(Long tabId, String productId) {
        List<TabProduct> tabProduct = list(new QueryWrapper<TabProduct>().select("id").eq("tab_id",tabId).eq("item_id",productId).isNull("delete_time"));
        return CollectionUtil.isNotEmpty(tabProduct);
    }

    @Override
    public List<String> getProductIds(Long tabId) {
        List<TabProduct> tabProduct = list(new QueryWrapper<TabProduct>().select("item_id").eq("tab_id",tabId).isNull("delete_time"));
        return tabProduct.stream().map(TabProduct::getItemId).collect(Collectors.toList());
    }
}
