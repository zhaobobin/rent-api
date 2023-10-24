package com.rent.common.dto.components.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Description: 放款报送
 * @Author: yr
 * @Date: 2022/2/28
 */
@Data
public class RepayPlanInfoDto {
    private Integer period;
    private BigDecimal needRepayAmount;
    private BigDecimal needRepayCapital;
    private String repayDate;
}
