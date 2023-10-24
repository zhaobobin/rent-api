package com.rent.common.dto.order.response;

import com.rent.common.enums.order.EnumOrderStatus;
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
 * @date 2020-8-4 下午 2:19:38
 * @since 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "买断原订单信息")
public class BuyOutOriginalOrderDto implements Serializable {

    private static final long serialVersionUID = 4738090250316285791L;

    @Schema(description = "原订单号码")
    private String orderId;

    @Schema(description = "商品图片")
    private String imageUrl;

    @Schema(description = "商品Id")
    private String productId;

    @Schema(description = "商品名称")
    private String productName;

    @Schema(description = "规格")
    private String spec;

    @Schema(description = "总租金")
    private BigDecimal totalRent;

    @Schema(description = "已付租金")
    private BigDecimal payedRent;

    @Schema(description = "订单状态")
    private EnumOrderStatus status;
}
