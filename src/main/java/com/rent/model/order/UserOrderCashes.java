package com.rent.model.order;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户订单金额
 *
 * @author xiaoyao
 * @Date 2020-06-15 17:08
 */
@Data
@Accessors(chain = true)
@TableName("ct_user_order_cashes")
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UserOrderCashes {

    private static final long serialVersionUID = 1L;

    /**
     * Id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * CreateTime
     */
    @TableField(value = "create_time")
    private Date createTime;
    /**
     * UpdateTime
     */
    @TableField(value = "update_time")
    private Date updateTime;
    /**
     * DeleteTime
     */
    @TableField(value = "delete_time")
    private Date deleteTime;
    /**
     * 所属订单id，可能为续租的订单等
     */
    @TableField(value = "order_id")
    private String orderId;
    /**
     * 总押金 商家设置的起始押金金额
     */
    @TableField(value = "total_deposit")
    private BigDecimal totalDeposit;
    /**
     * 押金减免金额
     */
    @TableField(value = "deposit_reduction")
    private BigDecimal depositReduction;
    /**
     * 实付押金 总的押金-芝麻信用押金减免额/实名认证押金减免额，商家可以修改
     */
    @TableField(value = "deposit")
    private BigDecimal deposit;
    /**
     * 店铺优惠券减免 符合优惠券条件，减免一定金额
     */
    @TableField(value = "coupon_reduction")
    private BigDecimal couponReduction;
    /**
     * 满减金额 符合满减/送条件，减免一定金额 买断订单为 已付租金
     */
    @TableField(value = "full_reduction")
    private BigDecimal fullReduction;
    /**
     * 平台优惠券减免 符合平台优惠券条件，减免一定金额
     */
    @TableField(value = "platform_coupon_reduction")
    private BigDecimal platformCouponReduction;
    /**
     * 平台满减 符合平台满减送条件，减免一定金额
     */
    @TableField(value = "platform_full_reduction")
    private BigDecimal platformFullReduction;
    /**
     * 活动减免金额
     */
    @TableField(value = "activity_reduction")
    private BigDecimal activityReduction;
    /**
     * 租金单价 商家设置的实际租金单价
     */
    @TableField(value = "rent")
    private BigDecimal rent;
    /**
     * 总租金 租金单价*天数*数量 + 溢价*天数*数量  买断总价
     */
    @TableField(value = "total_rent")
    private BigDecimal totalRent;
    /**
     * 结算租金 正常情况下即总租金，但当订单开始后，可能被商家各种改，为最终结算时所需要支付的租金   买断订单若发生修改则为原买断总价
     */
    @TableField(value = "settlement_rent")
    private BigDecimal settlementRent;
    /**
     * 订单总金额 买断尾款 本次买断订单需支付的
     */
    @TableField(value = "total")
    private BigDecimal total;
    /**
     * 溢价总金额
     */
    @TableField(value = "total_premium")
    private BigDecimal totalPremium;
    /**
     * 物流费用 物流所需要的金额
     */
    @TableField(value = "freight_price")
    private BigDecimal freightPrice;
    /**
     * 丢失赔偿金额
     */
    @TableField(value = "lost_price")
    private BigDecimal lostPrice;
    /**
     * 损坏赔偿金额
     */
    @TableField(value = "damage_price")
    private BigDecimal damagePrice;
    /**
     * 违约赔偿金额
     */
    @TableField(value = "penalty_amount")
    private BigDecimal penaltyAmount;
    /**
     * 消费者申请退款的金额 消费者发起退款所要求的金额
     */
    @TableField(value = "refund")
    private BigDecimal refund;
    /**
     * 补充金额 当押金加租金都不足时，用户需要补充额外的金额
     */
    @TableField(value = "supplement_price")
    private BigDecimal supplementPrice;
    /**
     * 减免的物流费用 商家或平台减免的物流费用，商家修改物流费用，此处的值为原物流金额 - 商家修改后的物流金额
     */
    @TableField(value = "freight_reduction")
    private BigDecimal freightReduction;
    /**
     * 应退款金额
     */
    @TableField(value = "should_refund_price")
    private BigDecimal shouldRefundPrice;
    /**
     * 增值费
     */
    @TableField(value = "additional_services_price")
    private BigDecimal additionalServicesPrice;

    /**
     * 原始月租金
     */
    @TableField(value = "original_rent")
    private BigDecimal originalRent;
    /**
     * 日租单价*天数
     */
    @TableField(value = "original_total_rent")
    private BigDecimal originalTotalRent;
    /**
     * 首次冻结金额 押金加第一期支付金额
     */
    @TableField(value = "freeze_price")
    private BigDecimal freezePrice;
    /**
     * 订单唤回优惠券减免金额
     */
    @TableField(value = "coupon_recall_reduction")
    private BigDecimal couponRecallReduction;

    /**
     * 首期订单减免
     */
    @TableField(value = "retain_deduction_amount")
    private BigDecimal retainDeductionAmount;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
