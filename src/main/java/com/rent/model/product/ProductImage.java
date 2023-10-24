
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
 * 商品主图表
 *
 * @author youruo
 * @Date 2020-06-16 15:16
 */
@Data
@Accessors(chain = true)
@TableName("ct_product_image")
public class ProductImage {

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
     * 图片链接
     * 
     */
    @TableField(value="src")
    private String src;
    /**
     * 所属产品id
     * 
     */
    @TableField(value="product_id")
    private String productId;
    /**
     * 商品展示主图  1 展示第一栏   其余设定排序按数字大小规则   不排序传0
     * 
     */
    @TableField(value="is_main")
    private Integer isMain;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
