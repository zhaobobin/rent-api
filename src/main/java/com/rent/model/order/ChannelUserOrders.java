package com.rent.model.order;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rent.common.enums.order.EnumOrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户订单
 *
 * @author xiaoyao
 * @Date 2020-06-10 17:02
 */
@Data
@Accessors(chain = true)
@TableName("ct_channel_user_orders")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChannelUserOrders {

    private static final long serialVersionUID = 1L;

    /**
     * Id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 下单用户姓名
     */
    @TableField(value = "user_name")
    private String userName;
    /**
     * 手机号
     */
    @TableField(value = "phone")
    private String phone;
    /**
     * CreateTime
     */
    @TableField(value = "create_time")
    private Date createTime;
    /**
     * 渠道id
     */
    @TableField(value = "marketing_channel_id")
    private String marketingChannelId;

    /**
     * 渠道链接ID，可以认定为这是店铺特定ID
     */
    @TableField(value = "marketing_id")
    private String marketingId;

    /**
     * 订单号
     */
    @TableField(value = "order_id")
    private String orderId;
    /**
     * 订单状态
     */
    @TableField(value = "status")
    private EnumOrderStatus status;
    /**
     * 结算状态
     */
    @TableField(value = "settle_status")
    private String settleStatus;
    /**
     * 商品名称
     */
    @TableField(value = "product_name")
    private String productName;
    /**
     * 商家名称
     */
    @TableField(value = "shop_name")
    private String shopName;
    /**
     * 总租金
     */
    @TableField(value = "total_amount")
    private BigDecimal totalAmount;
    /**
     * 分账比例
     */
    @TableField(value = "scale")
    private BigDecimal scale;
    /**
     * 总期数
     */
    @TableField(value = "total_periods")
    private Integer totalPeriods;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

}
