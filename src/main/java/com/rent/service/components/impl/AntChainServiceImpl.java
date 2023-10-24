package com.rent.service.components.impl;


import cn.com.antcloud.api.AntFinTechApiClient;
import cn.com.antcloud.api.twc.v1_0_0.request.CreateLeaseBizRequest;
import cn.com.antcloud.api.twc.v1_0_0.request.CreateLeaseBiznotaryRequest;
import cn.com.antcloud.api.twc.v1_0_0.request.CreateLeaseNotifyregisterRequest;
import cn.com.antcloud.api.twc.v1_0_0.request.QueryLeaseBizRequest;
import cn.com.antcloud.api.twc.v1_0_0.response.CreateLeaseBizResponse;
import cn.com.antcloud.api.twc.v1_0_0.response.CreateLeaseBiznotaryResponse;
import cn.com.antcloud.api.twc.v1_0_0.response.CreateLeaseNotifyregisterResponse;
import cn.com.antcloud.api.twc.v1_0_0.response.QueryLeaseBizResponse;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rent.common.dto.components.dto.AntChainSyncLeasePromise;
import com.rent.common.dto.components.dto.AntChainSyncLogistic;
import com.rent.common.dto.components.dto.AntChainSyncOrder;
import com.rent.common.dto.components.dto.AntChainSyncPerformance;
import com.rent.common.enums.components.AntiReqType;
import com.rent.common.enums.components.EnumAntChainLogStatus;
import com.rent.common.enums.product.AntChainProductClassEnum;
import com.rent.common.properties.AntChainProperties;
import com.rent.common.util.AntChainClientFactory;
import com.rent.common.util.GsonUtil;
import com.rent.config.outside.OutsideConfig;
import com.rent.dao.components.AntChainLogDao;
import com.rent.model.components.AntChainLog;
import com.rent.service.components.AntChainService;
import com.rent.util.MD5;
import com.rent.common.util.OSSFileUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Date;

