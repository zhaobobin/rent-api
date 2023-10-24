        
package com.rent.service.user;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.dto.backstage.resp.AuthPageResp;
import com.rent.common.dto.user.BackstageDepartmentDto;
import com.rent.common.dto.user.BackstageDepartmentReqDto;
import com.rent.common.dto.user.DepartmentFunctionReqDto;

import java.util.List;

/**
 * 后台部门Service
 * @author zhao
 * @Date 2020-06-24 11:47
 */
public interface BackstageDepartmentService {

        /**
         * 新增后台部门
         * @param request 条件
         * @return boolean
         */
        Long addBackstageDepartment(BackstageDepartmentDto request);

        /**
         * 根据 ID 修改后台部门
         * @param request 条件
         * @return String
         */
        Boolean modifyBackstageDepartment(BackstageDepartmentDto request);

        /**
         * 根据条件查询一条记录
         * @param request 实体对象
         * @return BackstageDepartment
         */
        BackstageDepartmentDto queryBackstageDepartmentDetail(BackstageDepartmentReqDto request);

        /**
         * 根据条件列表
         * @param request 实体对象
         * @return BackstageDepartment
         */
        Page<BackstageDepartmentDto> queryBackstageDepartmentPage(BackstageDepartmentReqDto request);

        /**
         * 根据条件列表
         * @param request 实体对象
         * @return BackstageDepartment
         */
        List<BackstageDepartmentDto> queryBackstageDepartment(BackstageDepartmentReqDto request);

        /**
         * 权限设置页面
         * @param request
         * @return
         */
        List<AuthPageResp> authPage(BackstageDepartmentReqDto request);

        /**
         * 更新权限
         * @param request
         * @return
         */
        Boolean updateDepartmentFunction(DepartmentFunctionReqDto request);

        /**
         * 删除部门
         * @param request
         * @return
         */
        Boolean deleteBackstageDepartment(BackstageDepartmentDto request);

        /**
         * 根据ID获取部门名称
         * @param id
         * @return
         */
        String getNameById(Long id);

        /**
         * 判断部门是否能够修改
         * @param id
         * @return
         */
        Boolean canEdit(Long id);
}