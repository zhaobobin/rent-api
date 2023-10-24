package com.rent.common.converter.user;

import cn.hutool.core.collection.CollectionUtil;
import com.rent.common.dto.user.UserAddressDto;
import com.rent.common.dto.user.UserEmergencyContactDto;
import com.rent.model.user.UserAddress;
import com.rent.model.user.UserEmergencyContact;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class UserEmergencyContactConverter {

    public static UserEmergencyContact dto2model(UserEmergencyContactDto dto) {
        if (dto == null) {
            return null;
        }
        UserEmergencyContact model = new UserEmergencyContact();
        model.setId(dto.getId());
        model.setName(dto.getName());
        model.setRelationship(dto.getRelationship());
        model.setMobile(dto.getMobile());
        return model;
    }

    public static UserEmergencyContactDto model2Dto(UserEmergencyContact model) {
        if (model == null) {
            return null;
        }
        UserEmergencyContactDto dto = new UserEmergencyContactDto();
        dto.setId(model.getId());
        dto.setName(model.getName());
        dto.setMobile(model.getMobile());
        dto.setCreateTime(model.getCreateTime());
        dto.setRelationship(model.getRelationship());
        dto.setChecked(model.getChecked());
        return dto;
    }

    public static List<UserEmergencyContactDto> models2Dtos(List<UserEmergencyContact> models) {
        if (Objects.isNull(models)) {
            return null;
        }
        if (CollectionUtil.isEmpty(models)) {
            return null;
        }
        List<UserEmergencyContactDto> dtos = new ArrayList<>();
        models.forEach(model -> {
            dtos.add(model2Dto(model));
        });
        return dtos;
    }
}
