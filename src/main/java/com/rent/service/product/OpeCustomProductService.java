        
package com.rent.service.product;

import com.rent.model.product.Product;

/**
 * 自定义tab产品挂载表Service
 *
 * @author youruo
 * @Date 2020-06-16 10:00
 */
public interface OpeCustomProductService {


        Boolean deleteProduct(String itemId);


        void repairCusProduct(Integer status, Product product, String image);



}