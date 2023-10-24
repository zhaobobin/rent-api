
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
 * 渠道分账信息
 *
 * @author zhao
 * @Date 2020-08-11 09:59
 */
@Data
@Accessors(chain = true)
@TableName("ct_channel_split_bill")
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ChannelSplitBill {


    /**
     * Id
     * 
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    /**
     * 账期ID
     *
     */
    @TableField(value="account_period_id")
    private Long accountPeriodId;

    /**
     * 分账订单ID
     */
    @TableField(value="order_id")
    private String orderId;

    /**
     * 当前期数
     */
    @TableField(value="period")
    private Integer period;

    /**
     * 分账订单所属用户ID
     */
    @TableField(value="uid")
    private String uid;
    /**
     * 渠道ID
     * 
     */
    @TableField(value="marketing_id")
    private String marketingId;
    /**
     * 分账比例
     * 
     */
    @TableField(value="scale")
    private BigDecimal scale;
    /**
     * 用户支付金额
     * 
     */
    @TableField(value="user_pay_amount")
    private BigDecimal userPayAmount;
    /**
     * 分给渠道的金额
     * 
     */
    @TableField(value="channel_amount")
    private BigDecimal channelAmount;

    /**
     * 订单状态
     * 
     */
    @TableField(value="status")
    private String status;
    /**
     * 用户支付时间
     * 
     */
    @TableField(value="user_pay_time")
    private Date userPayTime;
    /**
     * 创建时间
     * 
     */
    @TableField(value="create_time")
    private Date createTime;
    /**
     * 更新时间
     * 
     */
    @TableField(value="update_time")
    private Date updateTime;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
