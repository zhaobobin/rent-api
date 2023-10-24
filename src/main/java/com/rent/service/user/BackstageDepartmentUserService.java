        
package com.rent.service.user;

import com.rent.common.dto.user.BackstageDepartmentUserDto;

import java.util.List;

/**
 * 后台部门用户映射表Service
 *
 * @author zhao
 * @Date 2020-06-24 11:47
 */
public interface BackstageDepartmentUserService {

        /**
         * 新增后台部门用户映射表
         * @param request 条件
         * @return boolean
         */
        Long addBackstageDepartmentUser(BackstageDepartmentUserDto request);

        /**
         * 根据用户ID删除用户和部门之间的关系映射
         * @param backstageUserId 条件
         * @return boolean
         */
        void deleteByBackstageUserId(Long backstageUserId);

        /**
         * 获取部门有多少用户
         * @param departmentId
         * @return
         */
        Integer getUserCount(Long departmentId);

        /**
         * 获取部门用户ID
         * @param departmentId
         */
        List<Long> getDepartmentUserId(Long departmentId);

        /**
         * 获取用户所在部门ID
         * @param backstageUserId
         */
        Long getDepartmentIdByUserId(Long backstageUserId);


}