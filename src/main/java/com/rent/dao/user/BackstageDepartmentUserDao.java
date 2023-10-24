        
package com.rent.dao.user;


import com.rent.config.mybatis.IBaseDao;
import com.rent.model.user.BackstageDepartmentUser;

import java.util.List;

/**
 * BackstageDepartmentUserDao
 *
 * @author zhao
 * @Date 2020-06-24 11:47
 */
public interface BackstageDepartmentUserDao extends IBaseDao<BackstageDepartmentUser> {

    /**
     * 获取部门有多少用户
     * @param departmentId
     * @return
     */
    Integer getUserCount(Long departmentId);

    /**
     * 获取部门用户
     * @param departmentId
     * @return
     */
    List<BackstageDepartmentUser> getDepartmentUser(Long departmentId);

    /**
     * 获取用户部门
     * @param backstageUserId
     * @return
     */
    BackstageDepartmentUser getDepartmentIdByUserId(Long backstageUserId);

    /**
     * 根据用户ID删除用户和部门之间的关系映射
     * @param backstageUserId 条件
     * @return boolean
     */
    void deleteByBackstageUserId(Long backstageUserId);
}
