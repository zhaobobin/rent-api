
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
 * 素材中心-素材分类
 * @author zhaowenchao
 */
@Data
@Accessors(chain = true)
@TableName("ct_material_center_category")
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class MaterialCenterCategory {

    /**
     * ID
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    /**
     * 素材分类名称
     */
    @TableField(value="name")
    private String name;

    /**
     * 宽度
     */
    @TableField(value="width")
    private Integer width;

    /**
     * 高度
     */
    @TableField(value="height")
    private Integer height;


    @TableField(value="create_time")
    private Date createTime;

    @TableField(value="delete_time")
    private Date deleteTime;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
