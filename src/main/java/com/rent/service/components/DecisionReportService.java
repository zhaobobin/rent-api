package com.rent.service.components;


import com.rent.common.dto.components.request.DecisionRequest;
import com.rent.common.dto.components.response.RiskReportResponse;

/**
 * <p>
 * 用户报告表 服务类
 * </p>
 *
 * @author Boan
 * @since 2020-02-02
 */
public interface DecisionReportService {

    /**
     * @param request 调用风控接口
     * @return 是否成功
     */
    boolean queryRiskReport(DecisionRequest request);

    /**
     * 根据uid查询报告详情
     *
     * @param request
     * @return null 无记录 else 详情
     */
    RiskReportResponse getSiriusReportByOrderId(DecisionRequest request);


    /**
     * 根据orderId判断是否有风控报告
     * @param orderId
     * @return
     */
    Boolean queryWhetherRiskReport(String orderId);
}
