package com.rent.model.components;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rent.common.enums.components.EnumAliPayStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 支付宝订单支付
 *
 * @author xiaoyao
 * @Date 2020-06-24 16:11
 */
@Data
@Accessors(chain = true)
@TableName("ct_alipay_trade_pay")
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class AlipayTradePay {

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 标题
     */
    @TableField(value = "subject")
    private String subject;
    /**
     * 支付授权码
     */
    @TableField(value = "auth_code")
    private String authCode;
    /**
     * 商户订单号
     */
    @TableField(value = "out_trade_no")
    private String outTradeNo;
    /**
     * 支付宝交易号
     */
    @TableField(value = "trade_no")
    private String tradeNo;
    /**
     * 订单号
     */
    @TableField(value = "order_id")
    private String orderId;
    /**
     * 期数
     */
    @TableField(value = "period")
    private String period;
    /**
     * 金额
     */
    @TableField(value = "amount")
    private BigDecimal amount;

    /**
     * 状态
     */
    @TableField(value = "status")
    private EnumAliPayStatus status;
    /**
     * 入参
     */
    @TableField(value = "request", select = false)
    private String request;
    /**
     * 返回参数
     */
    @TableField(value = "response", select = false)
    private String response;
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

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
