package com.rent.dao.user.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rent.common.converter.user.UserCertificationConverter;
import com.rent.common.dto.user.UidAndPhone;
import com.rent.common.dto.user.UserCertificationDto;
import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.user.UserCertificationDao;
import com.rent.mapper.user.UserCertificationMapper;
import com.rent.model.user.UserCertification;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * UserCertificationDao
 *
 * @author zhao
 * @Date 2020-06-18 15:06
 */
@Repository
public class UserCertificationDaoImpl extends AbstractBaseDaoImpl<UserCertification, UserCertificationMapper>
    implements UserCertificationDao {

    @Override
    public Map<String, UserCertificationDto> queryUserCertificationList(List<String> uidList) {
        List<UserCertification> modelList = list(new QueryWrapper<UserCertification>().in("uid", uidList));
        List<UserCertificationDto> dtoList = UserCertificationConverter.modelList2DtoList(modelList);
        Map<String, UserCertificationDto> map = dtoList.stream()
            .collect(Collectors.toMap(UserCertificationDto::getUid, Function.identity(), (e1, e2) -> e1));
        return map;
    }

    @Override
    public UserCertification getByUid(String uid) {
        return getOne(new QueryWrapper<UserCertification>().eq("uid", uid));
    }

    @Override
    public void initRecord(String uid, String idCard, String userName, String telephone, String idCardFrontUrl,
                           String idCardBackUrl, Date limitDate,Date startDate,String address,String sex,String issueOrg,String nation) {
        Date now = new Date();
        save(UserCertification.builder()
                .createTime(now)
                .updateTime(now)
                .uid(uid)
                .idCard(idCard)
                .userName(userName)
                .telephone(telephone)
                .idCardFrontUrl(idCardFrontUrl)
                .idCardBackUrl(idCardBackUrl)
                .limitDate(limitDate)
                .startDate(startDate)
                .address(address)
                .sex(sex)
                .issueOrg(issueOrg)
                .nation(nation)
                .build());
    }

    @Override
    public List<UidAndPhone> getUidAndPhoneSet(List<String> phones) {
        List<UserCertification> modelList = list(new QueryWrapper<UserCertification>().select("telephone", "uid")
            .in("telephone", phones));
        if (CollectionUtil.isNotEmpty(modelList)) {
            List<UidAndPhone> uidAndPhones = new ArrayList<>(modelList.size());
            for (UserCertification userCertification : modelList) {
                UidAndPhone uidAndPhone = new UidAndPhone();
                uidAndPhone.setPhone(userCertification.getTelephone());
                uidAndPhone.setUid(userCertification.getUid());
                uidAndPhones.add(uidAndPhone);
            }
            return uidAndPhones;
        }
        return Collections.emptyList();
    }

    @Override
    public List<String> getUidByIdCard(String idCard) {
        List<UserCertification> modelList = list(new QueryWrapper<UserCertification>().select("uid")
                .eq("id_card", idCard));
        return modelList.stream()
                .map(UserCertification::getUid)
                .collect(Collectors.toList());
    }

}
