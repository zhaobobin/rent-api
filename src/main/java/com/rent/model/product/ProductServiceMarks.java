
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
 * 商品服务标
 *
 * @author youruo
 * @Date 2020-06-22 10:39
 */
@Data
@Accessors(chain = true)
@TableName("ct_product_service_marks")
public class ProductServiceMarks {

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
     * 服务标信息id，是指platform_serivce_mark的id
     * 
     */
    @TableField(value="info_id")
    private Integer infoId;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
