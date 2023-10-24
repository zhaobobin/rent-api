
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

import java.util.Date;

/**
 * 订单状态流转
 *
 * @author xiaoyao
 * @Date 2020-07-23 16:06
 */
@Data
@Accessors(chain = true)
@TableName("ct_user_orders_status_tranfer")
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UserOrdersStatusTranfer {


    /**
     * Id
     * 
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    /**
     * 订单编号
     * 
     */
    @TableField(value="order_id")
    private String orderId;
    /**
     * uid
     *
     */
    @TableField(value="uid")
    private String uid;
    /**
     * 操作人id
     * 
     */
    @TableField(value="operator_id")
    private String operatorId;
    /**
     * 操作人姓名
     *
     */
    @TableField(value="operator_name")
    private String operatorName;
    /**
     * 原状态
     * 
     */
    @TableField(value="old_status")
    private String oldStatus;
    /**
     * 新状态
     * 
     */
    @TableField(value="new_status")
    private String newStatus;
    /**
     * 操作
     *
     */
    @TableField(value="operate")
    private String operate;
    /**
     * 创建时间
     * 
     */
    @TableField(value="create_time")
    private Date createTime;
    /**
     * 删除时间
     * 
     */
    @TableField(value="delete_time")
    private Date deleteTime;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
