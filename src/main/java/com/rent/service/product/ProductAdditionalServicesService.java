        
package com.rent.service.product;

import com.rent.common.dto.product.ProductAdditionalServicesDto;

import java.util.List;

/**
 * 商品增值服务表Service
 *
 * @author youruo
 * @Date 2020-06-16 15:07
 */
public interface ProductAdditionalServicesService {

        /**
         * 批量增加增值服务商品
         * @param productId
         * @param shopAdditionals
         */
        void insertAdditionalService(String productId, List<Integer> shopAdditionals);

        /**
         * 批量修改增值服务商品
         * @param productId
         * @param shopAdditionals
         */
        void updateAdditionalService(String productId, List<Integer> shopAdditionals);


        /**
         * 新增商品增值服务表
         *
         * @param request 条件
         * @return boolean
         */
        Integer addProductAdditionalServices(ProductAdditionalServicesDto request);

        /**
         * 查询商品增值服务
         * @param produnctId
         * @return
         */
        List<ProductAdditionalServicesDto> queryProductAdditionalServicesByProductId(String produnctId);

        List<Integer> queryShopAdditionals(String produnctId);



}