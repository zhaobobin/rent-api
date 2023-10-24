package com.rent.service.components.impl;

import cn.com.antcloud.api.AntFinTechApiClient;
import cn.com.antcloud.api.twc.v1_0_0.request.CreateLeaseBizRequest;
import cn.com.antcloud.api.twc.v1_0_0.response.CreateLeaseBizResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rent.common.cache.compoments.InsuranceBalanceCache;
import com.rent.common.enums.components.EnumAntChainLogStatus;
import com.rent.common.enums.components.EnumInsureOperate;
import com.rent.common.util.AntChainClientFactory;
import com.rent.common.util.GsonUtil;
import com.rent.config.outside.PlatformChannelDto;
import com.rent.dao.components.AntChainInsuranceLogDao;
import com.rent.exception.HzsxBizException;
import com.rent.model.components.AntChainInsuranceLog;
import com.rent.service.components.AntChainInsuranceService;
import com.rent.service.product.PlatformChannelService;
import com.rent.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;


/**
 * @author zhaowenchao
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class AntChainInsuranceServiceImpl implements AntChainInsuranceService {

    /**
     * 杭州⼈保：RJPYGNCN    亚太保险：JWBCQILO
     */
    private static final String insuranceCorpIsvAccount = "RJPYGNCN";
    private static final String productInstanceId = "notary-api-prod";

    private final AntChainInsuranceLogDao antChainInsuranceLogDao;
    private final PlatformChannelService platformChannelService;
    private final AntChainClientFactory antChainClientFactory;

    @Override
    public Boolean antChainInsure(String orderId,Integer month,String channelId) {
        PlatformChannelDto platFormChannel = platformChannelService.getPlatFormChannel(channelId);
        AntFinTechApiClient client = antChainClientFactory.getClient();
        CreateLeaseBizRequest request = new CreateLeaseBizRequest();
        JSONObject data = new JSONObject();
        data.put("orderId",orderId);
        data.put("insuredIdNo",platFormChannel.getEnterpriseLicenseNo());
        data.put("insuranceCorpIsvAccount",insuranceCorpIsvAccount);
        //投保的结束时间
        data.put("insuranceEndTime", DateUtil.date2String(DateUtil.dateAddMonth(new Date(),month),DateUtil.DATETIME_FORMAT_1));
        JSONObject insured = new JSONObject();
        insured.put("insuredName",platFormChannel.getEnterpriseName());
        insured.put("insuredMobile",platFormChannel.getEnterpriseLegalPhone());
        insured.put("insuredAddress",platFormChannel.getEnterpriseAddress());
        insured.put("insuredMail",platFormChannel.getEmail());
        insured.put("insuredIdNo",platFormChannel.getEnterpriseLicenseNo());
        data.put("insured",insured);
        JSONObject holder = new JSONObject();
        holder.put("holderName",platFormChannel.getEnterpriseName());
        holder.put("holderMobile",platFormChannel.getEnterpriseLegalPhone());
        holder.put("holderAddress",platFormChannel.getEnterpriseAddress());
        holder.put("holderMail",platFormChannel.getEmail());
        holder.put("holderIdNo",platFormChannel.getEnterpriseLicenseNo());
        data.put("holder",holder);

        request.setBizContent(data.toJSONString());
        request.setType("insurance");
        request.setProductInstanceId(productInstanceId);
        request.setRegionName("CN-HANGZHOU-FINANCE");
        // 发送请求，并且获取响应结果
        AntChainInsuranceLog antChainInsuranceLog = new AntChainInsuranceLog();
        antChainInsuranceLog.setOrderId(orderId);
        antChainInsuranceLog.setOperate(EnumInsureOperate.INSURE);
        antChainInsuranceLog.setReqParams(GsonUtil.objectToJsonString(request));
        antChainInsuranceLog.setStatus(EnumAntChainLogStatus.FAIL);
        String errorMsg = null;
        try {
            log.info("【蚂蚁链保险-投保】参数：{}", GsonUtil.objectToJsonString(request));
            CreateLeaseBizResponse response = client.execute(request);
            antChainInsuranceLog.setResp(GsonUtil.objectToJsonString(response));
            if("200".equals(response.getResultCode())){
                antChainInsuranceLog.setStatus(EnumAntChainLogStatus.SUCCESS);
                JSONObject responseData = JSON.parseObject(response.getResponseData());
                String balance = responseData.getString("accountBalance");
                InsuranceBalanceCache.updateBalance(balance);
            }else {
                errorMsg = response.getResultMsg();
            }
            log.info("【蚂蚁链保险-投保】响应：{}", GsonUtil.objectToJsonString(response));
        } catch (Exception e) {
            log.error("【蚂蚁链保险-投保】",e);
        }finally {
            antChainInsuranceLog.setCreateTime(new Date());
            antChainInsuranceLogDao.save(antChainInsuranceLog);
            if(StringUtils.isNotEmpty(errorMsg)){
                throw new HzsxBizException("-1",errorMsg);
            }
            return antChainInsuranceLog.getStatus().equals(EnumAntChainLogStatus.SUCCESS);
        }
    }

    @Override
    public Boolean cancelInsurance(String orderId,String channelId) {
        PlatformChannelDto platFormChannel = platformChannelService.getPlatFormChannel(channelId);
        AntChainInsuranceLog history = antChainInsuranceLogDao.getLastSuccessByOrderId(orderId);
        if(history==null || !history.getOperate().equals(EnumInsureOperate.INSURE)){
            throw new HzsxBizException("-1","未找到投保记录");
        }
        if(DateUtil.getBetweenDays(history.getCreateTime(),new Date())>7){
            throw new HzsxBizException("-1","超出退保时间,投保7天内可退保");
        }
        AntFinTechApiClient client = antChainClientFactory.getClient();
        CreateLeaseBizRequest request = new CreateLeaseBizRequest();
        JSONObject data = new JSONObject();
        data.put("orderId",orderId);
        data.put("methodType","1");
        data.put("insuredIdNo",platFormChannel.getEnterpriseLicenseNo());
        data.put("insuranceCorpIsvAccount",insuranceCorpIsvAccount);

        request.setBizContent(data.toJSONString());
        request.setType("insurance");
        request.setProductInstanceId(productInstanceId);
        request.setRegionName("CN-HANGZHOU-FINANCE");
        // 发送请求，并且获取响应结果
        AntChainInsuranceLog antChainInsuranceLog = new AntChainInsuranceLog();
        antChainInsuranceLog.setOrderId(orderId);
        antChainInsuranceLog.setOperate(EnumInsureOperate.CANCEL);
        antChainInsuranceLog.setReqParams(GsonUtil.objectToJsonString(request));
        antChainInsuranceLog.setStatus(EnumAntChainLogStatus.FAIL);
        try {
            log.info("【蚂蚁链保险-退保】参数：{}", GsonUtil.objectToJsonString(request));
            CreateLeaseBizResponse response = client.execute(request);
            antChainInsuranceLog.setResp(GsonUtil.objectToJsonString(response));
            if("200".equals(response.getResultCode())){
                antChainInsuranceLog.setStatus(EnumAntChainLogStatus.SUCCESS);
                JSONObject responseData = JSON.parseObject(response.getResponseData());
                String backAmount = responseData.getString("backAmount");
                InsuranceBalanceCache.addBalance(backAmount);
            }
            log.info("【蚂蚁链保险-退保】响应：{}", GsonUtil.objectToJsonString(response));
        } catch (Exception e) {
            log.error("【蚂蚁链保险-退保】",e);
            e.printStackTrace();
        }finally {
            antChainInsuranceLog.setCreateTime(new Date());
            antChainInsuranceLogDao.save(antChainInsuranceLog);
            return antChainInsuranceLog.getStatus().equals(EnumAntChainLogStatus.SUCCESS);
        }
    }
}
