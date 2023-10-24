package com.rent.dao.user.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rent.common.converter.user.UserThirdInfoConverter;
import com.rent.common.dto.user.UserThirdInfoDto;
import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.user.UserThirdInfoDao;
import com.rent.mapper.user.UserThirdInfoMapper;
import com.rent.model.user.UserThirdInfo;
import com.rent.util.StringUtil;
import org.springframework.stereotype.Repository;

/**
 * UserThirdInfoDao
 *
 * @author zhao
 * @Date 2020-06-28 14:24
 */
@Repository
public class UserThirdInfoDaoImpl extends AbstractBaseDaoImpl<UserThirdInfo, UserThirdInfoMapper> implements UserThirdInfoDao{


    @Override
    public UserThirdInfo getByUidAndChannelType(String uid, String channelType) {
        return getOne(new QueryWrapper<UserThirdInfo>().eq("uid",uid).eq("channel",channelType));
    }

    @Override
    public UserThirdInfo getByUidAndChannelTypeV1(String uid, String channelType) {
        return getOne(new QueryWrapper<UserThirdInfo>().eq("uid",uid).eq("channel",channelType).last("limit 1"));
    }

    @Override
    public UserThirdInfo getByThirdId(String thirdId) {
        return getOne(new QueryWrapper<UserThirdInfo>().eq("third_id",thirdId));
    }

    @Override
    public UserThirdInfo getByTelephoneAndChannelType(String telephone, String channelType) {
        return getOne(new QueryWrapper<UserThirdInfo>().eq("telephone",telephone).eq("channel",channelType));
    }

    @Override
    public void replaceUid(String origin, String newUid) {
        if(StringUtil.isNotEmpty(origin) && StringUtil.isNotEmpty(newUid)){
            baseMapper.replaceUid(origin,newUid);
        }
    }

    @Override
    public UserThirdInfoDto getByUid(String uid) {
        UserThirdInfo userThirdInfo =  getOne(new QueryWrapper<UserThirdInfo>().eq("uid",uid));
        UserThirdInfoDto dto = UserThirdInfoConverter.model2Dto(userThirdInfo);
        return dto;
    }


}
