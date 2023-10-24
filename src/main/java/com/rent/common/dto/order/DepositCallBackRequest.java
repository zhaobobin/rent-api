package com.rent.common.dto.order;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;


/**
 * @author zhaowenchao
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DepositCallBackRequest implements Serializable {

    private static final long serialVersionUID = -223861630287131957L;

    @Schema(description = "订单编号")
    @NotBlank
    private String orderId;

    @Schema(description = "商户订单号")
    private String outTradeNo;

    @Schema(description = "支付宝交易号")
    private String tradeNo;

    @Schema(description = "用户支付宝ID")
    private String buyerUserId;
}
