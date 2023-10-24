        
package com.rent.dao.user;


import com.rent.config.mybatis.IBaseDao;
import com.rent.model.user.BackstageDepartmentFunction;

import java.util.List;

/**
 * BackstageDepartmentFunctionDao
 *
 * @author zhao
 * @Date 2020-06-24 11:47
 */
public interface BackstageDepartmentFunctionDao extends IBaseDao<BackstageDepartmentFunction> {

    /**
     * 获取部门拥有的权限
     * @param departmentId
     * @return
     */
    List<Long> getDepartmentFunctionId(Long departmentId);

    /**
     * 删除部门所有的权限
     * @param departmentId
     */
    void deleteByDepartmentId(Long departmentId);
}
