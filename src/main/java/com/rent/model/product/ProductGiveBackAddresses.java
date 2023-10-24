
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
 * 商品归还地址
 *
 * @author youruo
 * @Date 2020-06-16 15:12
 */
@Data
@Accessors(chain = true)
@TableName("ct_product_give_back_addresses")
public class ProductGiveBackAddresses {

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
     * 商品id
     * 
     */
    @TableField(value="item_id")
    private String itemId;
    /**
     * 归还地址id
     * 
     */
    @TableField(value="address_id")
    private Integer addressId;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
