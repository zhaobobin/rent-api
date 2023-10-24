        
package com.rent.service.product;

import com.rent.common.dto.product.ProductLabelDto;

import java.util.List;

/**
 * 商品租赁标签Service
 *
 * @author youruo
 * @Date 2020-06-29 18:32
 */
public interface ProductLabelService {

        /**
         * 新增商品租赁标签
         *
         * @param request 条件
         * @return boolean
         */
        Integer addProductLabel(ProductLabelDto request);


        /**
         * 获取商品标签
         * @param productId
         * @return
         */
        List<String> getProductLabelList(String productId);

        void batchProduct(String productId ,List<String> labels);

        void deleteLabelProduct(String productId);


}