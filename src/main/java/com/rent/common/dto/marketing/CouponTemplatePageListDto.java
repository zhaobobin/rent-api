package com.rent.common.dto.marketing;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.util.List;

/**
 * 优惠券模版
 *
 * @author zhao
 * @Date 2020-07-07 15:04
 */
@Data
@Schema(description = "优惠券模版")
public class CouponTemplatePageListDto extends CouponTemplateDto {

    private static final long serialVersionUID = 1L;

    private List<CouponTemplateDto> history;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
