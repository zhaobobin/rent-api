
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
 * 商品sku属性图片
 *
 * @author youruo
 * @Date 2020-06-16 15:34
 */
@Data
@Accessors(chain = true)
@TableName("ct_product_spec_value_images")
public class ProductSpecValueImages {

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
     * sku值的图片 即product_spec_value中的id
     * 
     */
    @TableField(value="spec_value_id")
    private Long specValueId;
    /**
     * 图片链接
     * 
     */
    @TableField(value="image")
    private String image;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
