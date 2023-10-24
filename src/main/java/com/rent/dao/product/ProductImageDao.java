        
package com.rent.dao.product;

import com.rent.common.dto.product.ProductImageDto;
import com.rent.config.mybatis.IBaseDao;
import com.rent.model.product.ProductImage;

import java.util.List;

/**
 * ProductImageDao
 *
 * @author youruo
 * @Date 2020-06-16 15:16
 */
public interface ProductImageDao extends IBaseDao<ProductImage> {

    List<ProductImage> saveBatchImange(List<ProductImageDto> productImage, String newestProductId);


    /**
     * 获取商品主图
     * @param productId
     * @return
     */
    String getMainImage(String productId);

}
