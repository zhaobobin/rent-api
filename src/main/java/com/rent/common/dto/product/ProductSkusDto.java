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
import java.util.Date;
import java.util.List;

/**
 * 商品sku表
 *
 * @author youruo
 * @Date 2020-06-16 15:26
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "商品sku表")
public class ProductSkusDto implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * Id
     */
    @Schema(description = "Id")
    private Integer id;

    /**
     * CreateTime
     */
    @Schema(description = "CreateTime")
    private Date createTime;

    /**
     * UpdateTime
     */
    @Schema(description = "UpdateTime")
    private Date updateTime;

    /**
     * DeleteTime
     */
    @Schema(description = "DeleteTime")
    private Date deleteTime;

    /**
     * 产品ID
     */
    @Schema(description = "产品ID")
    private String itemId;

    /**
     * 市场价
     */
    @Schema(description = "官方售价")
    private BigDecimal marketPrice;

    /**
     * 当前库存
     */
    @Schema(description = "当前库存")
    private Integer inventory;

    /**
     * 总库存
     */
    @Schema(description = "总库存")
    private Integer totalInventory;

    /**
     * 1为全新 2为99新 3为95新 4为9成新 5为8成新 6为7成新
     */
    @Schema(description = "1为全新 2为99新 3为95新 4为9成新 5为8成新 6为7成新")
    private Integer oldNewDegree;

    /**
     * 是否可以买断 1:可以买断。0:不可以买断
     */
    @Schema(description = "是否可以买断 1:可以买断。0:不可以买断")
    private Integer buyOutSupport;

    @Schema(description = "是否支持分期 0-不支持；1支持")
    private Integer isSupportStage;

    /**
     * 销售价
     */
    @Schema(description = "销售价")
    private BigDecimal salePrice;

    /**
     * 押金
     */
    @Schema(description = "押金")
    private BigDecimal depositPrice;


    @Schema(description = "商品库存图片")
    private List<ProductSkusImageResponse> values;

    @Schema(description = "商品周期价格表")
    private List<ProductCyclePricesDto> cyclePrices;


    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
