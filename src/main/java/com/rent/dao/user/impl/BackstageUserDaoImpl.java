package com.rent.dao.user.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rent.common.constant.BackstageUserConstant;
import com.rent.common.dto.user.BackstageUserReqDto;
import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.user.BackstageUserDao;
import com.rent.mapper.user.BackstageUserMapper;
import com.rent.model.user.BackstageUser;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * BackstageUserDao
 *
 * @author zhao
 * @Date 2020-06-24 11:46
 */
@Repository
public class BackstageUserDaoImpl extends AbstractBaseDaoImpl<BackstageUser, BackstageUserMapper> implements BackstageUserDao {


    @Override
    public Boolean deleteBackstageUser(BackstageUserReqDto request) {
        BackstageUser backstageUser = new BackstageUser().setDeleteTime(new Date());
        return update(backstageUser,new QueryWrapper<BackstageUser>().eq("id",request.getId()).eq("shop_id",request.getShopId()));
    }

    @Override
    public BackstageUser getRegisterByShopId(String shopId) {
        return getOne(new QueryWrapper<BackstageUser>()
                .eq("shop_id",shopId)
                .eq("create_user_name", BackstageUserConstant.BACKSTAGE_USER_CREATE_REGISTER));
    }
}
