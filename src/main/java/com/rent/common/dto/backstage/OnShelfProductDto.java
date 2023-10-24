package com.rent.common.dto.backstage;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * @author zhaowenchao
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "已上架商品")
public class OnShelfProductDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "所属店铺id")
    private String shopId;

    @Schema(description = "商店名称")
    private String shopName;

    @Schema(description = "商品id")
    private String productId;

    @Schema(description = "商品最低价")
    private BigDecimal sale;

    @Schema(description = "商品名称")
    private String name;

    @Schema(description = "平台分类id，为最后一级分类id")
    private Integer categoryId;

    @Schema(description = "类目商品字符串")
    private String  opeCategoryStr;

    @Schema(description = "商品排序")
    private Integer  sort;

    @Schema(description = "系统分数")
    private Integer  sysSortScore;

    @Schema(description = "增加分数")
    private Integer  addSortScore;

    @Schema(description = "增加截止日期")
    private Date  addSortScoreExpire;

    @Schema(description = "系统分数")
    private Boolean  hidden;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
