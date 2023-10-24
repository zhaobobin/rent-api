package com.rent.dao.user.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.user.UserEmergencyContactDAO;
import com.rent.mapper.user.UserEmergencyContactMapper;
import com.rent.model.user.UserEmergencyContact;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserEmergencyContactDAOImpl extends AbstractBaseDaoImpl<UserEmergencyContact, UserEmergencyContactMapper>
        implements UserEmergencyContactDAO {
    @Override
    public UserEmergencyContact getUserEmergencyContactByMobile(String uid, String mobile) {
        return getOne(new QueryWrapper<UserEmergencyContact>().eq("uid", uid).eq("mobile", mobile));
    }

    @Override
    public List<UserEmergencyContact> queryUserEmergencyContactListByUid(String uid) {
        return list(new QueryWrapper<UserEmergencyContact>().eq("uid", uid));
    }

    @Override
    public List<UserEmergencyContact> queryUserEmergencyContactChecedListByUid(String uid, Boolean checked) {
        return list(new QueryWrapper<UserEmergencyContact>().eq("uid", uid).eq("checked", checked));
    }
}
