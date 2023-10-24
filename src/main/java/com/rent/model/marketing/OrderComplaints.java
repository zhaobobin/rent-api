
package com.rent.model.marketing;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rent.common.enums.marketing.OrderComplaintStatusEnum;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.util.Date;

@Data
@Accessors(chain = true)
@TableName("ct_order_complaints")
public class OrderComplaints {

    private static final long serialVersionUID = 1L;


    /**
     * Id
     * 
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    /**
     * 用户ID
     * 
     */
    @TableField(value="uid")
    private String uid;
    /**
     * 用户手机
     * 
     */
    @TableField(value="telphone")
    private String telphone;
    /**
     * 用户名称
     * 
     */
    @TableField(value="name")
    private String name;
    /**
     * 投诉内容
     * 
     */
    @TableField(value="content")
    private String content;
    /**
     * 投诉类型
     * 
     */
    @TableField(value="type_id")
    private Long typeId;
    /**
     * 投诉订单ID
     * 
     */
    @TableField(value="order_id")
    private String orderId;
    /**
     * 投诉商户ID
     * 
     */
    @TableField(value="shop_id")
    private String shopId;
    /**
     * 平台处理结果
     * 
     */
    @TableField(value="result")
    private String result;
    /**
     * 操作人
     * 
     */
    @TableField(value="operator")
    private String operator;
    /**
     * CreateTime
     * 
     */
    @TableField(value="create_time")
    private Date createTime;
    /**
     * UpdateTime
     * 
     */
    @TableField(value="update_time")
    private Date updateTime;
    /**
     * DeleteTime
     * 
     */
    @TableField(value="delete_time")
    private Date deleteTime;
    /**
     * 来源渠道 006
     * 
     */
    @TableField(value="channel")
    private String channel;
    /**
     * 处理状态 0未处理 1已处理
     * 
     */
    @TableField(value="status")
    private OrderComplaintStatusEnum status;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
