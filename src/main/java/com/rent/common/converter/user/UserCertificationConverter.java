package com.rent.common.converter.user;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.rent.common.dto.user.UserCertificationDto;
import com.rent.model.user.UserCertification;
import com.rent.util.DateUtil;

import java.util.Collections;
import java.util.List;

/**
 * 用户认证表Service
 *
 * @author zhao
 * @Date 2020-06-18 15:06
 */
public class UserCertificationConverter {

    /**
     * model转dto
     *
     * @param model
     * @return
     */
    public static UserCertificationDto model2Dto(UserCertification model) {
        if (model == null) {
            return null;
        }
        UserCertificationDto dto = new UserCertificationDto();
        dto.setId(model.getId());
        dto.setUid(model.getUid());
        dto.setUserName(model.getUserName());
        dto.setIdCard(model.getIdCard());
        dto.setTelephone(model.getTelephone());
        dto.setIdCardFrontUrl(model.getIdCardFrontUrl());
        dto.setIdCardBackUrl(model.getIdCardBackUrl());
        dto.setLimitDate( DateUtil.date2String(model.getLimitDate(), DateUtil.DATE_FORMAT_2));
        dto.setCreateTime(model.getCreateTime());
        dto.setStartDate(DateUtil.date2String(model.getStartDate(), DateUtil.DATE_FORMAT_2));
        dto.setAddress(model.getAddress());
        dto.setSex(model.getSex());
        dto.setNation(model.getNation());
        dto.setIssueOrg(model.getIssueOrg());
        dto.setUpdateTime(model.getUpdateTime());
        return dto;
    }

    /**
     * modelList转dtoList
     *
     * @param modelList
     * @return
     */
    public static List<UserCertificationDto> modelList2DtoList(List<UserCertification> modelList) {
        if (CollectionUtil.isEmpty(modelList)) {
            return Collections.emptyList();
        }
        return Lists.newArrayList(Iterators.transform(modelList.iterator(), UserCertificationConverter::model2Dto));
    }
}