package com.rent.common.dto.marketing;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.util.Date;

/**
 * 优惠券使用范围
 *
 * @author zhao
 * @Date 2020-07-07 15:04
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "优惠券使用范围")
public class CouponTemplateRangeDto implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * Id
     * 
     */
    @Schema(description = "Id")
    private Long id;

    /**
     * 模版ID
     * 
     */
    @Schema(description = "模版ID")
    private Long templateId;

    /**
     * 类型：CATEGORY=类目 PRODUCT：商品  SHOP：店铺 ALL：全场通用
     * 
     */
    @Schema(description = "类型：CATEGORY=类目 PRODUCT：商品  SHOP：店铺 ALL：全场通用")
    private String type;

    /**
     * 对应的类型的值
     * 
     */
    @Schema(description = "对应的类型的值")
    private String value;

    /**
     * 对应的值的描述，比如商品名称|类型名称
     * 
     */
    @Schema(description = "对应的值的描述，比如商品名称|类型名称")
    private String description;

    /**
     * CreateTime
     * 
     */
    @Schema(description = "CreateTime")
    private Date createTime;

    /**
     * DeleteTime
     * 
     */
    @Schema(description = "DeleteTime")
    private Date deleteTime;



    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
