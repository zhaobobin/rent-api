package com.rent.service.components.impl;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.http.ProtocolType;
import com.aliyuncs.profile.DefaultProfile;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rent.common.dto.components.response.FaceInitResponse;
import com.rent.common.enums.common.EnumComponentsError;
import com.rent.common.enums.common.EnumRpcError;
import com.rent.common.enums.components.EnumFaceAuthStatus;
import com.rent.common.enums.order.EnumOrderFaceStatus;
import com.rent.common.properties.FaceProperties;
import com.rent.dao.components.AlipayFaceAuthRecordDao;
import com.rent.dao.order.UserOrdersDao;
import com.rent.dao.user.UserCertificationDao;
import com.rent.exception.HzsxBizException;
import com.rent.model.components.AlipayFaceAuthRecord;
import com.rent.model.order.UserOrders;
import com.rent.model.user.UserCertification;
import com.rent.service.components.AlipayFaceAuthRecordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 人脸识别记录Service
 *
 * @author xiaoyao
 * @Date 2020-06-24 16:22
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class AlipayFaceAuthRecordServiceImpl implements AlipayFaceAuthRecordService {

    private static final String domain = "saf.cn-shanghai.aliyuncs.com";
    private static final String version = "2017-03-31";
    private static final String action = "ExecuteRequest";
    private static final String REGION_ID = "cn-shanghai";

    private final AlipayFaceAuthRecordDao alipayFaceAuthRecordDao;
    private final UserCertificationDao userCertificationDao;
    private final UserOrdersDao userOrdersDao;
    private final FaceProperties faceProperties;

    @Override
    public FaceInitResponse superCloudFaceAuthInit(String uid, String userName, String idCard, String orderId) {
        UserCertification user = userCertificationDao.getByUid(uid);
        if (null == user) {
            throw new HzsxBizException(EnumRpcError.DATA_ERROR.getCode(), "用户认证信息不存在");
        }

        DefaultProfile profile = DefaultProfile.getProfile(REGION_ID, faceProperties.getKeyId(), faceProperties.getKeySecret());
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        UUID uuid = UUID.randomUUID();
        String outerOrderNo = uuid.toString().replace("-", "");
        request.setSysMethod(MethodType.POST);
        request.setSysDomain(domain);
        request.setSysVersion(version);
        request.setSysAction(action);
        request.setSysProtocol(ProtocolType.HTTPS);
        // 业务详细参数。
        Map<String, Object> serviceParams = new HashMap<String, Object>();
        // 发起认证请求。
        serviceParams.put("method", "init");
        serviceParams.put("sceneId", faceProperties.getSceneId());
        serviceParams.put("outerOrderNo", outerOrderNo);
        //当用户在iOS或安卓平台发起认证时，认证场景码是 FACE_SDK;在小程序中，认证场景码则为 FACE。
        serviceParams.put("bizCode", "FACE");
        serviceParams.put("identityType", "CERT_INFO");
        serviceParams.put("certType", "IDENTITY_CARD");
        serviceParams.put("certNo", user.getIdCard());
        serviceParams.put("certName", user.getUserName());
        serviceParams.put("returnUrl", null);
        request.putBodyParameter("ServiceParameters", JSON.toJSONString(serviceParams));
        // 固定值，Service = fin_face_verify。
        request.putBodyParameter("Service", "fin_face_verify");
        try {
            CommonResponse response = client.getCommonResponse(request);
            String result = response.getData();
            JSONObject json = JSONObject.fromObject(result);
            String certifyUrl = null;
            String certifyId = null;
            String code = null;
            if (json.has("Code")) {
                code = json.getString("Code");
            }
            if (json.has("Data")) {
                String data = json.getString("Data");
                JSONObject dataJson = JSONObject.fromObject(data);
                if (dataJson.has("certifyUrl")) {
                    certifyUrl = dataJson.getString("certifyUrl");
                }
                if (dataJson.has("certifyId")) {
                    certifyId = dataJson.getString("certifyId");
                }
            }
            if (StringUtils.isNotEmpty(certifyId) && StringUtils.isNotEmpty(certifyUrl)) {
                AlipayFaceAuthRecord record = AlipayFaceAuthRecord.builder()
                        .uid(uid)
                        .userName(userName)
                        .idCard(idCard)
                        .certifyId(certifyId)
                        .createTime(new Date())
                        .updateTime(new Date())
                        .status(EnumFaceAuthStatus.INIT)
                        .orderId(orderId)
                        .build();
                alipayFaceAuthRecordDao.save(record);
                return FaceInitResponse.builder()
                        .faceUrl(certifyUrl)
                        .certifyId(certifyId)
                        .build();
            } else {
                log.error("人脸认证初始化失败 outerOrderNo:{}, orderId:{} ,result:{}", outerOrderNo, orderId, result);
                if (StringUtils.isNotEmpty(code) && "403".equals(code)) {
                    throw new NullPointerException("【超级云实人认证可能欠费啦】,赶充值呀" + code);
                }
                throw new HzsxBizException(EnumComponentsError.RPC_ERROR.getCode(), "人脸认证初始化失败", this.getClass());
            }
        } catch (Exception e) {
            log.error("人脸认证初始化失败", e);
            throw new HzsxBizException(EnumComponentsError.RPC_ERROR.getCode(), "人脸认证初始化失败", this.getClass());
        }
    }


    @Override
    public void superCloudFaceAuthInitAsync(String certifyId) {
        DefaultProfile profile = DefaultProfile.getProfile(REGION_ID, faceProperties.getKeyId(), faceProperties.getKeySecret());
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain(domain);
        request.setSysVersion(version);
        request.setSysAction(action);
        request.setSysProtocol(ProtocolType.HTTPS);
        // 业务详细参数。
        Map<String, Object> serviceParams = new HashMap<>();
        // 查询认证结果。
        serviceParams.put("method", "query");
        serviceParams.put("certifyId", certifyId);
        serviceParams.put("sceneId", faceProperties.getSceneId());
        request.putBodyParameter("ServiceParameters", JSON.toJSONString(serviceParams));
        // 固定值，Service = fin_face_verify。
        request.putBodyParameter("Service", "fin_face_verify");
        try {
            CommonResponse response = client.getCommonResponse(request);
            String data = response.getData();
            JSONObject json = JSONObject.fromObject(data);
            String passed = null;
            String code = null;
            if (json.has("Code")) {
                code = json.getString("Code");
            }
            if (json.has("Data")) {
                String data1 = json.getString("Data");
                JSONObject dataJson = JSONObject.fromObject(data1);
                if (dataJson.has("passed")) {
                    passed = dataJson.getString("passed");
                }
            }
            log.info("超级云实人认证返回结果 json：{}", json);
            if (StringUtils.isNotEmpty(passed) && "T".equals(passed)) {
                this.faceAuthInitAsync(certifyId);
            } else {
                log.error("人脸认证回调失败 certifyId:{}", certifyId);
                if (StringUtils.isNotEmpty(code) && "403".equals(code)) {
                    throw new NullPointerException("【超级云实人认证可能欠费啦】,赶充值呀" + code);
                }
            }
        } catch (Exception e) {
            log.error("人脸认证回调失败", e);
            throw new HzsxBizException(EnumComponentsError.RPC_ERROR.getCode(), "人脸认证回调失败", this.getClass());
        }
    }


    private void faceAuthInitAsync(String certifyId) {
        AlipayFaceAuthRecord alipayFaceAuthRecord = alipayFaceAuthRecordDao.getOne(new QueryWrapper<>(
                AlipayFaceAuthRecord.builder().certifyId(certifyId).build()));
        if (Objects.nonNull(alipayFaceAuthRecord)) {
            alipayFaceAuthRecord.setStatus(EnumFaceAuthStatus.SUCCESS);
            alipayFaceAuthRecord.setUpdateTime(new Date());
            alipayFaceAuthRecordDao.updateById(alipayFaceAuthRecord);

            UserOrders userOrders = new UserOrders();
            userOrders.setOrderId(alipayFaceAuthRecord.getOrderId());
            userOrders.setFaceAuthStatus(EnumOrderFaceStatus.AUTHED.getCode());
            userOrders.setUpdateTime(new Date());
            userOrdersDao.updateByOrderId(userOrders);
        }
    }
}