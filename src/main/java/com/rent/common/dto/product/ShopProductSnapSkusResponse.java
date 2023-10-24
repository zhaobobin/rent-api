package com.rent.common.dto.product;

import lombok.Data;

import java.util.List;

@Data
public class ShopProductSnapSkusResponse {

    private ProductSkusDto productSkus;

    private List<ProductCyclePricesDto> cyclePrices;

    private List<ProductSkuValuesDto> skuValues;


}
