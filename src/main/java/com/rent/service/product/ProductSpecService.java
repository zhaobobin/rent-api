
package com.rent.service.product;

import com.rent.common.dto.product.ProductSpecDto;
import com.rent.common.dto.product.ProductSpecReqDto;
import com.rent.common.dto.product.ShopProductSpecAllReqDto;
import com.rent.common.dto.product.SpecsDto;

import java.util.List;

/**
 * 商品属性规格表Service
 *
 * @author youruo
 * @Date 2020-06-16 15:32
 */
public interface ProductSpecService {

    /**
     * 批量增加商品规格
     *
     * @param specAll
     */
    void insertSpec(String productId, Integer skuId, List<ShopProductSpecAllReqDto> specAll);
    /**
     * 删除商品规格
     *
     * @param productId
     */
    void deleteSpec(String productId);
    /**
     * 新增商品属性规格表
     *
     * @param request 条件
     * @return boolean
     */
    Integer addProductSpec(ProductSpecDto request);

    Integer repairAddProductSpe(ProductSpecDto request);
    /**
     * <p>
     * 根据条件查询一条记录
     * </p>
     *
     * @param request 实体对象
     * @return ProductSpec
     */
    ProductSpecDto queryProductSpecDetail(ProductSpecReqDto request);

    List<SpecsDto> queryspecs(String itemId);



}