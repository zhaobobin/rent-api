package com.rent.common.dto.order.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Schema(description = "查看可以申请开票的费用响应")
public class EnableApplyFeeBillTicketResp {

    @Schema(description = "开票金额")
    private BigDecimal amount;

    @Schema(description = "费用ID列表")
    private List<Long> feeBillIdList;
}
