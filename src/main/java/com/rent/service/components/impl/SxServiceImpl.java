package com.rent.service.components.impl;

import com.alibaba.fastjson.JSONObject;
import com.deepoove.poi.util.BytePictureUtils;
import com.rent.common.dto.components.bean.ExpressResult;
import com.rent.common.dto.components.bean.NodeList;
import com.rent.common.dto.components.bean.SxExpressResponse;
import com.rent.common.dto.components.dto.SxSignCompanyDto;
import com.rent.common.dto.components.dto.SxSignPersonDto;
import com.rent.common.dto.components.response.ContractResponse;
import com.rent.common.dto.components.response.ExpressInfoResponse;
import com.rent.common.dto.components.response.ExpressSignInfoResponse;
import com.rent.common.dto.components.response.IdCardOcrResponse;
import com.rent.common.enums.components.EnumSxExpressDeliveryStatus;
import com.rent.common.properties.SxProperties;
import com.rent.common.util.AsyncUtil;
import com.rent.common.util.CheckDataUtils;
import com.rent.common.util.GsonUtil;
import com.rent.common.util.OSSFileUtils;
import com.rent.exception.HzsxBizException;
import com.rent.service.components.SxService;
import com.rent.util.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.CountDownLatch;

@Service
@Slf4j
@RequiredArgsConstructor
public class SxServiceImpl implements SxService {

    private static String CERT_URL = "https://shouwei.shouxin168.com/api/lightning/product/query";
    private static String QUERY_URL = "https://shouwei.shouxin168.com/api/lightning/product/query";
    private static String UPLOAD_FILE_URL = "https://shouwei.shouxin168.com/api/contract/file/upload";
    private static String SIGN_CONTRACT_URL = "https://shouwei.shouxin168.com/api/lightning/product/query";
    private static final String SERVICE = "query_express_service";
    private static final String MODEL = "mode_query_express";
    private static final String OCR_SERVICE = "ident_number_ocr_service";
    private static final String OCR_MODEL = "mode_ident_number_ocr";
    private static final String SUCCESS_RESP_CODE = "SW0000";

    private static final String SX_REPORT_RESULT = "SX_REPORT_RESULT::";

    private final SxProperties sxProperties;
    private final OSSFileUtils ossFileUtils;

    @Override
    public boolean cert(String realName, String idCardNo) {
        Map<String, Object> dataMap = new HashMap<>(16);
        dataMap.put("name", realName);
        dataMap.put("ident_number", idCardNo);
        dataMap.put("service", "identity_two_elements_service");
        dataMap.put("mode", "mode_identity_two_elements");
        JSONObject result = OkHttpUtil.postFormData(CERT_URL, encryMap(dataMap), null);
        String responseCode = result.getString("resp_code");
        if (!SUCCESS_RESP_CODE.equals(responseCode)) {
            log.error(result.toJSONString());
        }
        String resultCode = result.getJSONObject("resp_data").getString("result_code");
        return "02".equals(resultCode);
    }

    @Override
    public boolean mobileCheck(String realName, String mobile) {
        Map<String, Object> dataMap = new HashMap<>(16);
        dataMap.put("name", realName);
        dataMap.put("phone", mobile);
        dataMap.put("service", "network_operator_triple_elements");
        dataMap.put("mode", "mode_two_elements");
        JSONObject result = OkHttpUtil.postFormData(CERT_URL, encryMap(dataMap), null);
        String responseCode = result.getString("resp_code");
        if (!SUCCESS_RESP_CODE.equals(responseCode)) {
            log.error(result.toJSONString());
        }
        String resultCode = result.getJSONObject("resp_data").getString("result_code");
        return "02".equals(resultCode);
    }

    @Override
    public String riskReport(String name, String idCardNo, String phone) {

        String infoMd5 = DigestUtils.md5DigestAsHex((name + "_" + idCardNo + "_" + phone).getBytes());
        String cacheKey = SX_REPORT_RESULT + infoMd5;
        Object cacheObject = RedisUtil.get(cacheKey);
        if (cacheObject != null) {
            return (String) cacheObject;
        }


        Map<String, Object> dataMap = new HashMap<>(16);
        dataMap.put("name", name);
        dataMap.put("ident_number", idCardNo);
        dataMap.put("phone", phone);
        dataMap.put("service", "risk_report_service");
        dataMap.put("mode", "JC604b4ad_CL5803109");
        JSONObject result = OkHttpUtil.postFormData(CERT_URL, encryMap(dataMap), null);
        log.info("【天狼星报告返回结果】result：{}", result);
        RedisUtil.set(cacheKey, result.toJSONString(), 60 * 60 * 15);
        return result.toJSONString();
    }

