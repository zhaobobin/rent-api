    
package com.rent.dao.product.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rent.common.converter.product.ProductImageConverter;
import com.rent.common.dto.product.ProductImageDto;
import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.product.ProductImageDao;
import com.rent.mapper.product.ProductImageMapper;
import com.rent.model.product.ProductImage;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * ProductImageDao
 *
 * @author youruo
 * @Date 2020-06-16 15:16
 */
@Repository
public class ProductImageDaoImpl extends AbstractBaseDaoImpl<ProductImage, ProductImageMapper> implements
    ProductImageDao {

    @Override
    public List<ProductImage> saveBatchImange(List<ProductImageDto> productImage, String newestProductId) {
        Date now = new Date();
        if (CollectionUtil.isNotEmpty(productImage)) {
            List<ProductImage> result = ProductImageConverter.dtoList2ModelList(productImage);
            result.stream().map((x) -> {
                x.setCreateTime(now);
                x.setUpdateTime(now);
                x.setId(null);
                x.setProductId(newestProductId);
                return x;
            }).forEach(System.out::println);
            this.baseMapper.saveBatchImange(result);
            return result;
        }
        return null;
    }

    @Override
    public String getMainImage(String productId) {
        List<ProductImage> productImageList = list(new QueryWrapper<ProductImage>().eq("product_id",productId));
        if(CollectionUtil.isEmpty(productImageList)){
            return null;
        }
        return productImageList.get(0).getSrc();
    }
}
