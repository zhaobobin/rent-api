package com.rent.common.dto.product;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 12132
 */
@Data
@Accessors(chain = true)
public class ShopProductSkuAllReqDto {
    @Schema(description = "skuId")
    private Integer skuId;
    @Schema(description = "官方售价")
    private BigDecimal marketPrice;
    @Schema(description = "押金")
    private BigDecimal depositPrice;
    @Schema(description = "库存")
    private Integer inventory;
    @Schema(description = "新旧程度")
    private Integer oldNewDegree;

    @Schema(description = "价格周期")
    private List<ProductCyclePricesDto> cycs;
    @Schema(description = "商品规格")
    private String platformSpecValue;
    @Schema(description = "商品规格")
    private List<ShopProductSpecAllReqDto> specAll;


}
