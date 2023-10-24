
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
 * 平台服务标表
 *
 * @author youruo
 * @Date 2020-06-22 10:35
 */
@Data
@Accessors(chain = true)
@TableName("ct_platform_service_marks")
public class PlatformServiceMarks {

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
     * 服务标说明
     * 
     */
    @TableField(value="description")
    private String description;
    /**
     * 服务标类型 0为包邮 1为免押金 2为免赔 3为随租随还 4为全新品 5为分期支付
     * 
     */
    @TableField(value="type")
    private Short type;
    /**
     * 图标
     * 
     */
    @TableField(value="icon")
    private String icon;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
