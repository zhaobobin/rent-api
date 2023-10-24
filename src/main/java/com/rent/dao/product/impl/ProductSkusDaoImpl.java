    
package com.rent.dao.product.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.product.ProductSkusDao;
import com.rent.mapper.product.ProductSkusMapper;
import com.rent.model.product.ProductSkus;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * ProductSkusDao
 *
 * @author youruo
 * @Date 2020-06-16 15:26
 */
@Repository
public class ProductSkusDaoImpl extends AbstractBaseDaoImpl<ProductSkus, ProductSkusMapper> implements ProductSkusDao {

    @Override
    public Map<Integer, Integer> getInventoryById(List<Integer> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return new HashMap<>();
        }
        List<ProductSkus> skuList = this.baseMapper.selectList(new QueryWrapper<ProductSkus>().select("id,inventory").in("id", ids));
        if (CollectionUtils.isEmpty(skuList)) {
            return new HashMap<>();
        }
        return skuList.stream().collect(Collectors.toMap(ProductSkus::getId, ProductSkus::getInventory, (k1, k2) -> k2));
    }
}
