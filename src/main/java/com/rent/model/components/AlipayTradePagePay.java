package com.rent.model.components;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rent.common.enums.components.EnumAliPayStatus;
import com.rent.common.enums.components.EnumTradeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.math.BigDecimal;
import java.util.Date;

/**
 * APP支付记录
 *
 * @author xiaoyao
 * @Date 2020-06-24 16:11
 */
@Data
@Accessors(chain = true)
@TableName("ct_alipay_trade_page_pay")
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class AlipayTradePagePay {

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 订单号
     */
    @TableField(value = "shop_id")
    private String shopId;

    /**
     * 商户订单号
     */
    @TableField(value = "out_trade_no")
    private String outTradeNo;
    /**
     * 商户本次资金操作的请求流水号
     */
    @TableField(value = "trade_no")
    private String tradeNo;
    /**
     * 主题
     */
    @TableField(value = "subject")
    private String subject;
    /**
     * 请求参数
     */
    @TableField(value = "request", select = false)
    private String request;
    /**
     * 返回参数
     */
    @TableField(value = "response", select = false)
    private String response;
    /**
     * 交易类型EnumTradeType 01:下单 02：关单 03：账单代扣 04：账单主动支付 05：结算单支付 06：充值 07：买断 08：商家分账
     */
    @TableField(value = "trade_type")
    private EnumTradeType tradeType;
    /**
     * 交易金额
     */
    @TableField(value = "amount")
    private BigDecimal amount;
    /**
     * 00:初始化  01：支付中 02：支付成功 03：支付失败
     */
    @TableField(value = "status")
    private EnumAliPayStatus status;
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
