package com.rent.service.export;

import com.rent.common.dto.export.ProductExportDto;
import com.rent.common.dto.product.ShopProductSerachReqDto;

import java.util.List;

/**
 * @author zhaowenchao
 */
public interface ProductExportService {

    /**
     * 商品导出
     * @param request
     * @return
     */
    List<ProductExportDto> exportBusinessProduct(ShopProductSerachReqDto model);



}
