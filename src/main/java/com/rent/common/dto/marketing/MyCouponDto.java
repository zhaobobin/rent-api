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
@Schema(description = "我的优惠券")
public class MyCouponDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "优惠券ID")
    private Long id;

    @Schema(description = "优惠券模板ID")
    private Long templateId;

    @Schema(description = "有效期结束时间")
    private String title;

    @Schema(description = "减免金额")
    private BigDecimal discountAmount;

    @Schema(description = "添加优惠券的店铺名称")
    private String sourceShopName;

    @Schema(description = "最小使用金额")
    private BigDecimal minAmount;

    @Schema(description = "有效期开始时间")
    private Date startTime;

    @Schema(description = "有效期结束时间")
    private Date endTime;

    @Schema(description = "有效期结束时间")
    private String rangeStr;


    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
