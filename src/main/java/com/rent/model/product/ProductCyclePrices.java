
package com.rent.model.product;

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
 * 商品周期价格表
 *
 * @author youruo
 * @Date 2020-06-16 15:08
 */
@Data
@Accessors(chain = true)
@TableName("ct_product_cycle_prices")
public class ProductCyclePrices {

    private static final long serialVersionUID = 1L;


    /**
     * Id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
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
     * 商品id
     */
    @TableField(value = "item_id")
    private String itemId;
    /**
     * sku的id
     */
    @TableField(value = "sku_id")
    private Long skuId;
    /**
     * 目前官方限制7天 30天 90天 180天 365天 指7天以上 30天以上
     */
    @TableField(value = "days")
    private Integer days;
    /**
     * 单天价格
     */
    @TableField(value = "price")
    private BigDecimal price;
    /**
     * 周期销售价格
     */
    @TableField(value = "sale_price")
    private BigDecimal salePrice;

    /**
     * 芝麻押金评估金额
     */
    @TableField(value = "sesame_deposit")
    private BigDecimal sesameDeposit;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
