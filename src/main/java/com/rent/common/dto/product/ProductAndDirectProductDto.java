package com.rent.common.dto.product;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 直购和租赁商品
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "直购和租赁商品")
public class ProductAndDirectProductDto implements Serializable {

    private static final long serialVersionUID = 1L;


    @Schema(description = "商品ID")
    private String productId;


    @Schema(description = "商品名称")
    private String name;


    @Schema(description = "商家ID")
    private String shopId;

    @Schema(description = "是否是购买商品 1-租赁商品 2-购买商品")
    private Integer isDirect;


    @Schema(description = "历史销量")
    private Integer salesVolume;

    @Schema(description = "最低售价-天/起--根据isDirect判断")
    private BigDecimal lowestSalePrice;

    @Schema(description = "主图")
    private String src;

    @Schema(description = "最低售价")
    private BigDecimal sale;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
