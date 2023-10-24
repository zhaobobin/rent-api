    
package com.rent.dao.user.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rent.common.converter.user.UserPointConverter;
import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.user.UserPointDao;
import com.rent.mapper.user.UserPointMapper;
import com.rent.model.order.UserPointDto;
import com.rent.model.user.UserPoint;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * UserPointDao
 *
 * @author youruo
 * @Date 2020-09-25 14:38
 */
@Repository
public class UserPointDaoImpl extends AbstractBaseDaoImpl<UserPoint, UserPointMapper> implements UserPointDao {


    @Override
    public List<UserPointDto> getAllData() {
        List<UserPoint> list = list(new QueryWrapper<>());
        return UserPointConverter.modelList2DtoList(list);
    }

    @Override
    public List<UserPointDto> getByUid(String uid) {
        List<UserPoint> list = list(new QueryWrapper<UserPoint>().eq("uid",uid));
        return UserPointConverter.modelList2DtoList(list);
    }
}
