package com.rent.common.dto.order.resquest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Schema(description = "申请开票 求参数")
public class FeeBillTicketApplyReq {

    @Schema(description = "开票金额")
    private BigDecimal amount;

    @Schema(description = "费用ID列表")
    private List<Long> feeBillIdList;
}
