        
package com.rent.dao.user;


import com.rent.common.dto.user.UidAndPhone;
import com.rent.common.dto.user.UserCertificationDto;
import com.rent.config.mybatis.IBaseDao;
import com.rent.model.user.UserCertification;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * UserCertificationDao
 *
 * @author zhao
 * @Date 2020-06-18 15:06
 */
public interface UserCertificationDao extends IBaseDao<UserCertification> {

    /**
     * 根据用户ID列表获取对应的对应的认证信息
     * @param uidList
     * @return
     */
    Map<String, UserCertificationDto> queryUserCertificationList(List<String> uidList);

    /**
     * 根据用户ID获取用户认证信息
     * @param uid
     * @return
     */
    UserCertification getByUid(String uid);

    /**
     * 初始化用户的认证信息
     * @param uid
     * @param idCard
     * @param userName
     * @param telephone
     * @param idCardFrontUrl
     * @param idCardBackUrl
     * @param limitDate
     */
    void initRecord(String uid, String idCard, String userName, String telephone, String idCardFrontUrl,
                    String idCardBackUrl, Date limitDate,Date startDate,String address,String sex,String issueOrg,String nation);

    /**
     * 据手机号码获取用户uid
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

}
