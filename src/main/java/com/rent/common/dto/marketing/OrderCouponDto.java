package com.rent.common.dto.marketing;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 优惠券大礼包
 *
 * @author zhao
 * @Date 2020-07-07 15:04
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "下单页面可用优惠券")
public class OrderCouponDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "优惠券编码")
    private String code;

    @Schema(description = "使用场景:CouponTemplateConstant")
    private String scene;

    @Schema(description = "店铺ID")
    private String shopId;

    @Schema(description = "减免金额")
    private BigDecimal discountAmount;

    @Schema(description = "最小使用金额")
    private BigDecimal minAmount;

    @Schema(description = "有效期开始时间")
    private Date startTime;

    @Schema(description = "有效期结束时间")
    private Date endTime;

    @Schema(description = "是否选中")
    private Boolean check;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
