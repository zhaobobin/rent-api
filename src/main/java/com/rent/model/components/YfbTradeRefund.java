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
 * 订单退款信息
 *
 * @author xiaoyao
 * @Date 2020-07-02 10:57
 */
@Data
@Accessors(chain = true)
@TableName("ct_yfb_trade_refund")
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class YfbTradeRefund {

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 订单编号
     */
    @TableField(value = "order_id")
    private String orderId;
    /**
     * 订单支付时传入的商户订单号
     */
    @TableField(value = "out_trade_no")
    private String outTradeNo;
    /**
     * 支付宝交易号
     */
    @TableField(value = "trade_no")
    private String tradeNo;
    /**
     * 标识一次退款请求，同一笔交易多次退款需要保证唯一，如需部分退款，则此参数必传。
     */
    @TableField(value = "out_request_no")
    private String outRequestNo;
    /**
     * 退款金额
     */
    @TableField(value = "refund_amount")
    private BigDecimal refundAmount;
    /**
     * 退款原因
     */
    @TableField(value = "refund_reason")
    private String refundReason;
    /**
     * 请求参数
     */
    @TableField(value = "request", select = false)
    private String request;
    /**
     * 响应
     */
    @TableField(value = "response", select = false)
    private String response;
    /**
     * 支付状态 00：初始化 01：支付中 02：成功 03：失败
     */
    @TableField(value = "status")
    private EnumAliPayStatus status;
    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;
    /**
     * 创建时间
     */
    @TableField(value = "update_time")
    private Date updateTime;


    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
