
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
 * 后台商品类目表
 *
 * @author youruo
 * @Date 2020-06-15 10:56
 */
@Data
@Accessors(chain = true)
@TableName("ct_category")
public class Category {

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
     * 分类名称
     * 
     */
    @TableField(value="name")
    private String name;
    /**
     * 父级分类id
     * 
     */
    @TableField(value="parent_id")
    private Integer parentId;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
