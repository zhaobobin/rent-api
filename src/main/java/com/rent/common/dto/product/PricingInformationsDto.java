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
 * 店铺增值服务表
 *
 * @author youruo
 * @Date 2020-06-17 10:35
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "商品定价信息")
public class PricingInformationsDto implements Serializable {

    private static final long serialVersionUID = 1L;


    @Schema(description = "规格/颜色")
    private String speAndColer;

    @Schema(description = "官方售价")
    private BigDecimal marketPrice;


    @Schema(description = "租赁价格单天 7元/15天")
    private String rentPrice;


    @Schema(description = "库存数量")
    private Integer inventory;

    @Schema(description = "是否可买断")
    private Integer buyOutSupport;


    @Schema(description = "销售价 买断  7元/15天")
    private String salePrice;



    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
