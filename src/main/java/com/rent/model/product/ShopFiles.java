
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
 * 店铺文件表
 *
 * @author youruo
 * @Date 2020-06-17 10:47
 */
@Data
@Accessors(chain = true)
@TableName("ct_shop_files")
public class ShopFiles {

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
     * 文件名
     * 
     */
    @TableField(value="name")
    private String name;
    /**
     * 链接
     * 
     */
    @TableField(value="src")
    private String src;
    /**
     * 店铺id
     * 
     */
    @TableField(value="shop_id")
    private String shopId;
    /**
     * IsDir
     * 
     */
    @TableField(value="is_dir")
    private Integer isDir;
    /**
     * ParentId
     * 
     */
    @TableField(value="parent_id")
    private Long parentId;
    /**
     * 图片宽度
     * 
     */
    @TableField(value="width")
    private Integer width;
    /**
     * 图片高度
     * 
     */
    @TableField(value="height")
    private Integer height;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
