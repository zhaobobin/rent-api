package com.rent.service.order;


import com.rent.common.dto.backstage.OrderReportDto;
import com.rent.common.dto.backstage.OrderReportFormDto;
import com.rent.common.dto.backstage.OrderReportFormRequest;
import com.rent.common.dto.backstage.QueryOrderReportRequest;

import java.util.List;
import java.util.Map;

/**
 * 订单报表统计Service
 *
 * @author xiaoyao
 * @Date 2020-08-11 16:17
 */
public interface OrderReportService {

    /**
     * 查询订单统计
     *
     * @param request
     * @return
     */
    List<OrderReportDto> queryOrderReport(QueryOrderReportRequest request);

    /**
     * 统计
     *
     * @return
     */
    Map<String, Integer> businessOrderStatistics(Boolean isMyAuditOrder);


    /**
     * 业务报表，查询
     *
     * @param reportFormRequest
     * @return
     */
    OrderReportFormDto orderReportForm(OrderReportFormRequest reportFormRequest);
}