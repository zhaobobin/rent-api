package com.rent.common.dto.product;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;


@Data
public class RentWellProductDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "商品ID")
    private String productId;

    @Schema(description = "商品名称")
    private String productName;

    @Schema(description = "商品图片")
    private String image;

    @Schema(description = "商品价格")
    private BigDecimal price;

    @Schema(description = "新旧程度")
    private String oldNewDegree;

    @Schema(description = "销量")
    private Integer salesVolume;


}
