package com.rent.service.user.impl;

import com.rent.common.converter.user.UserEmergencyContactConverter;
import com.rent.common.dto.user.UserEmergencyContactDto;
import com.rent.dao.user.UserEmergencyContactDAO;
import com.rent.exception.HzsxBizException;
import com.rent.model.user.UserEmergencyContact;
import com.rent.service.components.SxService;
import com.rent.service.user.UserEmergencyContactService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserEmergencyContactServiceImpl implements UserEmergencyContactService {

    private final UserEmergencyContactDAO userEmergencyContactDAO;
    private final SxService sxService;

    @Override
    public List<UserEmergencyContactDto> getUserEmergencyContacts(String uid, Integer checked) {
        List<UserEmergencyContact> userEmergencyContacts;
        if (Objects.isNull(checked)) {
            userEmergencyContacts = userEmergencyContactDAO.queryUserEmergencyContactListByUid(uid);
        } else {
            userEmergencyContacts = userEmergencyContactDAO.queryUserEmergencyContactChecedListByUid(uid, checked == 1);
        }

        return UserEmergencyContactConverter.models2Dtos(userEmergencyContacts);
    }

    @Override
    public Boolean checkUserEmergencyContactCompleted(String uid) {
        List<UserEmergencyContact> userEmergencyContacts = userEmergencyContactDAO.queryUserEmergencyContactListByUid(uid);
        return userEmergencyContacts.size() >= 4;
    }

    @Override
    public Boolean addUserEmergencyContact(UserEmergencyContactDto dto, String uid) {
        UserEmergencyContact userEmergencyContact = userEmergencyContactDAO.getUserEmergencyContactByMobile(uid, dto.getMobile());
        if (!Objects.isNull(userEmergencyContact)) {
            throw new HzsxBizException("-1", "联系人已存在,请添加其他联系人");
        }

        UserEmergencyContact model = UserEmergencyContactConverter.dto2model(dto);
        // 首新二要素认证
        if (!sxService.mobileCheck(dto.getName(), dto.getMobile())) {
            model.setChecked(false);
            model.setUid(uid);
            model.setCreateTime(new Date());
            userEmergencyContactDAO.save(model);
            throw new HzsxBizException("-1", "手机号非本人实名,请重新填写");
        }

        model.setChecked(true);
        model.setUid(uid);
        model.setCreateTime(new Date());
        userEmergencyContactDAO.save(model);
        List<UserEmergencyContact> userEmergencyContacts = userEmergencyContactDAO.queryUserEmergencyContactChecedListByUid(uid, true);
        return userEmergencyContacts.size() >= 4;
    }
}
