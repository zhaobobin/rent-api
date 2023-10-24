package com.rent.service.order;

import com.rent.common.dto.components.response.FaceInitResponse;

/**
 * 订单认证相关接口
 *
 * @author xiaoyao
 * @version V1.0
 * @since v1.0 2020-5-20 14:58
 */
public interface UserOrderCertificationService {

    /**
     * 获取人脸认证url
     *
     * @param orderId 订单号
     * @return 人脸认证url
     */
    FaceInitResponse getAilFaceAuthCertifyUrl(String orderId);
}
