package com.rent.service.components;


import com.rent.common.dto.components.response.FaceInitResponse;


/**
 * 人脸识别记录Service
 *
 * @author xiaoyao
 * @Date 2020-06-24 16:22
 */
public interface AlipayFaceAuthRecordService {

    /**
     * 超级云人脸认证
     * @param uid
     * @param userName
     * @param idCard
     * @param orderId
     * @return
     */
    FaceInitResponse superCloudFaceAuthInit(String uid, String userName, String idCard, String orderId);

    /**
     * 超级云人脸认证结果同步
     * @param certifyId
     */
    void superCloudFaceAuthInitAsync(String certifyId);

}