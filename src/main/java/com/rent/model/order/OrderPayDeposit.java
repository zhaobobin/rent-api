package com.rent.model.order;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rent.common.enums.order.EnumOrderPayDepositStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户支付押金表
 * @author xiaotong
 */
@Data
@Accessors(chain = true)
@TableName("ct_order_pay_deposit")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderPayDeposit {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 订单编号
     */
    @TableField(value = "order_id")
    private String orderId;

    /**
     * 用户ID
     */
    @TableField(value = "uid")
    private String uid;

    /**
     * 总押金
     */
    @TableField(value = "total_deposit")
    private BigDecimal totalDeposit;


    /**
     * 分控给出的押金
     */
    @TableField(value = "risk_deposit")
    private BigDecimal riskDeposit;

    /**
     * 押金金额
     */
    @TableField(value = "amount")
    private BigDecimal amount;

    /**
     * 交易号
     */
    @TableField(value = "trade_no")
    private String tradeNo;

    /**
     * 交易号
     */
    @TableField(value = "out_trade_no")
    private String outTradeNo;

    /**
     * 状态
     */
    @TableField(value = "status")
    private EnumOrderPayDepositStatus status;

    /**
     * 支付时间
     */
    @TableField(value = "pay_time")
    private Date payTime;

    /**
     * 提现时间
     */
    @TableField(value = "refund_time")
    private Date refundTime;

    /**
     * 退款操作人员
     */
    @TableField(value = "refund_user")
    private Long refundUser;


    /**
     * 渠道ID
     */
    @TableField(value = "channel_id")
    private String channelId;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;


    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}