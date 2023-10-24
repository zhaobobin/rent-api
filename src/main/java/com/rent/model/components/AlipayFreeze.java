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
 * 预授权流水
 *
 * @author xiaoyao
 * @Date 2020-06-24 16:11
 */
@Data
@Accessors(chain = true)
@TableName("ct_alipay_freeze")
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class AlipayFreeze {

    /**
     * Id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 商户订单
     */
    @TableField(value = "order_id")
    private String orderId;
    /**
     * uid
     */
    @TableField(value = "uid")
    private String uid;
    /**
     * 付款方id
     */
    @TableField(value = "payer_user_id")
    private String payerUserId;
    /**
     * 支付订单
     */
    @TableField(value = "pay_id")
    private String payId;
    /**
     * 支付宝的资金授权订单号
     */
    @TableField(value = "auth_no")
    private String authNo;
    /**
     * 商户订单号
     */
    @TableField(value = "out_order_no")
    private String outOrderNo;
    /**
     * 商户本次资金操作的请求流水号
     */
    @TableField(value = "out_request_no")
    private String outRequestNo;
    /**
     * 支付宝的资金操作流水号
     */
    @TableField(value = "operation_id")
    private String operationId;
    /**
     * 请求参数
     */
    @TableField(value = "request", select = false)
    private String request;
    /**
     * 请求响应参数
     */
    @TableField(value = "response", select = false)
    private String response;
    /**
     * 本次操作冻结的金额，单位为：元（人民币）
     */
    @TableField(value = "amount")
    private BigDecimal amount;
    /**
     * 状态 01：支付中 02：成功 03：失败
     */
    @TableField(value = "status")
    private EnumAliPayStatus status;
    /**
     * 预授权成功时间
     */
    @TableField(value = "gmt_trans")
    private Date gmtTrans;
    /**
     * 预授权类型
     */
    @TableField(value = "pre_auth_type")
    private String preAuthType;
    /**
     * 信用冻结金额
     */
    @TableField(value = "credit_amount")
    private BigDecimal creditAmount;
    /**
     * 自有资金冻结金额
     */
    @TableField(value = "fund_amount")
    private BigDecimal fundAmount;
    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    private Date updateTime;
    /**
     * 创建时间
     */
    @TableField(value = "created_time")
    private Date createdTime;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
