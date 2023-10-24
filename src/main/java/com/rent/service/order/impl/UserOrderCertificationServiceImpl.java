package com.rent.service.order.impl;

import com.rent.common.dto.components.response.FaceInitResponse;
import com.rent.common.dto.user.UserCertificationDto;
import com.rent.common.enums.order.EnumOrderError;
import com.rent.common.enums.order.EnumOrderFaceStatus;
import com.rent.dao.order.UserOrdersDao;
import com.rent.exception.HzsxBizException;
import com.rent.model.order.UserOrders;
import com.rent.service.components.AlipayFaceAuthRecordService;
import com.rent.service.order.UserOrderCertificationService;
import com.rent.service.user.UserCertificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author xiaoyao
 * @version V1.0
 * @since v1.0 2020-5-20 15:04
 */
@Service
@RequiredArgsConstructor
public class UserOrderCertificationServiceImpl implements UserOrderCertificationService {

    private final UserOrdersDao userOrdersDao;
    private final UserCertificationService userCertificationService;
    private final AlipayFaceAuthRecordService alipayFaceAuthRecordService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public FaceInitResponse getAilFaceAuthCertifyUrl(String orderId) {
        UserOrders userOrders = userOrdersDao.selectOneByOrderId(orderId);
        if (EnumOrderFaceStatus.AUTHED.getCode().equals(userOrders.getFaceAuthStatus()) || EnumOrderFaceStatus.AUTHING.getCode().equals(userOrders.getFaceAuthStatus())) {
            throw new HzsxBizException(EnumOrderError.AUTHING_ORDER_FACE.getCode(),EnumOrderError.AUTHING_ORDER_FACE.getMsg(), this.getClass());
        }
        UserCertificationDto userCertificationDto = userCertificationService.getByUid(userOrders.getUid());
        if(userCertificationDto==null){
            throw new HzsxBizException(EnumOrderError.AUTHING_ORDER_FACE.getCode(),EnumOrderError.AUTHING_ORDER_FACE.getMsg(), this.getClass());
        }
        FaceInitResponse faceInitResponse = alipayFaceAuthRecordService.superCloudFaceAuthInit(userOrders.getUid(),userCertificationDto.getUserName(), userCertificationDto.getIdCard().toUpperCase(), orderId);
        return faceInitResponse;

    }
}
