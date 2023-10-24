package com.rent.common.dto.order;

import com.rent.common.enums.order.EnumOrderBuyOutStatus;
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
 * 用户买断
 *
 * @author xiaoyao
 * @Date 2020-06-23 14:50
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "用户买断")
public class UserOrderBuyOutDto implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 用户ID
     */
    @Schema(description = "用户ID")
    private String uid;

    /**
     * 租赁订单ID
     */
    @Schema(description = "租赁订单ID")
    private String orderId;

    /**
     * 买断订单ID
     */
    @Schema(description = "买断订单ID")
    private String buyOutOrderId;

    /**
     * 市场价，从SKU同步过来
     */
    @Schema(description = "市场价，从SKU同步过来")
    private BigDecimal marketPrice;

    /**
     * 原定销售价格，从SKU同步过来
     */
    @Schema(description = "原定销售价格，从SKU同步过来")
    private BigDecimal salePrice;

    /**
     * 实际销售价格（商家和用户沟通后可修改）
     */
    @Schema(description = "实际销售价格（商家和用户沟通后可修改）")
    private BigDecimal realSalePrice;

    /**
     * 已支付租金
     */
    @Schema(description = "已支付租金")
    private BigDecimal paidRent;

    /**
     * 应支付尾款（实际销售价格-已支付租金）
     */
    @Schema(description = "应支付尾款（实际销售价格-已支付租金）")
    private BigDecimal endFund;

    /**
     * 买断订单状态 01：用户取消。 02：关闭。  03：已完成。04:待支付。05：支付中（等待回调）
     */
    @Schema(description = "买断订单状态 01：用户取消。 02：关闭。  03：已完成。04:待支付。05：支付中（等待回调）")
    private EnumOrderBuyOutStatus state;

    /**
     * 商户传入的资金交易号
     */
    @Schema(description = "商户传入的资金交易号")
    private String outTransNo;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;

    /**
     * CreateTime
     */
    @Schema(description = "CreateTime")
    private Date createTime;

    /**
     * 渠道来源
     */
    @Schema(description = "渠道来源")
    private String channelId;

    @Schema(description = "商家结算金额")
    private BigDecimal toShopAmount;

    @Schema(description = "平台佣金")
    private BigDecimal toOpeAmount;

    @Schema(description = "结算状态")
    private String splitBillStatus;

    @Schema(description = "结算人")
    private String userName;

    @Schema(description = "结算时间")
    private Date splitBillTime;
    /**
     * 商品名称
     */
    @Schema(description = "商品名称")
    private String productName;

    @Schema(description = "优惠券抵扣金额")
    private BigDecimal couponReduction;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
