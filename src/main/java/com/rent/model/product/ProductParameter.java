
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
 * 商品参数信息表
 *
 * @author youruo
 * @Date 2020-06-29 18:33
 */
@Data
@Accessors(chain = true)
@TableName("ct_product_parameter")
public class ProductParameter {

    private static final long serialVersionUID = 1L;


    /**
     * Id
     * 
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
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
     * 参数名称
     * 
     */
    @TableField(value="name")
    private String name;
    /**
     * 参数值
     * 
     */
    @TableField(value="value")
    private String value;
    /**
     * 商品Id
     * 
     */
    @TableField(value="product_id")
    private String productId;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
