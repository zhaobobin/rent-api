package com.rent.model.order;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rent.common.enums.order.EnumAccountPeriodOperator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.util.Date;

/**
 * 账期流转记录
 * @author xiaotong
 */
@Data
@Accessors(chain = true)
@TableName("ct_account_period_progress")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountPeriodProgress {


    private static final long serialVersionUID = 1L;
    /**
     * Id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 账期ID
     */
    @TableField(value = "account_period_id")
    private Long accountPeriodId;
    /**
     * 操作人信息
     */
    @TableField(value = "operator")
    private String operator;
    /**
     * 流转状态
     */
    @TableField(value = "status")
    private EnumAccountPeriodOperator status;
    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;


    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
