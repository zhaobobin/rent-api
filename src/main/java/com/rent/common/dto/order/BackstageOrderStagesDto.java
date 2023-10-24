package com.rent.common.dto.order;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author xiaoyao
 * @version V1.0
 * @date 2020-8-4 上午 9:41:08
 * @since 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "后台订单账单信息")
public class BackstageOrderStagesDto implements Serializable {

    private static final long serialVersionUID = 6296973090316040654L;

    @Schema(description = "订单编号")
    private String orderId;

    @Schema(description = "订单金额信息")
    private UserOrderCashesDto userOrderCashesDto;

    @Schema(description = "账单信息")
    private List<OrderByStagesDto> orderByStagesDtoList;

    @Schema(description = "买断买断信息")
    private BackstageOrderBuyOutDto orderBuyOutDto;

    @Schema(description = "结算信息")
    private OrderSettlementInfoDto settlementInfoDto;

    @Schema(description = "增值服务信息")
    private OrderAdditionalServicesDto orderAdditionalServicesDto;

    @Schema(description = "增值服务信息集合")
    private List<OrderAdditionalServicesDto> orderAdditionalServicesList;

}
