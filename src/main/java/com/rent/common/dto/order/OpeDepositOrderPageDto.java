package com.rent.common.dto.order;

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
public class OpeDepositOrderPageDto {

    private BigDecimal totalDeposit;

    private BigDecimal creditAmount;

    private BigDecimal amount;

    private BigDecimal paidAmount;

    private BigDecimal waitPayAmount;

    private List<DepositOrderLogDto>  logs;
}
