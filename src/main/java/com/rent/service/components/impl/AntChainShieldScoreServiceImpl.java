package com.rent.service.components.impl;


import cn.com.antcloud.api.AntFinTechApiClient;
import cn.com.antcloud.api.bot.v1_0_0.request.QueryLeaseRiskRequest;
import cn.com.antcloud.api.bot.v1_0_0.response.QueryLeaseRiskResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rent.common.dto.components.dto.QueryAntChainShieldRequest;
import com.rent.common.properties.AntChainProperties;
import com.rent.common.util.AntChainClientFactory;
import com.rent.common.util.GsonUtil;
import com.rent.dao.components.AntChainShieldLogDao;
import com.rent.model.components.AntChainShieldLog;
import com.rent.service.components.AntChainShieldScoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;


/**
 * @author zhaowenchao
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class AntChainShieldScoreServiceImpl implements AntChainShieldScoreService {

    private final AntChainShieldLogDao antChainShieldLogDao;
    private final AntChainClientFactory antChainClientFactory;

    @Override
    public String queryAntChainShieldScore(QueryAntChainShieldRequest request) {
        AntFinTechApiClient client = antChainClientFactory.getClient();

        // 构建请求
        QueryLeaseRiskRequest queryLeaseRiskRequest = new QueryLeaseRiskRequest();
        queryLeaseRiskRequest.setUserId(request.getUid());
        queryLeaseRiskRequest.setUserName(request.getUserName());
        queryLeaseRiskRequest.setCertNo(request.getIdCard());
        queryLeaseRiskRequest.setMobile(request.getMobile());
        queryLeaseRiskRequest.setIp(request.getIp());
        queryLeaseRiskRequest.setProductInstanceId("bot-aimetaprod-prod");
        queryLeaseRiskRequest.setRegionName("CN-HANGZHOU-FINANCE");

        AntChainShieldLog antChainShieldLog = new AntChainShieldLog();
        antChainShieldLog.setOrderId(request.getOrderId());
        antChainShieldLog.setUid(request.getUid());
        antChainShieldLog.setIdCard(request.getIdCard());
        try {
            antChainShieldLog.setRequest(GsonUtil.objectToJsonString(queryLeaseRiskRequest));
            log.info("【查询蚁盾分】参数：{}", antChainShieldLog.getRequest());
            // 发送请求，并且获取响应结果
            QueryLeaseRiskResponse response = client.execute(queryLeaseRiskRequest);
            antChainShieldLog.setResponse(GsonUtil.objectToJsonString(response));
            antChainShieldLog.setStatusCode(response.getResultCode());
            log.info("【查询蚁盾分】响应：{}", antChainShieldLog.getResponse());
            JSONObject result = JSON.parseObject(response.getData());
            JSONArray models = result.getJSONArray("models");
            if(models!=null){
                for (int i = 0; i < models.size(); i++) {
                    JSONObject model = models.getJSONObject(i);
                    antChainShieldLog.setScore(model.getString("score"));
                }
            }
        } catch (Exception e) {
            log.error("【查询蚁盾分-异常】",e);
            antChainShieldLog.setResponse(e.getMessage());
        }finally {
            antChainShieldLog.setCreateTime(new Date());
            antChainShieldLogDao.save(antChainShieldLog);
            return  antChainShieldLog.getScore();
        }
    }
}
