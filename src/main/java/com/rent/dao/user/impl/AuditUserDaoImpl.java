package com.rent.dao.user.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rent.common.enums.user.EnumBackstageUserStatus;
import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.user.AuditUserDao;
import com.rent.mapper.user.AuditUserMapper;
import com.rent.model.user.AuditUser;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class AuditUserDaoImpl extends AbstractBaseDaoImpl<AuditUser, AuditUserMapper> implements AuditUserDao {


    @Override
    public AuditUser getByBackstageUserId(Long backstageUserId) {
        return getOne(new QueryWrapper<AuditUser>().eq("backstage_user_id", backstageUserId));
    }

    @Override
    public List<AuditUser> listShopAuditUser(String shopId, EnumBackstageUserStatus status) {
        return list(new QueryWrapper<AuditUser>().eq("shop_id", shopId).eq("status", status.getCode()));
    }

}
