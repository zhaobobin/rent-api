
package com.rent.model.product;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 * 风险等级
 *
 * @author youruo
 * @Date 2020-06-16 14:11
 */
@Data
@Accessors(chain = true)
@TableName("ct_categories_risk")
public class CategoriesRisk {

    private static final long serialVersionUID = 1L;


    /**
     * Id
     * 
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    /**
     * Name
     * 
     */
    @TableField(value="name")
    private String name;
    /**
     * CategoryId
     * 
     */
    @TableField(value="category_id")
    private Integer categoryId;
    /**
     * 风控等级 1低 2中 3高
     * 
     */
    @TableField(value="rank_level")
    private Integer rankLevel;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
