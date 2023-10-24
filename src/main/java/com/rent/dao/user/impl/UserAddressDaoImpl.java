package com.rent.dao.user.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.user.UserAddressDao;
import com.rent.mapper.user.UserAddressMapper;
import com.rent.model.user.UserAddress;
import com.rent.util.StringUtil;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * UserAddressDao
 *
 * @author zhao
 * @Date 2020-06-18 13:54
 */
@Repository
public class UserAddressDaoImpl extends AbstractBaseDaoImpl<UserAddress, UserAddressMapper> implements UserAddressDao {

    @Override
    public List<UserAddress> getUserAddress(String uid) {
        return list(new QueryWrapper<UserAddress>().eq("uid", uid)
                .isNull("delete_time"));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void replaceUid(String origin, String newUid) {
        if (StringUtil.isNotEmpty(origin) && StringUtil.isNotEmpty(newUid)) {
            baseMapper.replaceUid(origin, newUid);
        }
        baseMapper.updateUserAddressNoneDefault(newUid);
    }

    @Override
    public void updateUserAddressNoneDefault(String uid) {
        baseMapper.updateUserAddressNoneDefault(uid);
    }
}
