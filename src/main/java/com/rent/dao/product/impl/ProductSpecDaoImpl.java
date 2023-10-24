    
package com.rent.dao.product.impl;

import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.product.ProductSpecDao;
import com.rent.mapper.product.ProductSpecMapper;
import com.rent.model.product.ProductSpec;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * ProductSpecDao
 *
 * @author youruo
 * @Date 2020-06-16 15:32
 */
@Repository
public class ProductSpecDaoImpl extends AbstractBaseDaoImpl<ProductSpec, ProductSpecMapper> implements ProductSpecDao {


    @Override
    public List<Map> selectByItemId(String itemId) {
        return this.baseMapper.selectByItemId(itemId);
    }

    @Override
    public List<Map> selectProductopeSpecId(String itemId) {
        return this.baseMapper.selectProductopeSpecId(itemId);
    }

    @Override
    public List<Integer> selectProductopeSpecInfo(String itemId, Integer opeSpecId) {
        return this.baseMapper.selectProductopeSpecInfo(itemId,opeSpecId);
    }

    @Override
    public List<ProductSpec> selectRepairProductInfo(String itemId) {
        return this.baseMapper.selectRepairProductInfo(itemId);
    }

    @Override
    public List<Map> selectRepairProductInfoV1() {
        return this.baseMapper.selectRepairProductInfoV1();
    }
}
