
package com.rent.model.marketing;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.util.Date;

/**
 * 商家中心通知
 *
 * @author youruo
 * @Date 2021-08-16 16:10
 */
@Data
@Accessors(chain = true)
@TableName("ct_ope_notice")
public class OpeNotice {

    private static final long serialVersionUID = 1L;


    /**
     * 主键id
     * 
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    /**
     * 素材图片ID
     * 
     */
    @TableField(value="material_item_id")
    private Long materialItemId;


    /**
     * 跳转链接
     * 
     */
    @TableField(value="jump_url")
    private String jumpUrl;
    /**
     * 创建时间
     * 
     */
    @TableField(value="create_time")
    private Date createTime;
    /**
     * DeleteTime
     * 
     */
    @TableField(value="delete_time")
    private Date deleteTime;
    /**
     * UpdateTime
     * 
     */
    @TableField(value="update_time")
    private Date updateTime;
    /**
     * 序列
     * 
     */
    @TableField(value="index_sort")
    private Integer indexSort;
    /**
     * 标题
     * 
     */
    @TableField(value="name")
    private String name;
    /**
     * 备注
     * 
     */
    @TableField(value="remark")
    private String remark;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
