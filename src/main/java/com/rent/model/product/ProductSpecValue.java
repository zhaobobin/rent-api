
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
 * 商品规格属性value表
 *
 * @author youruo
 * @Date 2020-06-16 15:33
 */
@Data
@Accessors(chain = true)
@TableName("ct_product_spec_value")
public class ProductSpecValue {

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
     * 商品参数值id 是product_spec_value中的id
     * 
     */
    @TableField(value="product_spec_id")
    private Long productSpecId;

    @TableField(value="name")
    private String name;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
