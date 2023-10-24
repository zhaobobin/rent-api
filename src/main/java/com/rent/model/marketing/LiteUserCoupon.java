
package com.rent.model.marketing;

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
 * 用户优惠券表
 *
 * @author zhao
 * @Date 2020-07-07 15:04
 */
@Data
@Accessors(chain = true)
@TableName("ct_lite_user_coupon")
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class LiteUserCoupon {


    /**
     * Id
     * 
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    /**
     * TemplateId
     * 
     */
    @TableField(value="template_id")
    private Long templateId;
    /**
     * 优惠金额
     * 
     */
    @TableField(value="discount_amount")
    private BigDecimal discountAmount;
    /**
     * 编号
     * 
     */
    @TableField(value="code")
    private String code;
    /**
     * 领取用户ID
     * 
     */
    @TableField(value="uid")
    private String uid;
    /**
     * 领取用户的手机号码
     * 
     */
    @TableField(value="phone")
    private String phone;
    /**
     * 用户领取时间
     * 
     */
    @TableField(value="receive_time")
    private Date receiveTime;
    /**
     * 领取方式 REQUEST：用户主动领取 ASSIGN：系统派发
     * 
     */
    @TableField(value="receive_type")
    private String receiveType;
    /**
     * 有效期开始时间
     * 
     */
    @TableField(value="start_time")
    private Date startTime;
    /**
     * 有效期结束时间
     * 
     */
    @TableField(value="end_time")
    private Date endTime;
    /**
     * 订单ID
     * 
     */
    @TableField(value="order_id")
    private String orderId;
    /**
     * 用户使用时间
     * 
     */
    @TableField(value="use_time")
    private Date useTime;
    /**
     * PackageId
     * 
     */
    @TableField(value="package_id")
    private Long packageId;
    /**
     * 来自的大礼包名称
     * 
     */
    @TableField(value="package_name")
    private String packageName;
    /**
     * 状态：UNUSED：未使用 USED：已使用
     * 
     */
    @TableField(value="status")
    private String status;

    @TableField(value="channel_id")
    private String channelId;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
