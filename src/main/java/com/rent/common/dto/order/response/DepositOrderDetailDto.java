package com.rent.common.dto.order.response;

import com.rent.common.enums.order.EnumOrderPayDepositStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author xiaoyao
 * @version V1.0
 * @date 2021-6-16 下午 3:00:50
 * @since 1.0
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "押金充值详情")
public class DepositOrderDetailDto implements Serializable {

    private static final long serialVersionUID = -4667748452953663001L;

    /**
     * 订单编号
     */
    @Schema(description = "订单编号")
    private String orderId;

    /**
     * 用户ID
     */
    @Schema(description = "用户ID")
    private String uid;

    /**
     * 押金金额
     */
    @Schema(description = "订单编号")
    private BigDecimal amount;

    /**
     * 交易号
     */
    @Schema(description = "交易号")
    private String tradeNo;

    /**
     * 交易号
     */
    @Schema(description = "交易号")
    private String outTradeNo;

    /**
     * 状态
     */
    @Schema(description = "状态 WAITING_PAYMENT:待支付 PAID：已支付 WITHDRAW：已提现")
    private EnumOrderPayDepositStatus status;

    /**
     * 支付时间
     */
    @Schema(description = "支付时间")
    private Date payTime;

    /**
     * 提现时间
     */
    @Schema(description = "提现时间")
    private Date refundTime;

    /**
     * 渠道ID
     */
    @Schema(description = "渠道ID")
    private String channelId;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private Date createTime;
}
