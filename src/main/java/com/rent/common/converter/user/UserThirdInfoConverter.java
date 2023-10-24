        
package com.rent.common.converter.user;

import com.rent.common.dto.user.UserThirdInfoDto;
import com.rent.model.user.UserThirdInfo;


/**
 * 用户第三方信息表Service
 *
 * @author zhao
 * @Date 2020-06-28 14:24
 */
public class UserThirdInfoConverter {

    /**
     * model转dto
     *
     * @param model
     * @return
     */
    public static UserThirdInfoDto model2Dto(UserThirdInfo model) {
        if (model == null) {
            return null;
        }
        UserThirdInfoDto dto = new UserThirdInfoDto();
        dto.setId(model.getId());
        dto.setCreateTime(model.getCreateTime());
        dto.setUpdateTime(model.getUpdateTime());
        dto.setAvatar(model.getAvatar());
        dto.setNickName(model.getNickName());
        dto.setProvince(model.getProvince());
        dto.setCity(model.getCity());
        dto.setGender(model.getGender());
        dto.setUserType(model.getUserType());
        dto.setIsCertified(model.getIsCertified());
        dto.setIsStudentCetified(model.getIsStudentCetified());
        dto.setThirdId(model.getThirdId());
        dto.setUserStatus(model.getUserStatus());
        dto.setUid(model.getUid());
        dto.setUserName(model.getUserName());
        dto.setTelephone(model.getTelephone());
        dto.setChannel(model.getChannel());
        dto.setAppName(model.getAppName());
        return dto;
    }
}