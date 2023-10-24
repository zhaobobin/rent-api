package com.rent.common.dto.backstage;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author xiaoyao
 * @version V1.0
 * @date 2020-10-22 下午 3:05:47
 * @since 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "商家订单统计响应类")
public class BusinessOrderStaticsDto implements Serializable {

    private static final long serialVersionUID = 7005321856144864623L;

    @Schema(description = "待付款订单数量")
    private int unPayOrderCount;

    @Schema(description = "待审核订单数量")
    private int waitingAuditOrderCount;

    @Schema(description = "租用中订单数量")
    private int rentingOrderCount;

    @Schema(description = "待发货订单数量")
    private int pendingOrderCount;

    @Schema(description = "待收货订单数量")
    private int waitingConfirmOrderCount;

    @Schema(description = "待归还订单数量")
    private int waitingGiveBackOrderCount;

    @Schema(description = "待结算订单数量")
    private int waitingSettlementOrderCount;

    @Schema(description = "完成订单数量")
    private int finishOrderCount;
}
