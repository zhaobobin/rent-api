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
 * 转账记录
 *
 * @author xiaoyao
 * @Date 2020-07-03 10:49
 */
@Data
@Accessors(chain = true)
@TableName("ct_alipay_transfer_record")
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class AlipayTransferRecord {

    /**
     * Id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 外部订单号
     */
    @TableField(value = "out_biz_no")
    private String outBizNo;
    /**
     * 支付宝账号
     */
    @TableField(value = "identity")
    private String identity;
    /**
     * 支付宝实名信息
     */
    @TableField(value = "name")
    private String name;
    /**
     * uid
     */
    @TableField(value = "uid")
    private String uid;
    /**
     * 支付宝userId
     */
    @TableField(value = "user_id")
    private String userId;
    /**
     * 金额
     */
    @TableField(value = "amount")
    private BigDecimal amount;
    /**
     * 备注
     */
    @TableField(value = "remark")
    private String remark;
    /**
     * 状态
     */
    @TableField(value = "status")
    private EnumAliPayStatus status;
    /**
     * 错误信息
     */
    @TableField(value = "error_msg")
    private String errorMsg;
    /**
     * 请求参数
     */
    @TableField(value = "req_params")
    private String reqParams;
    /**
     * 响应参数
     */
    @TableField(value = "resp")
    private String resp;
    /**
     * 查询错误信息
     */
    @TableField(value = "query_error_info")
    private String queryErrorInfo;
    /**
     * 查询响应结果
     */
    @TableField(value = "query_resp")
    private String queryResp;
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
