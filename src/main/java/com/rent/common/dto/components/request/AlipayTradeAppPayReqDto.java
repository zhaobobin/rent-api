package com.rent.common.dto.components.request;

import com.rent.common.dto.Page;
import com.rent.common.enums.components.EnumAliPayStatus;
import com.rent.common.enums.components.EnumTradeType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * APP支付记录
 *
 * @author xiaoyao
 * @Date 2020-06-24 16:11
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "APP支付记录")
public class AlipayTradeAppPayReqDto extends Page implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @Schema(description = "主键")
    private Long id;

    /**
     * 订单号
     */
    @Schema(description = "订单号")
    private String orderId;

    /**
     * 商户订单号
     */
    @Schema(description = "商户订单号")
    private String outTradeNo;

    /**
     * 商户本次资金操作的请求流水号
     */
    @Schema(description = "商户本次资金操作的请求流水号 ")
    private String outRequestNo;

    /**
     * 主题
     */
    @Schema(description = "主题")
    private String subject;

    /**
     * 交易类型EnumTradeType 01:下单 02：关单 03：账单代扣 04：账单主动支付 05：结算单支付 06：充值 07：买断 08：商家分账
     */
    @Schema(description = "交易类型EnumTradeType 01:下单 02：关单 03：账单代扣 04：账单主动支付 05：结算单支付 06：充值 07：买断 08：商家分账")
    private EnumTradeType tradeType;

    /**
     * 交易金额
     */
    @Schema(description = "交易金额")
    private BigDecimal amount;

    /**
     * 00:初始化  01：支付中 02：支付成功 03：支付失败
     */
    @Schema(description = "00:初始化  01：支付中 02：支付成功 03：支付失败")
    private EnumAliPayStatus status;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private Date createTime;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间")
    private Date updateTime;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
