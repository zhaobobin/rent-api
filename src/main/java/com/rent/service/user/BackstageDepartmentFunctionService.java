        
package com.rent.service.user;

import com.rent.common.dto.user.DepartmentFunctionReqDto;

import java.util.List;


/**
 * 后台部门可以使用的功能Service
 *
 * @author zhao
 * @Date 2020-06-24 11:47
 */
public interface BackstageDepartmentFunctionService {
        /**
         * 获取部门拥有的权限
         * @param departmentId
         * @return
         */
        List<Long> getDepartmentFunctionId(Long departmentId);

        /**
         * 更新部门权限
         * 1.删除原来的
         * 2.新增新的权限
         * @param request
         * @return
         */
        Boolean updateDepartmentFunction(DepartmentFunctionReqDto request);

        /**
         * shan
         * @param id
         */
        void deleteByDepartmentId(Long id);
}