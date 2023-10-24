        
package com.rent.service.product;

import com.rent.common.dto.product.ProductParameterDto;

import java.util.List;

/**
 * 商品参数信息表Service
 *
 * @author youruo
 * @Date 2020-06-29 18:33
 */
public interface ProductParameterService {

        /**
         * 新增商品参数信息表
         *
         * @param request 条件
         * @return boolean
         */
        Long addProductParameter(ProductParameterDto request);


        /**
         * 查询商品参数
         * @param productId
         * @return
         */
        List<ProductParameterDto> queryProductParameterList(String productId);


        void batchinsert(String productId,List<ProductParameterDto> params);

        void deleteParamsProduct(String productId);


}