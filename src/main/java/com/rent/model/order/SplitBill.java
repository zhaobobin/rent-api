
package com.rent.model.order;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rent.common.enums.product.EnumSplitBillAppVersion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 分账信息
 *
 * @author zhao
 * @Date 2020-08-11 09:59
 */
@Data
@Accessors(chain = true)
@TableName("ct_split_bill")
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class SplitBill {


    /**
     * Id
     * 
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    /**
     * 分账规则ID
     * 
     */
    @TableField(value="split_bill_rule_id")
    private Long splitBillRuleId;

    /**
     * 分账规则ID
     *
     */
    @TableField(value="account_period_id")
    private Long accountPeriodId;


    /**
     * 分账单号
     * 
     */
    @TableField(value="order_no")
    private String orderNo;

    /**
     * 分账订单ID
     */
    @TableField(value="order_id")
    private String orderId;

    /**
     * 期数
     */
    @TableField(value="period")
    private Integer period;

    /**
     * 分账订单所属用户ID
     */
    @TableField(value="uid")
    private String uid;
    /**
     * 店铺ID
     * 
     */
    @TableField(value="shop_id")
    private String shopId;
    /**
     * 分账类型
     * 
     */
    @TableField(value="type")
    private String type;

    /**
     * 支付宝账号
     * 
     */
    @TableField(value="identity")
    private String identity;
    /**
     * 支付宝实名信息
     * 
     */
    @TableField(value="name")
    private String name;
    /**
     * 分账比例
     * 
     */
    @TableField(value="scale")
    private BigDecimal scale;
    /**
     * 用户支付金额
     * 
     */
    @TableField(value="user_pay_amount")
    private BigDecimal userPayAmount;
    /**
     * 分给商家的金额
     * 
     */
    @TableField(value="trans_amount")
    private BigDecimal transAmount;

    /**
     * 分给商家的金额
     *
     */
    @TableField(value="app_version")
    private EnumSplitBillAppVersion appVersion;
    /**
     * 创建时间
     * 
     */
    @TableField(value="status")
    private String status;
    /**
     * 用户支付时间
     * 
     */
    @TableField(value="user_pay_time")
    private Date userPayTime;
    /**
     * 计划支付时间
     * 
     */
    @TableField(value="plan_pay_time")
    private Date planPayTime;
    /**
     * 实际支付时间
     * 
     */
    @TableField(value="real_pay_time")
    private Date realPayTime;
    /**
     * 创建时间
     * 
     */
    @TableField(value="create_time")
    private Date createTime;
    /**
     * 更新时间
     * 
     */
    @TableField(value="update_time")
    private Date updateTime;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
