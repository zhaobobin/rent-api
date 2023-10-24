package com.rent.common.dto.marketing;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

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
@Schema(description = "我的优惠券列表")
public class MyCouponListDto implements Serializable {

    private static final long serialVersionUID = -5783738125826086671L;

    @Schema(description = "未使用优惠券列表")
    private List<MyCouponDto> unUseCouponList;

    @Schema(description = "已使用使用优惠券列表")
    private List<MyCouponDto> usedCouponList;

    @Schema(description = "过期优惠券列表")
    private List<MyCouponDto> expiredCouponList;
}
