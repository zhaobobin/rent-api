        
package com.rent.dao.user;


import com.rent.config.mybatis.IBaseDao;
import com.rent.model.user.BackstageUserFunction;

import java.util.List;

/**
 * BackstageUserFunctionDao
 *
 * @author zhao
 * @Date 2020-06-24 11:47
 */
public interface BackstageUserFunctionDao extends IBaseDao<BackstageUserFunction> {

    /**
     * 获取后台用户拥有的功能Id
     * @param backstageUserId
     * @return
     */
    List<Long> getBackstageUserFunctionId(Long backstageUserId);

    /**
     * 删除用户拥有的部门权限
     * @param departmentId
     */
    void deleteFunctionByDepartmentId(Long departmentId);

    /**
     * 删除用户拥有的权限
     * @param backstageUserId
     */
    void deleteFunctionByUserId(Long backstageUserId);

    /**
     * 判断用户是否拥有某个功能
     * @param userId
     * @param functionId
     * @return true有    false没有
     */
    Boolean checkHasFunction(Long userId, Long functionId);

    void deleteFunctionByDepartmentIdAndUserId(Long departmentId, Long backstageUserId);
}
