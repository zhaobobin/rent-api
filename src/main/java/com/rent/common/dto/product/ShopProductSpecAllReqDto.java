package com.rent.common.dto.product;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ShopProductSpecAllReqDto {

    @Schema(description = "商品id")
    private Integer platformSpecId;
    @Schema(description = "平台规格id")
    private Integer opeSpecId;
    @Schema(description = "商品规格名称")
    private  String platformSpecName;
    @Schema(description = "商品规格值")
    private String  platformSpecValue;



}
