
package com.rent.service.product;

import com.rent.common.dto.product.ProductCyclePricesDto;
import com.rent.model.product.ProductCyclePrices;

import java.util.List;

/**
 * 商品周期价格表Service
 *
 * @author youruo
 * @Date 2020-06-16 15:08
 */
public interface ProductCyclePricesService {

    /**
     * 查询sku以及周期对应的价格
     * @param skuId
     * @param days
     * @return
     */
    ProductCyclePrices getSkuCyclePrice(Long skuId, int days);

    /**
     * d
     * 查询商品周期价格
     *
     * @param skuId
     * @return
     */
    List<ProductCyclePricesDto> selectCyclePricesBySkuId(Long skuId);

    /**
     * 获取最低价格商品信息
     * @param itemId
     * @return
     */
    ProductCyclePricesDto getLowestProductCyclePricesByItemId(String itemId);


    /**
     * 批量甚至商品周期价格
     */
    void insertCyclePrices(List<ProductCyclePricesDto> cycs, Integer skuId,String productId);

    /**
     * 删除商品周期价格
     *
     * @param productId
     */
    void deleteCyclePrices(String productId);

    /**
     * 新增商品周期价格表
     *
     * @param request 条件
     * @return boolean
     */
    Integer addProductCyclePrices(ProductCyclePricesDto request);



}