package com.rent.common.dto.components.request;

import com.rent.common.enums.components.EnumTradeType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author zhaowenchao
 */
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Page支付入参")
public class AliPayTradeAppPageRequest implements Serializable {

    private static final long serialVersionUID = -3182431402300538369L;

    @Schema(description = "商户订单号")
    @NotBlank
    private String outTradeNo;

    @Schema(description = "交易金额")
    private BigDecimal totalAmount;

    @Schema(description = "订单标题")
    @NotBlank
    private String subject;

    @Schema(description = "订单编号")
    @NotBlank
    private String shopId;

    @Schema(description = "交易类型")
    @NotNull
    private EnumTradeType tradeType;
}
