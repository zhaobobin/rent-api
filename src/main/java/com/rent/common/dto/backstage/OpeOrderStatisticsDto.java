package com.rent.common.dto.backstage;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author xiaoyao
 * @version V1.0
 * @date 2020-8-4 下午 4:44:54
 * @since 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "订单统计")
public class OpeOrderStatisticsDto implements Serializable {

    private static final long serialVersionUID = -9032935781279871086L;

    @Schema(description = "今日订单总数")
    private int todayTotalOrderCount;

    @Schema(description = "今日下单总租金")
    private BigDecimal todayTotalOrderRentAmount;

    @Schema(description = "昨日下单总租金")
    private BigDecimal yesterdayTotalRentAmount;

    @Schema(description = "七日下单总租金")
    private BigDecimal sevenTotalOrderRentAmount;

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

    @Schema(description = "逾期订单数量")
    private int overDueOrderCount;

    @Schema(description = "已取消订单数量")
    private int closeOrderCount;

    @Schema(description = "完成订单数量")
    private int finishOrderCount;

    @Schema(description = "待电审订单数量")
    private int telephoneAuditOrderCount;

    @Schema(description = "近一周订单统计")
    private List<OrderReportDto> orderReportDtoWeekList;

    @Schema(description = "近一月订单统计")
    private List<OrderReportDto> orderReportDtoMonthList;

    @Schema(description = "本周订单数量")
    private int weekOrderCount;

    @Schema(description = "周订单数增长比率")
    private BigDecimal weekOrderCountRate;

    @Schema(description = "本周订单租金总额")
    private BigDecimal weekOrderRent;

    @Schema(description = "周订单租金增长比率")
    private BigDecimal weekOrderRentRate;

    @Schema(description = "本月订单数量")
    private int monthOrderCount;

    @Schema(description = "月订单数量增长比率")
    private BigDecimal monthOrderCountRate;

    @Schema(description = "本月订单租金总额")
    private BigDecimal monthOrderRent;

    @Schema(description = "月订单租金增长比率")
    private BigDecimal monthOrderRentRate;

    @Schema(description = "上周订单数量", hidden = true)
    private int lastWeekOrderCount;

    @Schema(description = "上周订单租金总额", hidden = true)
    private BigDecimal lastWeekOrderRent;

    @Schema(description = "上月订单数量", hidden = true)
    private int lastMonthOrderCount;

    @Schema(description = "上月订单租金总额", hidden = true)
    private BigDecimal lastMonthOrderRent;

}
