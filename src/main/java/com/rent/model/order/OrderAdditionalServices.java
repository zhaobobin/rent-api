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
 * 订单增值服务信息
 *
 * @author xiaoyao
 * @Date 2020-06-16 10:08
 */
@Data
@Accessors(chain = true)
@TableName("ct_order_additional_services")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderAdditionalServices {

    private static final long serialVersionUID = 1L;

    /**
     * Id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * CreateTime
     */
    @TableField(value = "create_time")
    private Date createTime;
    /**
     * UpdateTime
     */
    @TableField(value = "update_time")
    private Date updateTime;
    /**
     * DeleteTime
     */
    @TableField(value = "delete_time")
    private Date deleteTime;
    /**
     * 订单id
     */
    @TableField(value = "order_id")
    private String orderId;
    /**
     * 店铺增值服务id
     */
    @TableField(value = "shop_additional_services_id")
    private Integer shopAdditionalServicesId;
    /**
     * 增值服务费
     */
    @TableField(value = "price")
    private BigDecimal price;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
