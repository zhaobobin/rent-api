package com.rent.common.dto.order.response;

import com.rent.common.dto.order.DepositOrderDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author zhaowenchao
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DepositOrderPageDto {

    private BigDecimal totalDeposit;

    private BigDecimal withdrawAble;

    private List<DepositOrderDto>  orderList;
}
