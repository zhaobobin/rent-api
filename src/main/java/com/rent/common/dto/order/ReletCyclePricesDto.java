package com.rent.common.dto.order;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author xiaoyao
 * @version V1.0
 * @date 2020-11-5 上午 10:59:06
 * @since 1.0
 */
@Data
@Schema(description = "续租周期价格")
public class ReletCyclePricesDto {

    @Schema(description = " 目前官方限制1天3天 7天 30天 90天 180天 365天 730天")
    private Integer days;

    /**
     * 单天价格
     *
     */
    @Schema(description = "单天价格")
    private BigDecimal price;

    /**
     * 单天价格
     *
     */
    @Schema(description = "总价格")
    private BigDecimal totalPrice;

}
