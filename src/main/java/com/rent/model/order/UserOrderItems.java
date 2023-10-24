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
 * 用户订单商品
 *
 * @author xiaoyao
 * @Date 2020-06-16 10:10
 */
@Data
@Accessors(chain = true)
@TableName("ct_user_order_items")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserOrderItems {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "create_time")
    private Date createTime;

    @TableField(value = "update_time")
    private Date updateTime;

    @TableField(value = "delete_time")
    private Date deleteTime;

    @TableField(value = "snap_shot_id")
    private Long snapShotId;

    @TableField(value = "amount")
    private Integer amount;

    @TableField(value = "product_id")
    private String productId;

    @TableField(value = "sku_id")
    private Long skuId;

    @TableField(value = "order_id")
    private String orderId;

    @TableField(value = "product_name")
    private String productName;

    @TableField(value = "product_image")
    private String productImage;

    @TableField(value = "spec_join_name")
    private String specJoinName;

    @TableField(value = "market_price")
    private BigDecimal marketPrice;

    @TableField(value = "sale_price")
    private BigDecimal salePrice;

    @TableField(value = "buy_out_support")
    private Integer buyOutSupport;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
