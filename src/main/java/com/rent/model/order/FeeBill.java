
package com.rent.model.order;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rent.common.enums.order.EnumFeeBillType;
import com.rent.common.enums.order.EnumOderFeeBillInvoiceStatus;
import com.rent.common.enums.order.EnumOrderStatus;
import com.rent.common.enums.order.EnumSettleStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 租押分离收费信息
 * @author zhao
 * @Date 2020-08-11 09:59
 */
@Data
@Accessors(chain = true)
@TableName("ct_fee_bill")
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class FeeBill {

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    /**
     * 账期ID
     */
    @TableField(value="fund_flow_id")
    private Long fundFlowId;

    /**
     * 订单ID
     */
    @TableField(value="order_id")
    private String orderId;

    /**
     * 订单所属用户ID
     */
    @TableField(value="uid")
    private String uid;

    /**
     * 订单所属用户支付宝ID
     */
    @TableField(value="ali_uid")
    private String aliUid;

    /**
     * 店铺ID
     */
    @TableField(value="shop_id")
    private String shopId;

    /**
     * 费用金额
     */
    @TableField(value="amount")
    private BigDecimal amount;

    /**
     * 状态
     */
    @TableField(value="status")
    private EnumSettleStatus status;

    /**
     * 费用类型
     */
    @TableField(value="type")
    private EnumFeeBillType type;

    /**
     * 渠道
     */
    @TableField(value="channel_id")
    private String channelId;

    /**
     * 用户下单时间
     */
    @TableField(value="order_status")
    private EnumOrderStatus orderStatus;

    /**
     * 用户下单时间
     */
    @TableField(value="order_time")
    private Date orderTime;

    /**
     * 开发票状态
     */
    @TableField(value="ticket_status")
    private EnumOderFeeBillInvoiceStatus ticketStatus ;

    /**
     * 开发票状态
     */
    @TableField(value="ticket_id")
    private Long ticketId ;

    /**
     * 创建时间
     */
    @TableField(value="create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(value="update_time")
    private Date updateTime;

    /**
     * 更新时间
     */
    @TableField(value="backstage_user_id ")
    private String backstageUserId ;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
