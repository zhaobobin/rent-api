package com.rent.common.dto.components.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * https://opendocs.alipay.com/apis/api_1/alipay.trade.create
 * alipay.trade.create(统一收单交易创建接口)
 *
 * @return
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "APP支付响应参数")
public class AliPayTradeCreateResponse implements Serializable {

    private static final long serialVersionUID = 1293062642429524342L;

    /**
     * 商户原始订单号，最大长度限制32位
     */
    @Schema(description = "商户原始订单号")
    private String merchantOrderNo;

    /**
     * 商户网站唯一订单号
     */
    @Schema(description = "商户网站唯一订单号")
    private String outTradeNo;

    /**
     * 收款支付宝账号对应的支付宝唯一用户号。
     * 以2088开头的纯16位数字
     */
    @Schema(description = "收款支付宝账号对应的支付宝唯一用户号")
    private String sellerId;

    /**
     * 该笔订单的资金总额，单位为RMB-Yuan。取值范围为[0.01，100000000.00]，精确到小数点后两位。
     */
    @Schema(description = "该笔订单的资金总额")
    private String totalAmount;

    /**
     * 该交易在支付宝系统中的交易流水号。
     */
    @Schema(description = "该交易在支付宝系统中的交易流水号")
    private String tradeNo;

    @Schema(description = "流水号")
    private String serialNo;
}
