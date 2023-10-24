package com.rent.common.dto.order.resquest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Schema(description = "用户租金修改累")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRentChangeReqDto implements Serializable {
    @Schema(description = "订单编号")
    @NotBlank
    private String orderId;

    @Schema(description = "销售价格")
    @NotNull
    private BigDecimal salePrice;

    @Schema(description = "日租金")
    @NotNull
    private BigDecimal cyclePrice;


    @Schema(description = "总租金")
    @NotNull
    private BigDecimal totalRent;

    @Schema(description = "到期买断价", minimum = "0.00")
    @NotNull
    private BigDecimal expireBuyOutPrice;
}
