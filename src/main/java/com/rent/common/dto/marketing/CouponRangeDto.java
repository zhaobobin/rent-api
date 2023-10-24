package com.rent.common.dto.marketing;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;

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
public class CouponRangeDto implements Serializable {

    private static final long serialVersionUID = 1L;
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
    @Schema(description = "对应的类型的值，多个用逗号分割开")
    private String value;

    /**
     * 类型：CATEGORY=类目 PRODUCT：商品  SHOP：店铺 ALL：全场通用
     *
     */
    @Schema(description = "类型：CATEGORY=类目 PRODUCT：商品  SHOP：店铺 ALL：全场通用")
    private String excludeType;

    /**
     * 对应的类型的值
     *
     */
    @Schema(description = "对应的类型的值，多个用逗号分割开")
    private String excludeValue;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
