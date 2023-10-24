package com.rent.model.order;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rent.common.enums.order.EnumOrderBuyOutStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

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
@TableName("ct_user_order_buy_out")
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UserOrderBuyOut {

    /**
     * Id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 用户ID
     */
    @TableField(value = "uid")
    private String uid;
    /**
     * 租赁订单ID
     */
    @TableField(value = "order_id")
    private String orderId;
    /**
     * 买断订单ID
     */
    @TableField(value = "buy_out_order_id")
    private String buyOutOrderId;
    /**
     * 店铺id
     */
    @TableField(value = "shop_id")
    private String shopId;
    /**
     * 市场价，从SKU同步过来
     */
    @TableField(value = "market_price")
    private BigDecimal marketPrice;
    /**
     * 原定销售价格，从SKU同步过来
     */
    @TableField(value = "sale_price")
    private BigDecimal salePrice;
    /**
     * 实际销售价格（商家和用户沟通后可修改）
     */
    @TableField(value = "real_sale_price")
    private BigDecimal realSalePrice;
    /**
     * 已支付租金
     */
    @TableField(value = "paid_rent")
    private BigDecimal paidRent;
    /**
     * 应支付尾款（实际销售价格-已支付租金）
     */
    @TableField(value = "end_fund")
    private BigDecimal endFund;
    /**
     * 店铺优惠券抵扣金额
     */
    @TableField(value = "shop_coupon_reduction")
    private BigDecimal shopCouponReduction;
    /**
     * 平台优惠抵扣金额
     */
    @TableField(value = "platform_coupon_reduction")
    private BigDecimal platformCouponReduction;
    /**
     * 买断订单状态 01：用户取消。 02：关闭。  03：已完成。04:待支付。05：支付中（等待回调）
     */
    @TableField(value = "state")
    private EnumOrderBuyOutStatus state;
    /**
     * 商户传入的资金交易号
     */
    @TableField(value = "out_trans_no")
    private String outTransNo;
    /**
     * 备注
     */
    @TableField(value = "remark")
    private String remark;
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
     * 渠道来源
     */
    @TableField(value = "channel_id")
    private String channelId;
    /**
     * 分账时间
     */
    @TableField(value = "split_bill_time")
    private Date splitBillTime;


    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
