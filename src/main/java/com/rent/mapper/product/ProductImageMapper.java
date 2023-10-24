        
package com.rent.mapper.product;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rent.model.product.ProductImage;

import java.util.List;

/**
 * ProductImageDao
 *
 * @author youruo
 * @Date 2020-06-16 15:16
 */
public interface ProductImageMapper extends BaseMapper<ProductImage>{

    void saveBatchImange(List<ProductImage> list);
}