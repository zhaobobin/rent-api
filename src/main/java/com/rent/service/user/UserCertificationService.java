        
package com.rent.service.user;

import com.rent.common.dto.components.response.IdCardOcrResponse;
import com.rent.common.dto.user.UidAndPhone;
import com.rent.common.dto.user.UserCertificationDto;

import java.util.List;
import java.util.Map;

/**
 * 用户认证表Service
 *
 * @author zhao
 * @Date 2020-06-18 15:06
 */
public interface UserCertificationService {

        /**
         * 用户进行实名认证
         * @param request
         * @return uid
         */
        String userCertificationAuth(UserCertificationDto request);

        /**
         * 根据用户uid获取用户认证信息
         * @param uid
         * @return
         */
        UserCertificationDto getByUid(String uid);

        /**
         * 获取脱敏信息
         * @param uid
         * @return
         */
        UserCertificationDto getDesensitizationByUid(String uid);

        /**
         * 根据用户ID列表获取对应的对应的认证信息
         * @param uidList
         * @return
         */
        Map<String, UserCertificationDto> queryUserCertificationList(List<String> uidList);

        /**
         * 根据手机号码获取用户uid
         * @param phones
         * @return
         */
        List<UidAndPhone> getUidAndPhoneSet(List<String> phones);

        /**
         *根据用户身份证号码获取uid列表
         * @param idCard
         * @return
         */
        List<String> getUidByIdCard(String idCard);

        /**
         *
         * @param frontUrl
         * @param backUrl
         * @param uid
         * @return
         */
        IdCardOcrResponse certificationOcr(String frontUrl, String backUrl, String uid);
}