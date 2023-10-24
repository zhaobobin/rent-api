
package com.rent.service.product;

import com.rent.common.dto.product.PricingInformationsDto;
import com.rent.common.dto.product.ProductSkusDto;
import com.rent.common.dto.product.ShopProductSkuAllReqDto;

import java.util.List;

/**
 * 商品sku表Service
 *
 * @author youruo
 * @Date 2020-06-16 15:27
 */
public interface ProductSkusService {

    List<PricingInformationsDto> selectPricingByItemId(String productId);

    /**
     * 获取最低价格的商品库存信息
     * @param skuId
     * @return
     */
    ProductSkusDto getSkuBySkuId(Long skuId);

    /**
     * 根据获取商品库存信息
     * @param productId
     * @return
     */
    List<ShopProductSkuAllReqDto> selectPruductSkusByItemId(String productId);

    /**
     * 新增商品sku表
     *
     * @param request 条件
     * @return boolean
     */
    Integer addProductSkus(ProductSkusDto request);

    /**
     * 删除商品库存
     *
     * @param itemId
     */
    void deleteProductSkus(String itemId);

}