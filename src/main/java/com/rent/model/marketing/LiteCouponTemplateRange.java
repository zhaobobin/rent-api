
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
 * 优惠券使用范围
 *
 * @author zhao
 * @Date 2020-07-07 15:04
 */
@Data
@Accessors(chain = true)
@TableName("ct_lite_coupon_template_range")
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class LiteCouponTemplateRange {


    /**
     * Id
     * 
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    /**
     * 模版ID
     * 
     */
    @TableField(value="template_id")
    private Long templateId;
    /**
     * 类型：CATEGORY=类目 PRODUCT：商品  SHOP：店铺 ALL：全场通用
     * 
     */
    @TableField(value="type")
    private String type;
    /**
     * 对应的类型的值
     * 
     */
    @TableField(value="value")
    private String value;
    /**
     * 对应的值的描述，比如商品名称|类型名称
     * 
     */
    @TableField(value="description")
    private String description;

    @TableField(value="channel_id")
    private String channelId;
    /**
     * CreateTime
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

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
