package com.rent.common.dto.product;

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
@Schema(description = "商品详情页面可用优惠券")
public class ProductCouponDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "优惠券名称")
    private String title;

    @Schema(description = "优惠券模板ID")
    private Long templateId;

    @Schema(description = "减免金额")
    private BigDecimal discountAmount;

    @Schema(description = "最小使用金额")
    private BigDecimal minAmount;

    @Schema(description = "有效期开始时间")
    private Date startTime;

    @Schema(description = "有效期结束时间")
    private Date endTime;

    @Schema(description = "有效期")
    private Integer delayDayNum;

    @Schema(description = "有效期结束时间")
    private String rangeStr;

    @Schema(description = "试用场景")
    private String scene;

    @Schema(description = "是否已经领取")
    private Boolean bind;

    @Schema(description = "领取链接")
    private String bindUrl;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
