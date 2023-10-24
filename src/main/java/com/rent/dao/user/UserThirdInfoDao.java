        
package com.rent.dao.user;

import com.rent.common.dto.user.UserThirdInfoDto;
import com.rent.config.mybatis.IBaseDao;
import com.rent.model.user.UserThirdInfo;

/**
 * UserThirdInfoDao
 *
 * @author zhao
 * @Date 2020-06-28 14:24
 */
public interface UserThirdInfoDao extends IBaseDao<UserThirdInfo> {
    /**
     * 根据用户ID和渠道信息获取用户第三方信息
     * @param uid
     * @param channelType
     * @return
     */
    UserThirdInfo getByUidAndChannelType(String uid, String channelType);

    UserThirdInfo getByUidAndChannelTypeV1(String uid, String channelType);

    /**
     * 据第三方用户的ID获取用户第三方信息
     * @param thirdId
     * @return
     */
    UserThirdInfo getByThirdId(String thirdId);

    /**
     * 根据手机号码和渠道信息获取用户第三方信息
     * @param telephone
     * @param channelType
     * @return
     */
    UserThirdInfo getByTelephoneAndChannelType(String telephone, String channelType);


    /**
     * 更新第三方用户信息的uid 把origin替换成newUid
     * @param origin
     * @param newUid
     */
    void replaceUid(String origin, String newUid);



    UserThirdInfoDto getByUid(String uid);
}
