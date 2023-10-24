package com.rent.dao.user.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.user.BackstageDepartmentUserDao;
import com.rent.mapper.user.BackstageDepartmentUserMapper;
import com.rent.model.user.BackstageDepartmentUser;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * BackstageDepartmentUserDao
 *
 * @author zhao
 * @Date 2020-06-24 11:47
 */
@Repository
public class BackstageDepartmentUserDaoImpl extends AbstractBaseDaoImpl<BackstageDepartmentUser, BackstageDepartmentUserMapper> implements BackstageDepartmentUserDao {


    @Override
    public Integer getUserCount(Long departmentId) {
        return count(new QueryWrapper<BackstageDepartmentUser>().eq("department_id",departmentId));
    }

    @Override
    public List<BackstageDepartmentUser> getDepartmentUser(Long departmentId) {
        return list(new QueryWrapper<BackstageDepartmentUser>().eq("department_id",departmentId));
    }

    @Override
    public BackstageDepartmentUser getDepartmentIdByUserId(Long backstageUserId) {
        return getOne(new QueryWrapper<BackstageDepartmentUser>().eq("backstage_user_id",backstageUserId));
    }

    @Override
    public void deleteByBackstageUserId(Long backstageUserId) {
        remove(new QueryWrapper<BackstageDepartmentUser>().eq("backstage_user_id",backstageUserId));
    }
}
