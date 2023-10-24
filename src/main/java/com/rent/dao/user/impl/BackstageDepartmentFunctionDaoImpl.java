package com.rent.dao.user.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.user.BackstageDepartmentFunctionDao;
import com.rent.mapper.user.BackstageDepartmentFunctionMapper;
import com.rent.model.user.BackstageDepartmentFunction;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * BackstageDepartmentFunctionDao
 *
 * @author zhao
 * @Date 2020-06-24 11:47
 */
@Repository
public class BackstageDepartmentFunctionDaoImpl extends AbstractBaseDaoImpl<BackstageDepartmentFunction, BackstageDepartmentFunctionMapper> implements BackstageDepartmentFunctionDao {


    @Override
    public List<Long> getDepartmentFunctionId(Long departmentId) {
        List<BackstageDepartmentFunction> list = list(new QueryWrapper<BackstageDepartmentFunction>().eq("department_id",departmentId));
        return list.stream().map(BackstageDepartmentFunction::getFunctionId).collect(Collectors.toList());
    }

    @Override
    public void deleteByDepartmentId(Long departmentId) {
        remove(new QueryWrapper<BackstageDepartmentFunction>().eq("department_id",departmentId));
    }
}
