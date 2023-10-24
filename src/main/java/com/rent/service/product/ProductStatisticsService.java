package com.rent.service.product;


import com.rent.common.dto.product.ProductCountsDto;
import com.rent.common.dto.product.ShopProductSerachReqDto;

public interface ProductStatisticsService {

    ProductCountsDto selectProductCounts();


    ProductCountsDto selectBusinessPrdouctCounts(ShopProductSerachReqDto model);
}
