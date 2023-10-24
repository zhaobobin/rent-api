package com.rent.common.dto.order;

import com.rent.common.enums.order.EnumPlatformType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Description: 用户优惠详情
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "用户优惠详情")
public class UserOrderCouponDto implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 用户ID
     */
    @Schema(description = "用户ID")
    private String uid;


    /**
     * 优惠券名称
     */
    @Schema(description = "优惠券名称")
    private String couponName;

    /**
     * 优惠券金额
     */
    @Schema(description = "优惠券金额")
    private BigDecimal discountAmount;


    /**
     * 优惠券平台&类型
     */
    @Schema(description = "优惠券平台&类型")
    private EnumPlatformType platform;


    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
