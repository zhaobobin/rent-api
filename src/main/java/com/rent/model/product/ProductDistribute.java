
package com.rent.model.product;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.util.Date;

/**
 * 商品分发
 * @author zhaowenchao
 */
@Data
@Accessors(chain = true)
@TableName("ct_product_distribute")
public class ProductDistribute {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    @TableField(value="virtual_product_id")
    private String virtualProductId;

    @TableField(value="real_product_id")
    private String realProductId;

    @TableField(value="product_name")
    private String productName;

    @TableField(value="remark_name")
    private String remarkName;

    @TableField(value="period")
    private String period;

    @TableField(value="period_price")
    private String periodPrice;

    @TableField(value="shop_id")
    private String shopId;

    @TableField(value="shop_name")
    private String shopName;

    @TableField(value="weights")
    private Integer weights;

    @TableField(value="rent_price")
    private String rentPrice;

    @TableField(value="remark")
    private String remark;

    @TableField(value="create_time")
    private Date createTime;

    @TableField(value="update_time")
    private Date updateTime;

    @TableField(value="delete_time")
    private Date deleteTime;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
