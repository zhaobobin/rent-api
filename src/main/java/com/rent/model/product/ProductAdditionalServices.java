
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
 * 商品增值服务表
 *
 * @author youruo
 * @Date 2020-06-16 15:07
 */
@Data
@Accessors(chain = true)
@TableName("ct_product_additional_services")
public class ProductAdditionalServices {

    private static final long serialVersionUID = 1L;


    /**
     * Id
     * 
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    /**
     * 创建时间
     * 
     */
    @TableField(value="create_time")
    private Date createTime;
    /**
     * 更新时间
     * 
     */
    @TableField(value="update_time")
    private Date updateTime;
    /**
     * 删除时间
     * 
     */
    @TableField(value="delete_time")
    private Date deleteTime;
    /**
     * 商品id
     * 
     */
    @TableField(value="product_id")
    private String productId;
    /**
     * 店铺增值服务id
     * 
     */
    @TableField(value="shop_additional_services_id")
    private Integer shopAdditionalServicesId;


    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
