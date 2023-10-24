package com.rent.common.dto.order.resquest;

import com.rent.common.enums.components.EnumAliPayStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author xiaoyao
 * @version V1.0
 * @date 2020-6-17 13:40
 * @since 1.0
 */
@Schema(description = "结算单支付回调请求类")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaySettlementCallBackRequest implements Serializable {

    private static final long serialVersionUID = -6608578232056049369L;
    @NotNull
    @Schema(description = "支付状态")
    private EnumAliPayStatus payStatus;
    @Schema(description = "订单编号", required = true)
    @NotBlank
    private String orderId;
    @Schema(description = "订单编号", required = true)
    private BigDecimal amount;
    @Schema(description = "商户订单编号", required = true)
    private String outTradeNo;
    @Schema(description = "支付宝交易号", required = true)
    private String tradeNo;
    @Schema(description = "支付时间", required = true)
    private Date paymentTime;

}