/**
 * @author xiaoyao
 * @version V1.0
 * @date 2020-6-28 下午 4:24:04
 * @since 1.0
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class AntChainServiceImpl implements AntChainService {

    private static final String productInstanceId = "notary-api-prod";
    private static final String regionName = "CN-HANGZHOU-FINANCE";
    private static final String supplierName = OutsideConfig.COMPANY;

    private final AntChainLogDao antChainLogDao;
    private final AntChainProperties antChainProperties;
    private final AntChainClientFactory antChainClientFactory;
    private final OSSFileUtils ossFileUtils;


    @Override
    public void notifyRegister() {
        AntFinTechApiClient client = antChainClientFactory.getClient();
        // 构建请求
        CreateLeaseNotifyregisterRequest request = new CreateLeaseNotifyregisterRequest();
        request.setProductInstanceId(productInstanceId);
        request.setRegionName(regionName);
        // 发送请求，并且获取响应结果
        try {
            log.info("【蚂蚁链注册】参数：{}", GsonUtil.objectToJsonString(request));
            CreateLeaseNotifyregisterResponse response = client.execute(request);
            log.info("【蚂蚁链注册】响应：{}", GsonUtil.objectToJsonString(response));
        } catch (Exception e) {
            log.error("【蚂蚁链-注册-异常】",e);
            e.printStackTrace();
        }
    }

    @Override
    public Boolean syncLeasePromise(AntChainSyncLeasePromise req) {
        AntChainLog antChainLog = antChainLogDao.getSyncSuccessLog(req.getOrderId(), AntiReqType.LEASE_PROMISE);
        if(antChainLog!=null){
            return Boolean.TRUE;
        }
        AntFinTechApiClient client = antChainClientFactory.getClient();
        // 构建请求
        CreateLeaseBizRequest request = new CreateLeaseBizRequest();
        request.setProductInstanceId(productInstanceId);
        request.setRegionName(regionName);
        request.setType(AntiReqType.LEASE_PROMISE.getCode());
        JSONObject bizContent = new JSONObject();
        bizContent.put("orderId",req.getOrderId());
        bizContent.put("limit",0);
        bizContent.put("payDateList",req.getPayDateList());
        bizContent.put("payPeriod",req.getPayPeriod());
        bizContent.put("payMoneyList",req.getPayMoneyList());
        bizContent.put("leaseAlipayUid", OutsideConfig.PARENT_ID);
        request.setBizContent(bizContent.toJSONString());

        antChainLog = new AntChainLog();
        antChainLog.setOrderId(req.getOrderId());
        antChainLog.setType(AntiReqType.LEASE_PROMISE);
        antChainLog.setStatus(EnumAntChainLogStatus.FAIL);
        try {
            antChainLog.setReqParams(GsonUtil.objectToJsonString(request));
            log.info("【租赁平台统一接口服务-租赁订单承诺信息】参数：{}", antChainLog.getReqParams());
            CreateLeaseBizResponse response = client.execute(request);
            if("200".equals(response.getResultCode())){
                antChainLog.setStatus(EnumAntChainLogStatus.SUCCESS);
            }
            antChainLog.setResp(GsonUtil.objectToJsonString(response));
            log.info("【租赁平台统一接口服务-租赁订单承诺信息】响应：{}", antChainLog.getResp());
        } catch (Exception e) {
            log.error("【租赁平台统一接口服务-租赁订单承诺信息-异常】",e);
            antChainLog.setResp(e.getMessage());
        }finally {
            antChainLog.setCreateTime(new Date());
            antChainLogDao.save(antChainLog);
        }
        return antChainLog.getStatus().equals(EnumAntChainLogStatus.SUCCESS);
    }

    @Override
    public Boolean syncOrder(AntChainSyncOrder req) {
        //1.蚂蚁链-租赁平台统一接口服务-商品信息
        Boolean syncProductResult = syncProduct(req.getOrderId(),req.getProductId(),req.getProductName(),req.getProductPrice(),req.getProductModel(),req.getDepositPrice(),req.getAntChainProductClassEnum());
        if(!syncProductResult){
            return Boolean.FALSE;
        }
        //2.蚂蚁链-租赁平台统一接口服务-租赁订单用户信息
        Boolean syncUserResult = syncUser(req.getOrderId(),req.getUid(),req.getLoginTime(),req.getUserName(),req.getIdCard(),
                req.getUserIdCardFrontObjectKey(),req.getPhone(),req.getAlipayUID(),req.getLeaseCorpName(),
                req.getLeaseCorpId(),req.getLeaseCorpOwnerName());
        if(!syncUserResult){
            return Boolean.FALSE;
        }
        //3.蚂蚁链-租赁平台统一接口服务-租赁订单信息
        Boolean syncOrderResult = syncOrder(req.getOrderId(),req.getOrderCreateTime(),req.getOrderPayTime(),req.getOrderPayId(),
                req.getDepositFree(),req.getAcutalPreAuthFree(),req.getRentTerm(),req.getRentPricePerMonth(),
                req.getBuyOutPrice(),req.getLeaseContractUrl(),req.getUserAddress(),req.getProvinceCode(),
                req.getCityCode(),req.getDistrictCode(),req.getTotalRentMoney(),req.getLeaseCorpName(),
                req.getLeaseCorpId(),req.getLeaseCorpOwnerName(),req.getYidunScore());
        if(!syncOrderResult){
            return Boolean.FALSE;
        }
        //4.蚂蚁链-租赁平台统一接口服务-租赁订单商品信息
        return syncOrderProduct(req.getOrderId(),req.getProductId(),req.getProductName(),req.getProductPrice(),req.getSerialNumber(),req.getCostPrice());
    }



    /**
     * 蚂蚁链-租赁平台统一接口服务-商品信息
     */
    private Boolean syncProduct(String orderId, String productId, String productName, Long productPrice, String productModel, Long depositPrice, AntChainProductClassEnum antChainProductClass) {
        AntChainLog antChainLog = antChainLogDao.getSyncSuccessLog(orderId,AntiReqType.PRODUCT);
        if(antChainLog!=null){
            return Boolean.TRUE;
        }
        AntFinTechApiClient client = antChainClientFactory.getClient();
        // 构建请求
        CreateLeaseBizRequest request = new CreateLeaseBizRequest();
        request.setProductInstanceId(productInstanceId);
        request.setRegionName(regionName);
        request.setType(AntiReqType.PRODUCT.getCode());
        JSONObject bizContent = new JSONObject();
        bizContent.put("productId",productId);
        bizContent.put("productVersion","1");
        bizContent.put("productName",productName);
        bizContent.put("productPrice",productPrice);
        bizContent.put("mainClass",antChainProductClass.getParent());
        bizContent.put("subClass",antChainProductClass.getCode());
        bizContent.put("supplierName",supplierName);
        bizContent.put("supplierId",antChainProperties.getLeaseId());
        bizContent.put("installPrice",0L);
        bizContent.put("productOrigin",2);
        bizContent.put("realStock",100);
        bizContent.put("estimatedShipment",100);
        bizContent.put("productDetailInfo","-");
        bizContent.put("productUrl","-");
        bizContent.put("productBrand","-");
        bizContent.put("productModel",productModel);
        bizContent.put("depositPrice",depositPrice);
        request.setBizContent(bizContent.toJSONString());

        antChainLog = new AntChainLog();
        antChainLog.setOrderId(orderId);
        antChainLog.setType(AntiReqType.PRODUCT);
        antChainLog.setStatus(EnumAntChainLogStatus.FAIL);
        try {
            antChainLog.setReqParams(GsonUtil.objectToJsonString(request));
            log.info("【租赁平台统一接口服务-商品信息】参数：{}", antChainLog.getReqParams());
            CreateLeaseBizResponse response = client.execute(request);
            if("200".equals(response.getResultCode())){
                antChainLog.setStatus(EnumAntChainLogStatus.SUCCESS);
            }
            antChainLog.setResp(GsonUtil.objectToJsonString(response));
            log.info("【租赁平台统一接口服务-商品信息】响应：{}", antChainLog.getResp());
        } catch (Exception e) {
            log.error("【租赁平台统一接口服务-商品信息-异常】",e);
            antChainLog.setResp(e.getMessage());
        }finally {
            antChainLog.setCreateTime(new Date());
            antChainLogDao.save(antChainLog);
        }
        return antChainLog.getStatus().equals(EnumAntChainLogStatus.SUCCESS);
    }

    /**
     * 蚂蚁链-租赁平台统一接口服务-租赁订单用户信息
     */
    private Boolean syncUser(String orderId,String uid,String loginTime,String userName,String idCard,
                          String userIdCardFrontObjectKey,String phone,String alipayUID,
                          String leaseCorpName,String leaseCorpId,String leaseCorpOwnerName) {
        AntChainLog antChainLog = antChainLogDao.getSyncSuccessLog(orderId,AntiReqType.USER);
        if(antChainLog!=null){
            return Boolean.TRUE;
        }
        String userImageHash = bizNotary(userIdCardFrontObjectKey,leaseCorpId,leaseCorpName,leaseCorpOwnerName);
        AntFinTechApiClient client = antChainClientFactory.getClient();
        // 构建请求
        CreateLeaseBizRequest request = new CreateLeaseBizRequest();
        request.setProductInstanceId(productInstanceId);
        request.setRegionName(regionName);
        request.setType(AntiReqType.USER.getCode());
        JSONObject bizContent = new JSONObject();
        bizContent.put("orderId",orderId);
        bizContent.put("loginId",uid);
        bizContent.put("loginType",1);
        bizContent.put("loginTime",loginTime);
        bizContent.put("userName",userName);
        bizContent.put("userId",idCard);
        bizContent.put("userImageHash",userImageHash);
        bizContent.put("userImageUrl",userIdCardFrontObjectKey);
        bizContent.put("userPhoneNumber",phone);
        bizContent.put("userType",2);
        bizContent.put("alipayUID",alipayUID);
        bizContent.put("leaseCorpName",leaseCorpName);
        bizContent.put("leaseCorpId",leaseCorpId);
        bizContent.put("leaseCorpOwnerName",leaseCorpOwnerName);
        bizContent.put("lesseeType",2);
        request.setBizContent(bizContent.toJSONString());

        antChainLog = new AntChainLog();
        antChainLog.setOrderId(orderId);
        antChainLog.setType(AntiReqType.USER);
        antChainLog.setStatus(EnumAntChainLogStatus.FAIL);
        try {
            antChainLog.setReqParams(GsonUtil.objectToJsonString(request));
            log.info("【租赁平台统一接口服务-租赁订单用户信息】参数：{}", antChainLog.getReqParams());
            CreateLeaseBizResponse response = client.execute(request);
            if("200".equals(response.getResultCode())){
                antChainLog.setStatus(EnumAntChainLogStatus.SUCCESS);
            }
            antChainLog.setResp(GsonUtil.objectToJsonString(response));
            log.info("【租赁平台统一接口服务-租赁订单用户信息】响应：{}", antChainLog.getResp());
        } catch (Exception e) {
            log.error("【租赁平台统一接口服务-租赁订单用户信息-异常】",e);
            antChainLog.setResp(e.getMessage());
        }finally {
            antChainLog.setCreateTime(new Date());
            antChainLogDao.save(antChainLog);
        }
        return antChainLog.getStatus().equals(EnumAntChainLogStatus.SUCCESS);
    }

    /**
     * 同步订单信息
     */
    public Boolean syncOrder(String orderId, String orderCreateTime,String orderPayTime,String orderPayId,
                          Long depositFree, Long acutalPreAuthFree,Integer rentTerm,Long rentPricePerMonth,Long buyOutPrice,
                          String leaseContractUrl, String userAddress,Integer provinceCode,Integer cityCode,Integer districtCode,
                          Long totalRentMoney, String leaseCorpName,String leaseCorpId,String leaseCorpOwnerName,String yidunScore) {
        AntChainLog antChainLog = antChainLogDao.getSyncSuccessLog(orderId,AntiReqType.ORDER);
        if(antChainLog!=null){
            return Boolean.TRUE;
        }
        String leaseServiceFileHash = bizNotary(leaseContractUrl,leaseCorpId,leaseCorpName,leaseCorpOwnerName);
        AntFinTechApiClient client = antChainClientFactory.getClient();
        // 构建请求
        CreateLeaseBizRequest request = new CreateLeaseBizRequest();
        request.setProductInstanceId(productInstanceId);
        request.setRegionName(regionName);
        request.setType(AntiReqType.ORDER.getCode());
        JSONObject bizContent = new JSONObject();
        bizContent.put("orderId",orderId);
        bizContent.put("orderCreateTime",orderCreateTime);
        bizContent.put("orderPayTime",orderPayTime);
        bizContent.put("orderPayId", orderPayId);
        bizContent.put("orderPayType",1);
        bizContent.put("depositFree",depositFree);
        bizContent.put("acutalPreAuthFree",acutalPreAuthFree);
        bizContent.put("rentTerm",rentTerm);
        bizContent.put("rentPricePerMonth",rentPricePerMonth);
        bizContent.put("buyOutPrice",buyOutPrice);
        bizContent.put("leaseServiceFileHash",leaseServiceFileHash);
        bizContent.put("leaseContractUrl",leaseContractUrl);
        bizContent.put("userAddress",userAddress);
        bizContent.put("provinceCode",provinceCode);
        bizContent.put("cityCode",cityCode);
        bizContent.put("districtCode",districtCode);
        bizContent.put("dueMode",2);
        bizContent.put("storeType",2);
        bizContent.put("totalRentMoney",totalRentMoney);
        if(yidunScore!=null){
            String[] yidunScores = yidunScore.split("\\.");
            yidunScore = yidunScores[0];
            bizContent.put("yidunScore",Long.parseLong(yidunScore));
        }
        request.setBizContent(bizContent.toJSONString());

        antChainLog = new AntChainLog();
        antChainLog.setOrderId(orderId);
        antChainLog.setType(AntiReqType.ORDER);
        antChainLog.setStatus(EnumAntChainLogStatus.FAIL);
        try {
            antChainLog.setReqParams(GsonUtil.objectToJsonString(request));
            log.info("【租赁平台统一接口服务-租赁订单信息】参数：{}",antChainLog.getReqParams());
            CreateLeaseBizResponse response = client.execute(request);
            if("200".equals(response.getResultCode())){
                antChainLog.setStatus(EnumAntChainLogStatus.SUCCESS);
            }
            antChainLog.setResp(GsonUtil.objectToJsonString(response));
            log.info("【租赁平台统一接口服务-租赁订单信息】响应：{}",antChainLog.getResp());
        } catch (Exception e) {
            log.error("【租赁平台统一接口服务-租赁订单信息-异常】",e);
            antChainLog.setResp(e.getMessage());
        }finally {
            antChainLog.setCreateTime(new Date());
            antChainLogDao.save(antChainLog);
        }
        return antChainLog.getStatus().equals(EnumAntChainLogStatus.SUCCESS);
    }



    /**
     * 蚂蚁链-租赁平台统一接口服务-租赁订单商品信息
     */
    public Boolean syncOrderProduct(String orderId,String productId,String productName,Long productPrice,String serialNumber,Long costPrice) {
        AntChainLog antChainLog = antChainLogDao.getSyncSuccessLog(orderId,AntiReqType.ORDER_PRODUCT);
        if(antChainLog!=null){
            return Boolean.TRUE;
        }
        AntFinTechApiClient client = antChainClientFactory.getClient();
        // 构建请求
        CreateLeaseBizRequest request = new CreateLeaseBizRequest();
        request.setProductInstanceId(productInstanceId);
        request.setRegionName(regionName);
        request.setType(AntiReqType.ORDER_PRODUCT.getCode());
        JSONObject bizContent = new JSONObject();
        JSONArray productInfoArray = new JSONArray();
        JSONObject productInfo = new JSONObject();
        productInfo.put("productId",productId);
        productInfo.put("productName",productName);
        productInfo.put("productNumber",1);
        productInfo.put("productImeiId",StringUtils.isEmpty(serialNumber) ? productId : serialNumber);
        productInfo.put("productVersion","1");
        productInfo.put("productPrice",productPrice);
        productInfo.put("supplierId",antChainProperties.getLeaseId());
        productInfoArray.add(productInfo);
        bizContent.put("productInfo",productInfoArray);
        bizContent.put("leaseId",antChainProperties.getLeaseId());
        bizContent.put("orderId",orderId);
        request.setBizContent(bizContent.toJSONString());

        antChainLog = new AntChainLog();
        antChainLog.setOrderId(orderId);
        antChainLog.setType(AntiReqType.ORDER_PRODUCT);
        antChainLog.setStatus(EnumAntChainLogStatus.FAIL);
        try {
            antChainLog.setReqParams(GsonUtil.objectToJsonString(request));
            log.info("【租赁平台统一接口服务-租赁订单商品信息】参数：{}", antChainLog.getReqParams());
            CreateLeaseBizResponse response = client.execute(request);
            if("200".equals(response.getResultCode())){
                antChainLog.setStatus(EnumAntChainLogStatus.SUCCESS);
            }
            antChainLog.setResp(GsonUtil.objectToJsonString(response));
            log.info("【租赁平台统一接口服务-租赁订单商品信息】响应：{}", antChainLog.getResp());
        } catch (Exception e) {
            log.error("【租赁平台统一接口服务-租赁订单商品信息-异常】",e);
            antChainLog.setResp(e.getMessage());
        }finally {
            antChainLog.setCreateTime(new Date());
            antChainLogDao.save(antChainLog);
        }
        return antChainLog.getStatus().equals(EnumAntChainLogStatus.SUCCESS);
    }

    @Override
    public Boolean syncLogistic(AntChainSyncLogistic req) {
        String arriveConfirmHash = bizNotaryStr(req.getArriveConfirmUrl(),req.getLeaseCorpName(),req.getLeaseCorpId(),req.getLeaseCorpOwnerName());
        AntFinTechApiClient client = antChainClientFactory.getClient();
        // 构建请求
        CreateLeaseBizRequest request = new CreateLeaseBizRequest();
        request.setProductInstanceId(productInstanceId);
        request.setRegionName(regionName);
        request.setType(AntiReqType.LOGISTIC.getCode());
        JSONObject bizContent = new JSONObject();
        bizContent.put("orderId",req.getOrderId());
        bizContent.put("leaseId",antChainProperties.getLeaseId());
        if(StringUtils.isEmpty(req.getArriveConfirmTime())){
            bizContent.put("logisticStatus",1);
        }else {
            bizContent.put("arriveConfirmUrl",req.getArriveConfirmUrl());
            bizContent.put("arriveConfirmHash",arriveConfirmHash);
            bizContent.put("logisticStatus",3);
            bizContent.put("arriveConfirmTime",req.getArriveConfirmTime());
        }
        bizContent.put("logisticCompanyName",req.getLogisticCompanyName());
        bizContent.put("leaseStatus",1);
        bizContent.put("logisticsOrderId",req.getLogisticsOrderId());
        bizContent.put("deliverTime",req.getDeliverTime());
        request.setBizContent(bizContent.toJSONString());

        AntChainLog antChainLog = new AntChainLog();
        antChainLog.setOrderId(req.getOrderId());
        antChainLog.setType(AntiReqType.LOGISTIC);
        antChainLog.setStatus(EnumAntChainLogStatus.FAIL);
        try {
            antChainLog.setReqParams(GsonUtil.objectToJsonString(request));
            log.info("【租赁平台统一接口服务-租赁订单物流信息】参数：{}", antChainLog.getReqParams());
            CreateLeaseBizResponse response = client.execute(request);
            if("200".equals(response.getResultCode())){
                antChainLog.setStatus(EnumAntChainLogStatus.SUCCESS);
            }
            antChainLog.setResp(GsonUtil.objectToJsonString(response));
            log.info("【租赁平台统一接口服务-租赁订单物流信息】响应：{}", antChainLog.getResp());
        } catch (Exception e) {
            log.error("【租赁平台统一接口服务-租赁订单物流信息-异常】",e);
            antChainLog.setResp(e.getMessage());
        }finally {
            antChainLog.setCreateTime(new Date());
            antChainLogDao.save(antChainLog);
        }
        return antChainLog.getStatus().equals(EnumAntChainLogStatus.SUCCESS);
    }



    @Override
    public Boolean syncPerformance(AntChainSyncPerformance req) {
        AntFinTechApiClient client = antChainClientFactory.getClient();
        // 构建请求
        CreateLeaseBizRequest request = new CreateLeaseBizRequest();
        request.setProductInstanceId(productInstanceId);
        request.setRegionName(regionName);
        request.setType(AntiReqType.RENTAL.getCode());
        JSONObject bizContent = new JSONObject();
        bizContent.put("orderId",req.getOrderId());
        bizContent.put("leaseTermIndex",req.getLeaseTermIndex());
        bizContent.put("rentalReturnState",req.getRentalReturnState());
        bizContent.put("rentalMoney",req.getRentalMoney());
        bizContent.put("returnTime",req.getReturnTime());
        bizContent.put("returnWay",req.getReturnWay());
        bizContent.put("returnVoucherType",req.getReturnVoucherType());
        bizContent.put("returnVoucherSerial",req.getReturnVoucherSerial());
        if(req.getCharge()!=null){
            Long charge = req.getCharge()/100L*100L;
            bizContent.put("charge",charge);
        }
        bizContent.put("remainTerm",req.getRemainTerm());
        request.setBizContent(bizContent.toJSONString());

        AntChainLog antChainLog = new AntChainLog();
        antChainLog.setOrderId(req.getOrderId());
        antChainLog.setType(AntiReqType.RENTAL);
        antChainLog.setStatus(EnumAntChainLogStatus.FAIL);
        try {
            antChainLog.setReqParams(GsonUtil.objectToJsonString(request));
            log.info("【租赁平台统一接口服务-租赁订单履约信息】参数：{}", antChainLog.getReqParams());
            CreateLeaseBizResponse response = client.execute(request);
            if("200".equals(response.getResultCode())){
                antChainLog.setStatus(EnumAntChainLogStatus.SUCCESS);
            }
            antChainLog.setResp(GsonUtil.objectToJsonString(response));
            log.info("【租赁平台统一接口服务-租赁订单履约信息】响应：{}", antChainLog.getResp());
        } catch (Exception e) {
            log.error("【租赁平台统一接口服务-租赁订单履约信息-异常】",e);
            antChainLog.setResp(e.getMessage());
        }finally {
            antChainLog.setCreateTime(new Date());
            antChainLogDao.save(antChainLog);
        }
        return antChainLog.getStatus().equals(EnumAntChainLogStatus.SUCCESS);
    }

    @Override
    public void queryOrder(String orderId) {
        AntFinTechApiClient client = antChainClientFactory.getClient();
        // 构建请求
        QueryLeaseBizRequest request = new QueryLeaseBizRequest();
        JSONObject param = new JSONObject();
        param.put("orderId",orderId);
        request.setBizContent(param.toJSONString());
        request.setProductInstanceId(productInstanceId);
        request.setRegionName(regionName);
        request.setType(AntiReqType.ORDER.getCode());
        try {
            log.info("【租赁平台统一接口服务-查询订单信息】请求：{}", GsonUtil.objectToJsonString(request));
            QueryLeaseBizResponse response = client.execute(request);
            log.info("【租赁平台统一接口服务-查询订单信息】响应：{}", GsonUtil.objectToJsonString(response));
        } catch (Exception e) {
            log.error("【租赁平台统一接口服务-查询订单信息-异常】",e);
        }
    }

    @Override
    public void queryOrderProduct(String orderId) {
        AntFinTechApiClient client = antChainClientFactory.getClient();
        // 构建请求
        QueryLeaseBizRequest request = new QueryLeaseBizRequest();
        JSONObject param = new JSONObject();
        param.put("orderId",orderId);
        request.setBizContent(param.toJSONString());
        request.setProductInstanceId(productInstanceId);
        request.setRegionName(regionName);
        request.setType(AntiReqType.ORDER_PRODUCT.getCode());
        try {
            log.info("【租赁平台统一接口服务-查询订单商品信息】请求：{}", GsonUtil.objectToJsonString(request));
            QueryLeaseBizResponse response = client.execute(request);
            log.info("【租赁平台统一接口服务-查询订单商品信息】响应：{}", GsonUtil.objectToJsonString(response));
        } catch (Exception e) {
            log.error("【租赁平台统一接口服务-查询订单信息-异常】",e);
        }
    }

    @Override
    public void queryOrderUSer(String orderId) {
        AntFinTechApiClient client = antChainClientFactory.getClient();
        // 构建请求
        QueryLeaseBizRequest request = new QueryLeaseBizRequest();
        JSONObject param = new JSONObject();
        param.put("orderId",orderId);
        request.setBizContent(param.toJSONString());
        request.setProductInstanceId(productInstanceId);
        request.setRegionName(regionName);
        request.setType(AntiReqType.USER.getCode());
        try {
            log.info("【租赁平台统一接口服务-查询订单用户】请求：{}", GsonUtil.objectToJsonString(request));
            QueryLeaseBizResponse response = client.execute(request);
            log.info("【租赁平台统一接口服务-查询订单用户】响应：{}", GsonUtil.objectToJsonString(response));
        } catch (Exception e) {
            log.error("【租赁平台统一接口服务-查询订用户-异常】",e);
        }
    }

    @Override
    public void queryProduct(String productId,String productVersion) {
        AntFinTechApiClient client = antChainClientFactory.getClient();
        // 构建请求
        QueryLeaseBizRequest request = new QueryLeaseBizRequest();
        JSONObject param = new JSONObject();
        param.put("productId",productId);
        param.put("productVersion",productVersion);
        request.setBizContent(param.toJSONString());
        request.setProductInstanceId(productInstanceId);
        request.setRegionName(regionName);
        request.setType(AntiReqType.PRODUCT.getCode());
        try {
            log.info("【租赁平台统一接口服务-查询商品信息】请求：{}", GsonUtil.objectToJsonString(request));
            QueryLeaseBizResponse response = client.execute(request);
            log.info("【租赁平台统一接口服务-查询商品信息】响应：{}", GsonUtil.objectToJsonString(response));
        } catch (Exception e) {
            log.error("【租赁平台统一接口服务-查询订单信息-异常】",e);
        }
    }


    private String bizNotaryStr(String str,String leaseCorpId,String leaseCorpName,String leaseCorpOwnerName) {
        String md5=null;
        try {
            AntFinTechApiClient client = antChainClientFactory.getClient();
            // 构建请求
            CreateLeaseBiznotaryRequest request = new CreateLeaseBiznotaryRequest();
            request.setProductInstanceId(productInstanceId);
            request.setRegionName(regionName);
            md5 = MD5.getMD5(str);
            request.setHash(md5);
            request.setLeaseCorpId(leaseCorpId);
            request.setLeaseCorpName(leaseCorpName);
            request.setLeaseCorpOwnerName(leaseCorpOwnerName);
            // 发送请求，并且获取响应结果
            log.info("【租赁平台统一接口服务-文件存证-字符串】参数：{}", GsonUtil.objectToJsonString(request));
            CreateLeaseBiznotaryResponse response = client.execute(request);
            log.info("【租赁平台统一接口服务-文件存证-字符串】响应：{}", GsonUtil.objectToJsonString(response));
        }catch (Exception e){
            log.error("【租赁平台统一接口服务-商品信息-异常】",e);
        }
        return md5;
    }


    private String bizNotary(String objectKey,String leaseCorpId,String leaseCorpName,String leaseCorpOwnerName) {
        String md5=null;
        try {
            AntFinTechApiClient client = antChainClientFactory.getClient();
            // 构建请求
            CreateLeaseBiznotaryRequest request = new CreateLeaseBiznotaryRequest();
            request.setProductInstanceId(productInstanceId);
            request.setRegionName(regionName);
            File file = ossFileUtils.downImgUrl(objectKey);
            md5 = MD5.getFileMD5(file);
            request.setHash(md5);
            request.setLeaseCorpId(leaseCorpId);
            request.setLeaseCorpName(leaseCorpName);
            request.setLeaseCorpOwnerName(leaseCorpOwnerName);
            // 发送请求，并且获取响应结果
            log.info("【租赁平台统一接口服务-文件存证】参数：{}", GsonUtil.objectToJsonString(request));
            CreateLeaseBiznotaryResponse response = client.execute(request);
            log.info("【租赁平台统一接口服务-文件存证】响应：{}", GsonUtil.objectToJsonString(response));
        }catch (Exception e){
            log.error("【租赁平台统一接口服务-商品信息-异常】",e);
        }
        return md5;
    }
}
