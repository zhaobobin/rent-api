package com.rent.dao.user;

import com.rent.config.mybatis.IBaseDao;
import com.rent.model.user.UserEmergencyContact;

import java.util.List;

public interface UserEmergencyContactDAO extends IBaseDao<UserEmergencyContact> {

    UserEmergencyContact getUserEmergencyContactByMobile(String uid, String mobile);

    List<UserEmergencyContact> queryUserEmergencyContactListByUid(String uid);

    List<UserEmergencyContact> queryUserEmergencyContactChecedListByUid(String uid, Boolean checked);

}
