package com.rent.common.dto.marketing;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.util.List;

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
@Schema(description = "优惠券大礼包")
public class LiteCouponCenterPackageDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "大礼包名称")
    private Long id;

    @Schema(description = "大礼包名称")
    private String packageName;

    @Schema(description = "优惠券列表")
    private List<CouponTemplateIndexDto> couponList;

    @Schema(description = "是否已经领取")
    private Boolean isBind;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
