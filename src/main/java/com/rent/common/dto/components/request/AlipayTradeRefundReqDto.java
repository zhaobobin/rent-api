package com.rent.common.dto.components.request;

import com.rent.common.dto.Page;
import com.rent.common.enums.components.EnumAliPayStatus;
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
 * 订单退款信息
 *
 * @author xiaoyao
 * @Date 2020-07-02 10:57
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "订单退款信息")
public class AlipayTradeRefundReqDto extends Page implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @Schema(description = "主键")
    private Long id;

    /**
     * 订单编号
     */
    @Schema(description = "订单编号")
    private String orderId;

    /**
     * 订单支付时传入的商户订单号
     */
    @Schema(description = "订单支付时传入的商户订单号")
    private String outTradeNo;

    /**
     * 支付宝交易号
     */
    @Schema(description = "支付宝交易号")
    private String tradeNo;

    /**
     * 标识一次退款请求，同一笔交易多次退款需要保证唯一，如需部分退款，则此参数必传。
     */
    @Schema(description = "标识一次退款请求，同一笔交易多次退款需要保证唯一，如需部分退款，则此参数必传。")
    private String outRequestNo;

    /**
     * 退款金额
     */
    @Schema(description = "退款金额")
    private BigDecimal refundAmount;

    /**
     * 退款原因
     */
    @Schema(description = "退款原因")
    private String refundReason;

    /**
     * 请求参数
     */
    @Schema(description = "请求参数")
    private String body;

    /**
     * 支付状态 00：初始化 01：支付中 02：成功 03：失败
     */
    @Schema(description = "支付状态 00：初始化 01：支付中 02：成功 03：失败")
    private EnumAliPayStatus status;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private Date createTime;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private Date updateTime;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
