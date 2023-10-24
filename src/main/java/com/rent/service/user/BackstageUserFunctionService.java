        
package com.rent.service.user;

import com.rent.common.dto.user.BackstageFunctionDto;

import java.util.List;

/**
 * 后台用户可以用的功能点Service
 *
 * @author zhao
 * @Date 2020-06-24 11:47
 */
public interface BackstageUserFunctionService {


        /**
         * 获取后台用户拥有的功能
         * @param backstageUserId
         * @return
         */
        List<BackstageFunctionDto> getBackstageUserFunction(Long backstageUserId);


        /**
         * 删除用户拥有的部门权限
         * @param departmentId
         */
        void deleteFunctionByDepartmentId(Long departmentId);

        /**
         * 删除用户拥有的部门权限
         * @param departmentId
         */
        void deleteFunctionByDepartmentIdAndUserId(Long departmentId,Long backstageUserId);

        /**
         * 删除用户拥有的权限
         * @param backstageUserId
         */
        void deleteFunctionByUserId(Long backstageUserId);

        /**
         * 批量新增后台用户可以用的功能点
         * @param departmentUserIds
         * @param functionIds
         * @param sourceType
         * @param sourceValue
         */
        void addBackstageUserFunctionBatch(List<Long> departmentUserIds,  List<Long> functionIds,String sourceType,Long sourceValue);

        /**
         * 批量新增后台用户可以用的功能点
         * @param backstageUserId
         * @param functionIds
         * @param sourceType
         * @param sourceValue
         */
        void addBackstageUserFunctionBatch(Long backstageUserId, List<Long> functionIds,String sourceType,Long sourceValue);


}