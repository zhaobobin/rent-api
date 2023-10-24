package com.rent.model.order;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rent.common.enums.order.EnumOrderByStagesStatus;
import com.rent.common.enums.order.PaymentMethod;
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
 * 用户订单分期
 *
 * @author xiaoyao
 * @Date 2020-06-11 17:25
 */
@Data
@Accessors(chain = true)
@TableName("ct_order_by_stages")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderByStages implements Serializable {

    private static final long serialVersionUID = -9037360421209458541L;

    /**
     * Id
     */
    @TableId(value = "id")
    private Long id;
    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;
    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    private Date updateTime;
    /**
     * 删除时间
     */
    @TableField(value = "delete_time")
    private Date deleteTime;
    /**
     * 租期，单位：天
     */
    @TableField(value = "lease_term")
    private Integer leaseTerm;
    /**
     * 订单号
     */
    @TableField(value = "order_id")
    private String orderId;
    /**
     * 总期数
     */
    @TableField(value = "total_periods")
    private Integer totalPeriods;
    /**
     * 当前期数
     */
    @TableField(value = "current_periods")
    private Integer currentPeriods;
    /**
     * 总租金
     */
    @TableField(value = "total_rent")
    private BigDecimal totalRent;
    /**
     * 当期应付租额
     */
    @TableField(value = "current_periods_rent")
    private BigDecimal currentPeriodsRent;
    /**
     * 状态:  1：待支付 2：已支付 3：逾期已支付 4：逾期待支付 5：已取消  6: 已结算 7:已退款
     */
    @TableField(value = "status")
    private EnumOrderByStagesStatus status;
    /**
     * 逾期天数，单位：天
     */
    @TableField(value = "overdue_days")
    private Integer overdueDays;
    /**
     * 还款日
     */
    @TableField(value = "repayment_date")
    private Date repaymentDate;
    /**
     * 账单日
     */
    @TableField(value = "statement_date")
    private Date statementDate;
    /**
     * 商户订单
     */
    @TableField(value = "out_trade_no")
    private String outTradeNo;
    /**
     * 支付宝交易号
     */
    @TableField(value = "trade_no")
    private String tradeNo;

    /**
     * 退款资金交易号(已退款金额 部分退款才有)
     */
    @TableField(value = "refund_transaction_number")
    private String refundTransactionNumber;
    /**
     * 商铺id冗余订单表的字段
     */
    @TableField(value = "shop_id")
    private String shopId;
    /**
     * 渠道来源
     */
    @TableField(value = "channel_id")
    private String channelId;
    /**
     * 分账时间
     */
    @TableField(value = "split_bill_time")
    private Date splitBillTime;


    @TableField(value = "payment_method")
    private PaymentMethod paymentMethod;

    /**
     * 支付方式
     */
    @TableField(value = "deduction_times")
    private Integer deductionTimes;

    @TableField(value = "sync_to_chain")
    private Boolean syncToChain;


    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
