package com.rent.common.dto.components.request;

import com.rent.common.enums.components.EnumTradeType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * https://opendocs.alipay.com/apis/api_1/alipay.trade.app.pay
 * app支付接口2.0
 *
 * @return
 */
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "App支付入参")
public class AliPayTradeAppPayRequest implements Serializable {

    private static final long serialVersionUID = -3182431402300538369L;

    @Schema(description = "商户订单号")
    @NotBlank
    private String outTradeNo;
    @Schema(description = "交易金额")
    private BigDecimal totalAmount;
    @Schema(description = "订单标题")
    @NotBlank
    private String subject;
    // @Schema(description = "回调地址")
    // @NotBlank
    // private String notifyUrl;
    @Schema(description = "买家的支付宝唯一用户号")
    // @NotBlank
    private String buyerId;
    @Schema(description = "uid")
    @NotBlank
    private String uid;
    @Schema(description = "期数")
    private List<String> periodList;
    @Schema(description = "订单编号")
    @NotBlank
    private String orderId;
    @Schema(description = "交易类型")
    @NotNull
    private EnumTradeType tradeType;
    @Schema(description = "商品id")
    private String productId;

    @Schema(description = "购买支付的时候，用户选择的花呗分期数")
    private String hbPeriodNum;

    @Schema(description = "TRUE：商家支付花呗手续费，FALSE：用户支付花呗手续费")
    private Boolean shopPayHbFee;


    @Schema(description = "首期叠加减免")
    private BigDecimal discountAmount;

    @Schema(description = "减免优惠券ID")
    private Long disTemplateId;

}
