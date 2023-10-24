package com.rent.common.dto.order;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 订单增值服务信息
 *
 * @author xiaoyao
 * @Date 2020-06-16 10:08
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "订单增值服务信息")
public class OrderAdditionalServicesDto implements Serializable {

    private static final long serialVersionUID = -4294298909350485384L;


    /**
     * 订单id
     */
    @Schema(description = "订单id")
    private String orderId;

    /**
     * 店铺增值服务id
     */
    @Schema(description = "店铺增值服务id")
    private Integer shopAdditionalServicesId;

    /**
     * 增值服务费
     */
    @Schema(description = "增值服务费")
    private BigDecimal price;

    /**
     * 店铺增值服务id
     */
    @Schema(description = "店铺增值服务名称")
    private String shopAdditionalServicesName;
}
