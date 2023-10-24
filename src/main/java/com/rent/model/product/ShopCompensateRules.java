
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
 * 赔偿规则
 *
 * @author youruo
 * @Date 2020-06-17 10:39
 */
@Data
@Accessors(chain = true)
@TableName("ct_shop_compensate_rules")
public class ShopCompensateRules {

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
     * 赔偿规则模板名称
     * 
     */
    @TableField(value="name")
    private String name;
    /**
     * 赔偿规则内容
     * 
     */
    @TableField(value="content")
    private String content;
    /**
     * 赔偿规则所属店铺id
     * 
     */
    @TableField(value="shop_id")
    private String shopId;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
