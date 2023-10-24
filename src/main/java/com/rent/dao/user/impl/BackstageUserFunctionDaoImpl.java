package com.rent.dao.user.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.user.BackstageUserFunctionDao;
import com.rent.mapper.user.BackstageUserFunctionMapper;
import com.rent.model.user.BackstageUserFunction;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * BackstageUserFunctionDao
 *
 * @author zhao
 * @Date 2020-06-24 11:47
 */
@Repository
public class BackstageUserFunctionDaoImpl extends AbstractBaseDaoImpl<BackstageUserFunction, BackstageUserFunctionMapper> implements BackstageUserFunctionDao {


    @Override
    public List<Long> getBackstageUserFunctionId(Long backstageUserId) {
        List<BackstageUserFunction> userFunctionList = list(new QueryWrapper<BackstageUserFunction>().select("function_id").eq("backstage_user_id", backstageUserId));
        return userFunctionList.stream().map(BackstageUserFunction::getFunctionId).collect(Collectors.toList());
    }

    @Override
    public void deleteFunctionByDepartmentId(Long departmentId) {
        remove(new QueryWrapper<BackstageUserFunction>().eq("source_value", departmentId).eq("source_type", "DEPARTMENT"));
    }

    @Override
    public void deleteFunctionByDepartmentIdAndUserId(Long departmentId, Long backstageUserId) {
        remove(new QueryWrapper<BackstageUserFunction>().eq("source_value", departmentId).eq("source_type", "DEPARTMENT").eq("backstage_user_id", backstageUserId));
    }

    @Override
    public void deleteFunctionByUserId(Long backstageUserId) {
        remove(new QueryWrapper<BackstageUserFunction>().eq("backstage_user_id", backstageUserId));
    }

    @Override
    public Boolean checkHasFunction(Long userId, Long functionId) {
        BackstageUserFunction backstageUserFunction = getOne(new QueryWrapper<BackstageUserFunction>().select("id").eq("backstage_user_id", userId).eq("function_id", functionId));
        return backstageUserFunction != null;

    }


}
