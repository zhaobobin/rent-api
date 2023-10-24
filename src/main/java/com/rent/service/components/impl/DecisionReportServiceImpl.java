package com.rent.service.components.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rent.common.dto.components.request.DecisionRequest;
import com.rent.common.dto.components.response.RiskReportResponse;
import com.rent.common.enums.components.EnumRiskReportType;
import com.rent.dao.components.SxReportRecordDao;
import com.rent.model.components.SxReportRecord;
import com.rent.service.components.DecisionReportService;
import com.rent.service.components.SxService;
import com.rent.service.order.FeeBillService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;


/**
 * <p>
 * 用户报告表 服务实现类
 * </p>
 *
 * @author Boan
 * @since 2020-02-02
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class DecisionReportServiceImpl implements DecisionReportService {

    private final SxReportRecordDao sxReportRecordDao;
    private final FeeBillService feeBillService;
    private final SxService sxService;

    /**
     * @param request 调用风控接口
     * @return 是否成功
     */
    @Override
    public boolean queryRiskReport(DecisionRequest request) {
        feeBillService.reportBilling(request.getOrderId());
        Date now = new Date();
        SxReportRecord sxReportRecord = SxReportRecord.builder()
                .createTime(now)
                .queryTime(now)
                .idCard(request.getIdCardNo())
                .phone(request.getPhone())
                .uid(request.getUid())
                .reportType(EnumRiskReportType.SIRIUS.getCode())
                .orderId(request.getOrderId())
                .userName(request.getUserName())
                .build();

        //调用风控接口
        String result = sxService.riskReport(request.getUserName(),request.getIdCardNo(),request.getPhone());
        log.info("风控接口返回结果：{}"+result);
        if (StringUtils.isEmpty(result)) {
            sxReportRecord.setStatus(8);
            sxReportRecordDao.save(sxReportRecord);
            log.error("首新查询异常{}", request);
            return false;
        }
        sxReportRecord.setReportResult(result);
        JSONObject jsonObject = JSON.parseObject(result);
        JSONObject resp_data = jsonObject.getJSONObject("resp_data");
        if(jsonObject.getString("resp_code").equals("SW0000") && Objects.nonNull(resp_data) && resp_data.size() > 0){
            String score = resp_data.getString("score_norm_explain");
            sxReportRecord.setStatus(1);
            sxReportRecord.setMultipleScore(new Integer(score));
        }else {
            sxReportRecord.setMultipleScore(-1);
            sxReportRecord.setStatus(9);
        }
        sxReportRecordDao.save(sxReportRecord);
        log.info("[风控报告查询用户] uid:{}", sxReportRecord.getUid());
        return Boolean.TRUE;
    }

    /**
     * 根据uid查询报告详情
     *
     * @param request
     * @return null 无记录 else 详情
     */
    @SneakyThrows
    @Override
    public RiskReportResponse getSiriusReportByOrderId(DecisionRequest request) {
        if (StringUtils.isEmpty(request.getOrderId())) {
            return null;
        }
        SxReportRecord sxReportRecord = sxReportRecordDao.getOne(new QueryWrapper<>(SxReportRecord.builder().orderId(request.getOrderId()).build()).orderByDesc(), false);
        if (null == sxReportRecord) {
            this.queryRiskReport(request);
            sxReportRecord = sxReportRecordDao.getOne(new QueryWrapper<>(SxReportRecord.builder().orderId(request.getOrderId()).build()).orderByDesc(), false);
            log.info("调用完成后查询到的风控报告：{}"+sxReportRecord.toString());
        }
        RiskReportResponse reportResponse = new RiskReportResponse();
        if (EnumRiskReportType.SIRIUS.getCode().equals(sxReportRecord.getReportType())) {
            log.info(sxReportRecord.getReportType());
            reportResponse.setReportType(EnumRiskReportType.SIRIUS.getCode());
            reportResponse.setSiriusRiskReport(sxReportRecord.getReportResult());
        }
        return reportResponse;
    }

    @Override
    public Boolean queryWhetherRiskReport(String orderId) {
        SxReportRecord sxReportRecord = sxReportRecordDao.getOne(new QueryWrapper<>(SxReportRecord.builder()
                .orderId(orderId)
                .build()).orderByDesc(), false);
        if(sxReportRecord == null){
            return false;
        }
        return true;
    }

}
