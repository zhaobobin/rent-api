
package com.rent.model.marketing;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.util.Date;


/**
 * 素材中心文件
 * @author zhaowenchao
 */
@Data
@Accessors(chain = true)
@TableName("ct_material_center_item")
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class MaterialCenterItem {

    /**
     * id
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    /**
     * 素材所属分类ID
     */
    @TableField(value="category_id")
    private Long categoryId;

    /**
     * 名称
     */
    @TableField(value="name")
    private String name;

    /**
     * 文件路径
     */
    @TableField(value="file_url")
    private String fileUrl;

    @TableField(value="create_time")
    private Date createTime;

    @TableField(value="delete_time")
    private Date deleteTime;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
