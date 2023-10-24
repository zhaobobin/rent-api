        
package com.rent.service.product;

import com.rent.common.dto.product.ProductSkuValuesDto;

/**
 * 商品sku规格属性value表Service
 *
 * @author youruo
 * @Date 2020-06-16 15:28
 */
public interface ProductSkuValuesService {


        /**
         * 新增商品sku规格属性value表
         *
         * @param request 条件
         * @return boolean
         */
        Integer addProductSkuValues(ProductSkuValuesDto request);


}