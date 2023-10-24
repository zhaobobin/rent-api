package com.rent.model.components;

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
 * 小程序订单中心商品信息
 *
 * @author xiaoyao
 * @Date 2020-06-24 16:12
 */
@Data
@Accessors(chain = true)
@TableName("ct_order_center_sync_log")
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class OrderCenterSyncLog {

    /**
     * Id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 订单ID
     */
    @TableField(value = "order_id")
    private String orderId;
    /**
     * 产品ID
     */
    @TableField(value = "product_id")
    private String productId;

    /**
     * 同步类型("MERCHANT_DELIVERD"
     * 同步类型("MERCHANT_DELIVERD","商家发货中")("MERCHANT_FINISHED","订单已完成")("MERCHANT_LOANING","租赁中")
     */
    @TableField(value = "type")
    private String type;
    /**
     * 状态
     */
    @TableField(value = "state")
    private String state;
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
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 订单渠道
     */
    @TableField(value = "channel_id")
    private String channelId;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
