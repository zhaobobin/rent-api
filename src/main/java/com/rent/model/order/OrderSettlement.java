
package com.rent.model.order;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rent.common.enums.order.EnumOrderSettlementStatus;
import com.rent.common.enums.order.EnumOrderSettlementType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单结算记录
 *
 * @author xiaoyao
 * @Date 2020-06-19 10:49
 */
@Data
@Accessors(chain = true)
@TableName("ct_order_settlement")
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class OrderSettlement {

    /**
     *
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 创建时间
     *
     */
    @TableField(value = "create_time")
    private Date createTime;
    /**
     * 更新时间
     *
     */
    @TableField(value = "update_time")
    private Date updateTime;
    /**
     * DeleteTime
     *
     */
    @TableField(value = "delete_time")
    private Date deleteTime;
    /**
     * OrderId
     */
    @TableField(value = "order_id")
    private String orderId;
    /**
     * 结算状态 01：待支付，02:支付中 03:已结算 04：用户申请修改
     */
    @TableField(value = "settlement_status")
    private EnumOrderSettlementStatus settlementStatus;
    /**
     * 结算类型 01:完好 02:损坏 03:丢失 04:违约
     */
    @TableField(value = "settlement_type")
    private EnumOrderSettlementType settlementType;
    /**
     * 用户申请修改次数
     */
    @TableField(value = "apply_modify_times")
    private Integer applyModifyTimes;
    /**
     * 丢失金额
     */
    @TableField(value = "lose_amount")
    private BigDecimal loseAmount;
    /**
     * 损坏金额
     */
    @TableField(value="damage_amount")
    private BigDecimal damageAmount;
    /**
     * 违约金
     * 
     */
    @TableField(value="penalty_amount")
    private BigDecimal penaltyAmount;
    /**
     * 解冻押金
     * 
     */
    @TableField(value="deposit")
    private BigDecimal deposit;
    /**
     * 已退租金
     *
     */
    @TableField(value="rent")
    private BigDecimal rent;
    /**
     * 应付租金
     *
     */
    @TableField(value="payment_rent")
    private BigDecimal paymentRent;
    /**
     * 冻结转支付金额
     * 
     */
    @TableField(value="transfer_payment")
    private BigDecimal transferPayment;
    /**
     * 冻结押金
     * 
     */
    @TableField(value="real_deposit")
    private BigDecimal realDeposit;
    /**
     * 提前支付租金
     * 
     */
    @TableField(value="before_rent")
    private BigDecimal beforeRent;
    /**
     * 请求商户订单号
     * 
     */
    @TableField(value="out_trand_no")
    private String outTrandNo;
    /**
     * 支付宝交易号
     *
     */
    @TableField(value = "trade_no")
    private String tradeNo;
    /**
     * 支付时间
     */
    @TableField(value = "payment_time")
    private Date paymentTime;


    /**
     * 支付方式
     */
    @TableField(value = "split_time")
    private Date splitTime;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
