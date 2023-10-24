package com.rent.common.dto.components.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 支付宝订单结果查询请求类
 *
 * @author xiaoyao
 * @Date 2020-06-24 16:11
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "支付宝订单结果查询请求类")
public class AlipayTradePayQueryRequest implements Serializable {

    private static final long serialVersionUID = -2186894248922967367L;

    /**
     * 渠道编号
     */
    @Schema(description = "渠道编号")
    @NotBlank
    private String channelId;

    /**
     * 银行间联模式下有用,双联通过该参数指定需要查询的交易所属收单机构的pid
     */
    @Schema(description = "银行间联模式下有用,双联通过该参数指定需要查询的交易所属收单机构的pid")
    private String orgPid;

    /**
     * 支付宝交易号
     */
    @Schema(description = "支付宝交易号")
    private String tradeNo;

    /**
     * 商户订单号
     */
    @Schema(description = "商户订单号")
    private String outTradeNo;

    /**
     * 订单号
     */
    @Schema(description = "订单号")
    private String orderId;

}
