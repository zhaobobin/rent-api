    
package com.rent.dao.product.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rent.common.enums.product.ProductStatus;
import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.product.OpeCategoryProductDao;
import com.rent.mapper.product.OpeCategoryProductMapper;
import com.rent.model.product.OpeCategoryProduct;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * OpeCategoryProductDao
 *
 * @author youruo
 * @Date 2020-06-15 10:28
 */
@Repository
public class OpeCategoryProductDaoImpl extends AbstractBaseDaoImpl<OpeCategoryProduct, OpeCategoryProductMapper> implements OpeCategoryProductDao {

    @Override
    public List<String> getProductIdInParentId(String[] parentCategoryIds) {
        List<OpeCategoryProduct> list = list(new QueryWrapper<OpeCategoryProduct>()
                .select("item_id")
                .eq("status", ProductStatus.STATUS.getCode())
                .in("parent_category_id", parentCategoryIds)
                .isNull("delete_time")
        );
        List<String> itemIds = list.stream().map(OpeCategoryProduct::getItemId).collect(Collectors.toList());
        return itemIds;
    }

    @Override
    public Boolean copyTransferCategoryProduct(String originalProductId, String newestProductId, String transferShopId) {
        OpeCategoryProduct opeCategoryProduct = this.getOne(new QueryWrapper<OpeCategoryProduct>()
                .eq("item_id", originalProductId)
                .orderByDesc("id")
                .last("limit 1")
        );
        if (null != opeCategoryProduct) {
            Date now = new Date();
            opeCategoryProduct.setStatus(ProductStatus.NO_STATUS.getCode());
            opeCategoryProduct.setItemId(newestProductId);
            opeCategoryProduct.setUpdateTime(now);
            opeCategoryProduct.setCreateTime(now);
            opeCategoryProduct.setId(null);
            opeCategoryProduct.setShopId(transferShopId);
            opeCategoryProduct.setSalesVolume(1);
            this.save(opeCategoryProduct);
        }
        return Boolean.FALSE;
    }

    @Override
    public void cleanSortScore() {
        baseMapper.cleanSortScore();
    }

    @Override
    public void setProductSortScore(String productId, Integer score) {
        baseMapper.setProductSortScore(productId, score);
    }


}