    @Override
    public ExpressInfoResponse getExpressList(String com, String no, String receiverPhone) {
        SxExpressResponse resp = queryExpressProcess(com, no, receiverPhone);
        if (!SUCCESS_RESP_CODE.equals(resp.getResp_code())) {
            throw new HzsxBizException("-1", "查询物流信息失败：" + resp.getResp_msg());
        }
        SxExpressResponse.RespData resp_data = resp.getResp_data();
        ExpressInfoResponse expressInfoResponse = new ExpressInfoResponse();
        ExpressResult expressResult = new ExpressResult();
        expressResult.setCom(resp_data.getType());
        expressResult.setNo(resp_data.getNumber());
        expressResult.setCompany(resp_data.getTypename());
        List<NodeList> nodeListList = new ArrayList<>(resp_data.getList().size());
        for (SxExpressResponse.Node node : resp_data.getList()) {
            NodeList nodeList = new NodeList();
            nodeList.setDatetime(node.getTime());
            nodeList.setRemark(node.getStatus());
            nodeListList.add(nodeList);
        }
        expressResult.setList(nodeListList);
        expressInfoResponse.setResult(expressResult);
        expressInfoResponse.setReason(resp.getResp_msg());
        expressInfoResponse.setResultcode(resp.getResp_code());
        return expressInfoResponse;
    }


    @Override
    public ExpressSignInfoResponse querySignInfo(String com, String no, String receiverPhone) {
        ExpressSignInfoResponse response = new ExpressSignInfoResponse();
        response.setResult(Boolean.FALSE);
        try {
            SxExpressResponse resp = queryExpressProcess(com, no, receiverPhone);
            if (resp.getResp_data() != null && EnumSxExpressDeliveryStatus.RECEIVED.getCode() == resp.getResp_data().getDeliverystatus()) {
                response.setSignTime(resp.getResp_data().getList().get(0).getTime());
                response.setResult(Boolean.TRUE);
            }
        } finally {
            return response;
        }
    }

    @Override
    public String uploadContract(File file) {
        try {
            List<OkHttpUtil.FileParam> params = new ArrayList<>();
            OkHttpUtil.FileParam fileParam = new OkHttpUtil.FileParam();
            fileParam.setName("contractFile")
                    .setFileBytes(BytePictureUtils.toByteArray(new FileInputStream(file)))
                    .setFilename(file.getName())
                    .setMediaType(MediaType.APPLICATION_PDF.getType());
            params.add(fileParam);
            JSONObject result = OkHttpUtil.postFormData(UPLOAD_FILE_URL, new HashMap<>(), params);
            log.info("【上传合同文件】响应：{}", result);
            return result.getJSONObject("resp_data").getString("contractCode");
        } catch (FileNotFoundException e) {
            log.error("【上传合同文件异常】", e);
            throw new HzsxBizException("-1", "上传合同文件异常");
        }
    }

    @Override
    public ContractResponse signContract(String contractCode, List<SxSignPersonDto> signPerson, List<SxSignCompanyDto> signCompany) {
        Map<String, Object> dataMap = new HashMap<>(16);
        dataMap.put("service", "online_signature_service");
        dataMap.put("mode", "mode_online_signature");
        dataMap.put("contractCode", contractCode);
        dataMap.put("personInfo", signPerson);
        dataMap.put("companyInfo", signCompany);
        log.info("【签署合同响应】请求：{}", JSONObject.toJSONString(dataMap));
        JSONObject result = OkHttpUtil.postFormData(SIGN_CONTRACT_URL, encryMap(dataMap), null);
        log.info("【签署合同响应】响应：{}", result);
        ContractResponse contractResponse = new ContractResponse();
        contractResponse.setDownloadUrl(result.getJSONObject("resp_data").getString("downloadUrl"));
        contractResponse.setSignerCodes(result.getJSONObject("resp_data").getString("signerCodes"));
        return contractResponse;
    }

