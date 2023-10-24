
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
 * 商品sku规格属性value表
 *
 * @author youruo
 * @Date 2020-06-16 15:28
 */
@Data
@Accessors(chain = true)
@TableName("ct_product_sku_values")
public class ProductSkuValues {

    private static final long serialVersionUID = 1L;


    /**
     * Id
     * 
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    /**
     * CreateTime
     * 
     */
    @TableField(value="create_time")
    private Date createTime;
    /**
     * UpdateTime
     * 
     */
    @TableField(value="update_time")
    private Date updateTime;
    /**
     * DeleteTime
     * 
     */
    @TableField(value="delete_time")
    private Date deleteTime;
    /**
     * 商品参数值id 是product_spec_value中的id
     * 
     */
    @TableField(value="spec_value_id")
    private Long specValueId;
    /**
     * 所属sku值组的id
     * 
     */
    @TableField(value="sku_id")
    private Long skuId;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
