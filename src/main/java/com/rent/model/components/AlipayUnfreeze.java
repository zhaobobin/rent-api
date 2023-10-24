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
 * 支付宝资金授权解冻
 *
 * @author xiaoyao
 * @Date 2020-06-24 16:12
 */
@Data
@Accessors(chain = true)
@TableName("ct_alipay_unfreeze")
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class AlipayUnfreeze {

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
     * 请求参数
     */
    @TableField(value = "request", select = false)
    private String request;
    /**
     * 响应参数
     */
    @TableField(value = "response", select = false)
    private String response;
    /**
     * 支付宝资金授权订单号
     */
    @TableField(value = "auth_no")
    private String authNo;
    /**
     * 所属租单订单号
     */
    @TableField(value = "order_id")
    private String orderId;
    /**
     * 支付宝资金操作流水号
     */
    @TableField(value = "operation_id")
    private String operationId;
    /**
     * Uid
     */
    @TableField(value = "uid")
    private String uid;
    /**
     * 解冻流水号
     */
    @TableField(value = "unfreeze_request_no")
    private String unfreezeRequestNo;
    /**
     * 解冻金额
     */
    @TableField(value = "amount")
    private BigDecimal amount;
    /**
     * 解冻说明
     */
    @TableField(value = "remark")
    private String remark;
    /**
     * INIT("00", "初始化"),
     * PAYING("01", "支付中"),
     * SUCCESS("02", "成功"),
     * FAILED("03", "失败"),
     */
    @TableField(value = "status")
    private EnumAliPayStatus status;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
