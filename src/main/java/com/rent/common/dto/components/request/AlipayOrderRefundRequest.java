package com.rent.common.dto.components.request;

import com.rent.common.enums.components.EnumTradeType;
import com.rent.common.enums.order.PaymentMethod;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "订单退款请求参数")
public class AlipayOrderRefundRequest implements Serializable {

    private static final long serialVersionUID = 2692688644873290429L;
    /**
     * 统一收单交易退款接口
     * https://docs.open.alipay.com/api_1/alipay.trade.refund/
     *
     * @param out_trade_no 订单支付时传入的商户订单号,不能和 trade_no同时为空。
     * @param trade_no 支付宝交易号
     * @param refund_amount 需要退款的金额，该金额不能大于订单金额,单位为元，支持两位小数
     * @param refund_reason 退款的原因说明
     * @return
     */
    @Schema(description = "订单编号")
    @NotBlank
    private String orderId;

    @Schema(description = "支付宝交易号")
    @NotBlank
    private String tradeNo;

    @Schema(description = "订单支付时传入的商户订单号")
    @NotBlank
    private String outTradeNo;

    @Schema(description = "需要退款的金额")
    private BigDecimal refundAmount;

    @Schema(description = "退款的原因说明")
    @NotBlank
    private String refundReason;

    @Schema(description = "交易类型")
    @NotNull
    private EnumTradeType tradeType;

    @Schema(description = "uid")
    @NotBlank
    private String uid;

    @Schema(description = "渠道编号")
    @NotBlank
    private String channelId;

    @Schema(description = "支付方式")
    private PaymentMethod paymentMethod;
}
