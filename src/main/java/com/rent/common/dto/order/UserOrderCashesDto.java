package com.rent.common.dto.order;

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
import java.util.List;

/**
 * 用户订单金额
 *
 * @author xiaoyao
 * @Date 2020-06-15 17:08
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "用户订单金额")
public class UserOrderCashesDto implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * Id
     */
    @Schema(description = "Id")
    private Long id;

    /**
     * CreateTime
     */
    @Schema(description = "CreateTime")
    private Date createTime;

    /**
     * UpdateTime
     */
    @Schema(description = "UpdateTime")
    private Date updateTime;

    /**
     * DeleteTime
     */
    @Schema(description = "DeleteTime")
    private Date deleteTime;

    /**
     * 所属订单id，可能为续租的订单等
     */
    @Schema(description = "所属订单id，可能为续租的订单等")
    private String orderId;

    /**
     * 总押金 商家设置的起始押金金额
     */
    @Schema(description = "总押金 商家设置的起始押金金额")
    private BigDecimal totalDeposit;

    /**
     * 押金减免金额
     */
    @Schema(description = "押金减免金额")
    private BigDecimal depositReduction;

    /**
     * 实付押金 总的押金-芝麻信用押金减免额/实名认证押金减免额，商家可以修改
     */
    @Schema(description = "实付押金 总的押金-芝麻信用押金减免额/实名认证押金减免额，商家可以修改")
    private BigDecimal deposit;

    /**
     * 店铺优惠券减免 符合优惠券条件，减免一定金额
     */
    @Schema(description = "店铺优惠券减免 符合优惠券条件，减免一定金额")
    private BigDecimal couponReduction;

    /**
     * 满减金额 符合满减/送条件，减免一定金额 买断订单为 已付租金
     */
    @Schema(description = "满减金额 符合满减/送条件，减免一定金额 买断订单为 已付租金 ")
    private BigDecimal fullReduction;

    /**
     * 平台优惠券减免 符合平台优惠券条件，减免一定金额
     */
    @Schema(description = "平台优惠券减免 符合平台优惠券条件，减免一定金额")
    private BigDecimal platformCouponReduction;

    @Schema(description = "优惠详情")
    private List<UserOrderCouponDto> userOrderCouponDtos;

    /**
     * 平台满减 符合平台满减送条件，减免一定金额
     */
    @Schema(description = "平台满减 符合平台满减送条件，减免一定金额")
    private BigDecimal platformFullReduction;

    /**
     * 活动减免金额
     */
    @Schema(description = "活动减免金额")
    private BigDecimal activityReduction;

    /**
     * 租金单价 商家设置的实际租金单价
     */
    @Schema(description = "租金单价 商家设置的实际租金单价")
    private BigDecimal rent;

    /**
     * 总租金 租金单价*天数*数量 + 溢价*天数*数量  买断总价
     */
    @Schema(description = "总租金 租金单价*天数*数量 + 溢价*天数*数量  买断总价")
    private BigDecimal totalRent;

    /**
     * 结算租金 正常情况下即总租金，但当订单开始后，可能被商家各种改，为最终结算时所需要支付的租金   买断订单若发生修改则为原买断总价
     */
    @Schema(description = "结算租金 正常情况下即总租金，但当订单开始后，可能被商家各种改，为最终结算时所需要支付的租金   买断订单若发生修改则为原买断总价")
    private BigDecimal settlementRent;

    /**
     * 订单总金额 买断尾款 本次买断订单需支付的
     */
    @Schema(description = "订单总金额 买断尾款 本次买断订单需支付的")
    private BigDecimal total;

    /**
     * 溢价总金额
     */
    @Schema(description = "溢价总金额")
    private BigDecimal totalPremium;

    /**
     * 物流费用 物流所需要的金额
     */
    @Schema(description = "物流费用 物流所需要的金额")
    private BigDecimal freightPrice;

    /**
     * 丢失赔偿金额
     */
    @Schema(description = "丢失赔偿金额")
    private BigDecimal lostPrice;

    /**
     * 损坏赔偿金额
     */
    @Schema(description = "损坏赔偿金额")
    private BigDecimal damagePrice;

    /**
     * 违约赔偿金额
     */
    @Schema(description = "违约赔偿金额")
    private BigDecimal penaltyAmount;

    /**
     * 消费者申请退款的金额 消费者发起退款所要求的金额
     */
    @Schema(description = "消费者申请退款的金额 消费者发起退款所要求的金额")
    private BigDecimal refund;

    /**
     * 补充金额 当押金加租金都不足时，用户需要补充额外的金额
     */
    @Schema(description = "补充金额 当押金加租金都不足时，用户需要补充额外的金额")
    private BigDecimal supplementPrice;

    /**
     * 减免的物流费用 商家或平台减免的物流费用，商家修改物流费用，此处的值为原物流金额 - 商家修改后的物流金额
     */
    @Schema(description = "减免的物流费用 商家或平台减免的物流费用，商家修改物流费用，此处的值为原物流金额 - 商家修改后的物流金额")
    private BigDecimal freightReduction;

    /**
     * 应退款金额
     */
    @Schema(description = "应退款金额")
    private BigDecimal shouldRefundPrice;

    /**
     * 增值费
     */
    @Schema(description = "增值费")
    private BigDecimal additionalServicesPrice;

    /**
     * 原始月租金
     */
    @Schema(description = "原始月租金")
    private BigDecimal originalRent;

    /**
     * 日租单价*天数
     */
    @Schema(description = "日租单价*天数")
    private BigDecimal originalTotalRent;

    /**
     * 首次冻结金额 押金加第一期支付金额
     */
    @Schema(description = "首次冻结金额 押金加第一期支付金额")
    private BigDecimal freezePrice;

    /**
     * 信用减免金额
     */
    @Schema(description = "信用减免金额")
    private BigDecimal creditDeposit;


    @Schema(description = "订单唤回优惠券减免金额")
    private BigDecimal couponRecallReduction;

    @Schema(description = "首期订单减免")
    private BigDecimal retainDeductionAmount;

    @Schema(description = "已支付押金")
    private BigDecimal paidDeposit;

    @Schema(description = "待支付押金")
    private BigDecimal unPaidDeposit;


    @Schema(description = "账单逾期违约金")
    private BigDecimal orderOverdueAmount;

    @Schema(description = "归还逾期违约金")
    private BigDecimal returnOverdueAmount;

    @Schema(description = "折扣金额")
    private BigDecimal discountAmount;


    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
