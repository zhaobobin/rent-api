package com.rent.common.dto.components.request;

import com.rent.common.enums.components.EnumTradeType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "支付宝订单支付")
public class AliPayTradePayRequest implements Serializable {
    /**
     * 冻结转支付
     * https://docs.open.alipay.com/api_1/alipay.trade.pay
     * alipay.trade.pay(统一收单交易支付接口)
     *
     * @param out_trade_no 商户订单号
     * @param auth_code 支付授权码
     * @param subject  订单标题
     * @param totalAmount 订单金额
     * @return
     */
    @Schema(description = "订单号", required = true)
    @NotBlank
    private String orderId;
    @Schema(description = "商户订单号", required = true)
    @NotBlank
    private String outTradeNo;
    @Schema(description = "支付授权码", required = true)
    private String authCode;
    @Schema(description = "订单标题", required = true)
    @NotBlank
    private String subject;
    @Schema(description = "回调地址", required = true)
    // @NotBlank
    private String notifyUrl;
    @Schema(description = "订单金额", required = true)
    private BigDecimal totalAmount;
    @Schema(description = "付款方id", required = true)
    private String payerUserId;
    @Schema(description = "期次", required = true)
    @NotNull
    private List<String> periodList;
    @Schema(description = "订单类型")
    private EnumTradeType tradeType;
    @Schema(description = "是否是第一期")
    private Boolean isFisrtPeriod;
    @Schema(description = "店铺ID")
    private String shopId;

}
