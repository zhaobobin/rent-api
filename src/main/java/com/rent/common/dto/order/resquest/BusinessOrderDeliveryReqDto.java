package com.rent.common.dto.order.resquest;

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
 * @date 2020-6-18 11:17
 * @since 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "商家发货请求类")
public class BusinessOrderDeliveryReqDto implements Serializable {
    @Schema(description = "订单id", required = true)
    private String orderId;
    @Schema(description = "物流公司Id", required = true)
    private Long expressId;
    @Schema(description = "物流编号", required = true)
    private String expressNo;
    @Schema(description = "串号", required = true)
    private String serialNumber;
    @Schema(description = "成本价", required = true)
    private BigDecimal costPrice;
    @Schema(description = "供应商", required = true)
    private String supplier;
    @Schema(description = "是否上脸", required = true)
    private Boolean antChain;

    private String operatorName;
}
