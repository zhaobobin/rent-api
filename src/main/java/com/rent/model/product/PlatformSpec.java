
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
 * 平台商品规格属性分类表
 *
 * @author youruo
 * @Date 2020-06-16 11:50
 */
@Data
@Accessors(chain = true)
@TableName("ct_platform_spec")
public class PlatformSpec {

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
     * 名称
     * 
     */
    @TableField(value="name")
    private String name;
    /**
     * 所属分类id
     * 
     */
    @TableField(value="category_id")
    private Integer categoryId;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
