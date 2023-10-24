package com.rent.dao.user.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rent.common.dto.user.BackstageDepartmentDto;
import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.user.BackstageDepartmentDao;
import com.rent.mapper.user.BackstageDepartmentMapper;
import com.rent.model.user.BackstageDepartment;
import org.springframework.stereotype.Repository;

/**
 * BackstageDepartmentDao
 *
 * @author zhao
 * @Date 2020-06-24 11:47
 */
@Repository
public class BackstageDepartmentDaoImpl extends AbstractBaseDaoImpl<BackstageDepartment, BackstageDepartmentMapper> implements BackstageDepartmentDao {


    @Override
    public Boolean modifyBackstageDepartment(BackstageDepartmentDto request) {
        BackstageDepartment department = new BackstageDepartment();
        department.setUpdateTime(request.getUpdateTime());
        department.setName(request.getName());
        department.setDescription(request.getDescription());
        return update(department,new QueryWrapper<BackstageDepartment>().eq("id",request.getId()).eq("shop_id",request.getShopId()));
    }

    @Override
    public boolean deleteBackstageDepartment(BackstageDepartmentDto request) {
        return remove(new QueryWrapper<BackstageDepartment>().eq("id",request.getId()).eq("shop_id",request.getShopId()) );
    }
}
