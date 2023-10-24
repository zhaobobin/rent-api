
package com.rent.model.product;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rent.common.enums.product.ProductSearchTypeEnum;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.util.Date;

/**
 * 商品查询规则表
 *
 * @author youruo
 * @Date 2021-07-26 11:47
 */
@Data
@Accessors(chain = true)
@TableName("ct_product_search_rule")
public class ProductSearchRule {

    private static final long serialVersionUID = 1L;


    /**
     * Id
     * 
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    /**
     * 渠道来源
     * 
     */
    @TableField(value="channel_id")
    private String channelId;
    /**
     * 规则内容
     * 
     */
    @TableField(value="rule_content")
    private String ruleContent;
    /**
     * 规则类型 
     * 
     */
    @TableField(value="rule_type")
    private ProductSearchTypeEnum ruleType;

    /**
     * 创建时间
     * 
     */
    @TableField(value="create_time")
    private Date createTime;
    /**
     * 更新时间
     * 
     */
    @TableField(value="update_time")
    private Date updateTime;
    /**
     * 删除时间
     * 
     */
    @TableField(value="delete_time")
    private Date deleteTime;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
