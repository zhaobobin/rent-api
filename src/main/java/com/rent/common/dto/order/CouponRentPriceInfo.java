package com.rent.common.dto.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author xiaoyao
 * @version V1.0
 * @date 2020-7-16 下午 2:44:02
 * @since 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CouponRentPriceInfo {

    /** 总租金 */
    private BigDecimal rentPrice;
    /** 每期租金 */
    private BigDecimal periodsPrice;
}
