package com.rent.model.order;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户支付押金表修改日志
 * @author xiaotong
 */
@Data
@Accessors(chain = true)
@TableName("ct_order_pay_deposit_log")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderPayDepositLog {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "order_id")
    private String orderId;

    @TableField(value = "origin_amount")
    private BigDecimal originAmount;

    @TableField(value = "after_amount")
    private BigDecimal afterAmount;

    @TableField(value = "backstage_user_id")
    private Long backstageUserId;

    @TableField(value = "backstage_user_name")
    private String backstageUserName;

    @TableField(value = "remark")
    private String remark;

    @TableField(value = "create_time")
    private Date createTime;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}