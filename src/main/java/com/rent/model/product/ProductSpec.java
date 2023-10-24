
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
 * 商品属性规格表
 *
 * @author youruo
 * @Date 2020-06-16 15:32
 */
@Data
@Accessors(chain = true)
@TableName("ct_product_spec")
public class ProductSpec {

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
     * ct_platform_spec -->id
     * 
     */
    @TableField(value="spec_id")
    private Integer specId;
    /**
     * 所属产品id
     * 
     */
    @TableField(value="item_id")
    private String itemId;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
