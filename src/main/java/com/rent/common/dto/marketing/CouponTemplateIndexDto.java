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
@Schema(description = "优惠券")
public class CouponTemplateIndexDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "优惠券模板ID")
    private Long id;

    @Schema(description = "优惠券名称")
    private String name;

    @Schema(description = "最小使用金额")
    private BigDecimal minAmount;

    @Schema(description = "减免金额")
    private BigDecimal discountAmount;

    @Schema(description = "添加大礼包的店铺名称，运营端为OPE")
    private String sourceShopName;

    @Schema(description = "优惠券有效期开始时间")
    private Date startTime;

    @Schema(description = "优惠券有效期结束时间")
    private Date endTime;

    @Schema(description = "优惠券时间，按照领取时间往后推N天有效")
    private Integer delayDayNum;

    @Schema(description = "使用范围描述")
    private String rangeStr;

    @Schema(description = "是否已经领取完成")
    private Boolean isBind;

    @Schema(description = "领取链接")
    private String bindUrl;


    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
