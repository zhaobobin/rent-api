
package com.rent.model.order;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 秒杀订单流水记录
 *
 * @author youruo
 * @Date 2021-01-26 10:59
 */
@Data
@Accessors(chain = true)
@TableName("ct_order_activity_record")
public class OrderActivityRecord {

    private static final long serialVersionUID = 1L;


    /**
     * id
     * 
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    /**
     * 订单id
     * 
     */
    @TableField(value="order_id")
    private String orderId;
    /**
     * 配置页面
     * 
     */
    @TableField(value="index_id")
    private Long indexId;
    /**
     * 创建时间
     * 
     */
    @TableField(value="create_time")
    private Date createTime;
    /**
     * 周期天数
     * 
     */
    @TableField(value="days")
    private Integer days;
    /**
     * 秒杀价格
     * 
     */
    @TableField(value="price")
    private BigDecimal price;
    /**
     * 原价
     * 
     */
    @TableField(value="origin_price")
    private BigDecimal originPrice;
    /**
     * 库存Id
     * 
     */
    @TableField(value="sku_id")
    private Long skuId;
    /**
     * 商品ID
     * 
     */
    @TableField(value="product_id")
    private String productId;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
