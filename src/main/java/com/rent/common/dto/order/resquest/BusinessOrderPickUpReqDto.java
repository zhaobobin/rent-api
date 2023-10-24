package com.rent.common.dto.order.resquest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.math.BigDecimal;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "用户自提类")
public class BusinessOrderPickUpReqDto implements Serializable {
    @NotBlank
    @Schema(description = "订单id", required = true)
    private String orderId;
    private String operatorName;


    @Schema(description = "串号", required = true)
    private String serialNumber;

    @Schema(description = "成本价", required = true)
    private BigDecimal costPrice;
}
