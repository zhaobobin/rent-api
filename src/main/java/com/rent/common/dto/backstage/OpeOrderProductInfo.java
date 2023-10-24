package com.rent.common.dto.backstage;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author xiaoyao
 * @version V1.0
 * @date 2020-8-4 上午 11:04:33
 * @since 1.0
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "订单商品信息")
public class OpeOrderProductInfo implements Serializable {

    private static final long serialVersionUID = -8437790760148013680L;

    @Schema(description = "商品Id")
    private String productId;

    @Schema(description = "商品主键")
    private Integer id;

    @Schema(description = "商品名称")
    private String productName;

    @Schema(description = "商品url")
    private String imageUrl;

    @Schema(description = "规格")
    private String spec;

    @Schema(description = "数量")
    private Integer num;

    @Schema(description = "是否可买断--改规则后暂时废弃")
    private Boolean buyOutSupport;

    @Schema(description = "是否支持买断 0不支持，1支持提前买断， 2支持到期买断")
    private Integer buyOutSupportV1;


    @Schema(description = "市场价")
    private BigDecimal marketPrice;

    @Schema(description = "销售价")
    private BigDecimal salePrice;



}
