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

/**
 * 商品周期价格表
 *
 * @author youruo
 * @Date 2020-06-16 15:08
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "商品周期价格表")
public class ProductCyclePricesDto implements Serializable {

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
     * 商品id
     */
    @Schema(description = "商品id")
    private String itemId;

    /**
     * sku的id
     */
    @Schema(description = "sku的id")
    private Long skuId;

    /**
     * 租物租--
     * 目前官方限制 7天 30天 90天 180天 365天 指7天以上 30天以上
     * 租游记--
     * 目前官方限制 1天 3天 7天 30天 90天 180天 365天 730天
     */
    @Schema(description = " 目前官方限制1天3天 7天 30天 90天 180天 365天 730天")
    private Integer days;

    /**
     * 单天价格
     */
    @Schema(description = "单天价格")
    private BigDecimal price;


    /**
     * 周期销售价
     */
    @Schema(description = "周期销售价")
    private BigDecimal salePrice;

    /**
     * 芝麻押金评估金额
     */
    @Schema(description = "芝麻押金评估金额")
    private BigDecimal sesameDeposit;


    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