    @Override
    public IdCardOcrResponse idCardOcr(String frontUrl, String backUrl) {
        IdCardOcrResponse response = new IdCardOcrResponse();
        try {
            CountDownLatch latch = new CountDownLatch(2);
            AsyncUtil.runAsync(() -> idCardOcr(latch, frontUrl, response, true));
            AsyncUtil.runAsync(() -> idCardOcr(latch, backUrl, response, false));
            latch.await();
            if (StringUtil.isNotEmpty(response.getIdCardNo()) && StringUtil.isNotEmpty(response.getUserName()) && StringUtil.isNotEmpty(response.getLimitDate())) {
                return response;
            } else {
                throw new HzsxBizException("-1", "身份证识别错误");
            }
        } catch (InterruptedException e) {
            log.error("【身份证识别错误】", e);
            throw new HzsxBizException("-1", "身份证识别错误");
        }
    }

    private void idCardOcr(CountDownLatch latch, String url, IdCardOcrResponse response, Boolean front) {
        try {
            log.info("【开始身份证识别】url={}", url);
            File file = ossFileUtils.downImgUrl(url);
            CheckDataUtils.judge(file.length() > 5242880L, "上传照片大小不能超过5m");
            Map<String, Object> dataMap = new HashMap<>(16);
            dataMap.put("imageBase64", FileUtils.getFileMD5(file));
            dataMap.put("service", OCR_SERVICE);
            dataMap.put("mode", OCR_MODEL);
            log.info("【身份证识别请求开始】url={}", url);
            JSONObject resp = OkHttpUtil.postFormData(QUERY_URL, encryMap(dataMap), null);
            log.info("【身份证识别请求结束】result={}", resp);
            JSONObject info = resp.getJSONObject("resp_data").getJSONObject("info");
            if (front) {
                response.setIdCardNo(info.getString("number"));
                response.setUserName(info.getString("name"));
                response.setAddress(info.getString("address"));
                response.setSex(info.getString("sex"));
                response.setNation(info.getString("nation"));
            } else {
                String[] time = info.getString("timelimit").split("-");
                response.setLimitDate(time[1]);
                response.setStartDate(time[0]);
                response.setIssueOrg(info.getString("authority"));
            }
        } catch (IOException e) {
            log.error("【身份证识别错误】", e);
            throw new HzsxBizException("-1", "身份证识别错误");
        } finally {
            latch.countDown();
        }
    }

    /**
     * 查询物流进度信息
     * https://buershujv.yuque.com/pz57ep/qw3xe2/mf1vx14u0tvusgtq
     *
     * @param com
     * @param no
     * @param receiverPhone
     * @return
     */
    private SxExpressResponse queryExpressProcess(String com, String no, String receiverPhone) {
        log.info("【查询物流信参数】com={},no={},receiverPhone={}", com, no, receiverPhone);
        Map<String, Object> dataMap = new HashMap<>(16);
        dataMap.put("phone", receiverPhone);
        dataMap.put("type", com);
        dataMap.put("number", no);
        dataMap.put("service", SERVICE);
        dataMap.put("mode", MODEL);
        JSONObject result = OkHttpUtil.postFormData(QUERY_URL, encryMap(dataMap), null);
        log.info("【查询物流信息结果】{}", result);
        return GsonUtil.jsonStringToObject(result.toJSONString(), SxExpressResponse.class);
    }


    private Map<String, String> encryMap(Map<String, Object> dataMap) {
        Iterator<Map.Entry<String, Object>> it = dataMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Object> entry = it.next();
            Object value = entry.getValue();
            if (value instanceof String && StringUtils.isBlank((String) value)) {
                it.remove();
                continue;
            }
            if (Objects.isNull(value)) {
                it.remove();
            }
        }
        String bizDataRaw = JSONObject.toJSON(dataMap).toString();
        String bizDataEncoded = AesUtils.encrypt(bizDataRaw, sxProperties.getAesKey());
        Map<String, String> requestMap = new HashMap<>(16);

        requestMap.put("institution_id", sxProperties.getInstitutionId());
        requestMap.put("biz_data", bizDataEncoded);
        requestMap.put("sign", "");
        return requestMap;
    }
}
