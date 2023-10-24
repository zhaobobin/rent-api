package com.rent.common.dto.order;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author zhaowenchao
 */
@Data
@Schema(description = "订单金额信息")
public class OrderPricesDto {
    /** sku价格（单天价格） */
    @Schema(description = "sku价格（单天价格）")
    private BigDecimal skuPrice;
    /** 租金（只算租金） */
    @Schema(description = "租金（只算租金）")
    private BigDecimal rentPrice;
    /** 总租金 */
    @Schema(description = "总租金")
    private BigDecimal totalRent;
    /** 总期数 */
    @Schema(description = "总期数")
    private int totalPeriods;
    /** 首期支付租金(包含运费和增值服务) */
    @Schema(description = "首期支付租金(包含运费和增值服务)")
    private BigDecimal firstPeriodsPrice;
    /** 第一期纯租金 */
    @Schema(description = "第一期纯租金")
    private BigDecimal firstPeriodsRentPrice;
    /** 剩余期支付金额 */
    @Schema(description = "剩余期支付金额")
    private BigDecimal otherPeriodsPrice;
    /** 剩余期数 */
    @Schema(description = "剩余期数")
    private int restPeriods;
    /** 平台优惠券金额 */
    @Schema(description = "平台优惠券金额")
    private BigDecimal platformCouponPrice;
    /** 店铺优惠券金额 */
    @Schema(description = "店铺优惠券金额")
    private BigDecimal shopCouponPrice;
    /** 总优惠 */
    @Schema(description = "总优惠")
    private BigDecimal couponPrice;
    /** 增值服务--对应安心服务 */
    @Schema(description = "增值服务--对应安心服务")
    private BigDecimal additionalServicesPrice;
    /** 物流费用 */
    @Schema(description = "物流费用")
    private BigDecimal logisticPrice;
    /** 原始押金 */
    @Schema(description = "原始押金")
    private BigDecimal depositAmount;
    /** 减免押金 */
    @Schema(description = "减免押金")
    private BigDecimal depositReduce;
    /** 原始月租金 */
    @Schema(description = "原始月租金")
    private BigDecimal originalMonthRentPrice;
    /** 订单总金额 */
    @Schema(description = "订单总金额")
    private BigDecimal orderAmount;
    /** 冻结金额 */
    @Schema(description = "冻结金额")
    private BigDecimal freezePrice;
    /** 应付押金 */
    @Schema(description = "应付押金")
    private BigDecimal deposit;
    /** 活动减免金额 */
    @Schema(description = "活动减免金额")
    private BigDecimal activityDiscountAmount;
    @Schema(description = "分期账单")
    private List<OrderByStagesDto> orderByStagesDtoList;
    @Schema(description = "延期券编号")
    private String extensionCouponCode;
    @Schema(description = "到期买断价格")
    private BigDecimal expireBuyOutPrice;
    @Schema(description = "首期减免后金额")
    private BigDecimal retainDeductionAmount;

    @Schema(description = "基础服务费")
    private BigDecimal baseServiceFee;
    @Schema(description = "折扣金额")
    private BigDecimal discountAmount;
}
