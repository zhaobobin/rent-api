package com.rent.common.dto.product;

import lombok.Data;

import java.util.List;

@Data
public class ShopProductSnapSpecResponse {


    private ProductSpecDto productSpec;

    private List<ProductSpecValueImagesDto> specValueImages;

    private List<ShopProductSnapSpecValue> specValues;

    @Data
    public static class ShopProductSnapSpecValue {
        private ProductSpecValueDto productSpecValue;
        private List<ProductSpecValueImagesDto> productSpecValueImagesList;
    }

}
