package com.rent.common.dto.components.response;

import com.rent.common.enums.components.EnumTradeResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
// @ApiModel(value = "订单支付返回参数", description = "返回参数类")
public class AliPayTradePayResponse implements Serializable {

    private static final long serialVersionUID = 6614968680280010L;

    // @Schema(description = "订单id", required = true)
    private String orderId;

    private String resultMessage;

    // @Schema(description = "交易结果")
    private EnumTradeResult tradeResult;
    // @Schema(description = "支付宝交易号")
    private String tradeNo;
    // @Schema(description = "商户订单号")
    private String outTradeNo;

}
