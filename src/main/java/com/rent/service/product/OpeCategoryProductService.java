
package com.rent.service.product;

import com.rent.common.dto.product.OpeCategoryNameAndIdDto;
import com.rent.model.product.Product;


/**
 * 前台类目商品表Service
 *
 * @author youruo
 * @Date 2020-06-15 10:28
 */
public interface OpeCategoryProductService {

    OpeCategoryNameAndIdDto opeCategoryStr(String ProductId);


    OpeCategoryNameAndIdDto opeCategoryStrV1(Integer categoryId);

    /**
     * 添加商品类目
     *
     * @param product
     * @param categoryId
     * @return
     */
    Boolean addCategoryProductCommon(Product product, Integer categoryId);


    /**
     * 更改类目商品上下架状态
     * @param product
     * @param categoryId
     * @param status
     * @param image
     */
    void changeOpeCategoryProductStatus(Product product, Integer categoryId, Integer status, String image);

    /**
     * 删除类目下商品
     *
     * @return String
     */
    Boolean deleteProduct(Integer categoryId, String itemId);



}