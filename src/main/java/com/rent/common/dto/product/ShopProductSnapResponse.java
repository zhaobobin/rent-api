package com.rent.common.dto.product;

import lombok.Data;

import java.util.List;

@Data
public class ShopProductSnapResponse {

   private ProductDto product;

   private List<ProductImageDto> productImage;

   private List<ProductGiveBackAddressesDto> productGiveBackAddresses;

   private List<ProductAdditionalServicesDto> productAdditionalServices;

   /**
    * 商品的库存规格
    */
   private List<ShopProductSnapSkusResponse> shopProductSnapSkus;

   /**
    * 商品的属性规格--snapSpecs
    */
   private List<ShopProductSnapSpecResponse> snapSpecs;


   private ShopDto shop;

}
