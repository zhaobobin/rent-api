
package com.rent.service.user.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.converter.user.BackstageDepartmentConverter;
import com.rent.common.dto.backstage.resp.AuthPageResp;
import com.rent.common.dto.user.BackstageDepartmentDto;
import com.rent.common.dto.user.BackstageDepartmentReqDto;
import com.rent.common.dto.user.DepartmentFunctionReqDto;
import com.rent.common.enums.user.EnumBackstageUserPlatform;
import com.rent.dao.user.BackstageDepartmentDao;
import com.rent.exception.HzsxBizException;
import com.rent.model.user.BackstageDepartment;
import com.rent.service.user.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 后台部门Service
 *
 * @author zhao
 * @Date 2020-06-24 11:47
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class BackstageDepartmentServiceImpl implements BackstageDepartmentService {

    private final BackstageDepartmentDao backstageDepartmentDao;
    private final BackstageDepartmentUserService backstageDepartmentUserService;
    private final BackstageFunctionService backstageFunctionService;
    private final BackstageDepartmentFunctionService backstageDepartmentFunctionService;
    private final BackstageUserFunctionService backstageUserFunctionService;

    @Override
    public Long addBackstageDepartment(BackstageDepartmentDto request) {
        BackstageDepartment model = BackstageDepartmentConverter.dto2Model(request);
        if (backstageDepartmentDao.save(model)) {
            return model.getId();
        } else {
            throw new MybatisPlusException("保存数据失败");
        }
    }

    @Override
    public Boolean modifyBackstageDepartment(BackstageDepartmentDto request) {
        return backstageDepartmentDao.modifyBackstageDepartment(request);
    }

    @Override
    public BackstageDepartmentDto queryBackstageDepartmentDetail(BackstageDepartmentReqDto request) {
        BackstageDepartment backstageDepartment = backstageDepartmentDao.getOne(new QueryWrapper<>(BackstageDepartmentConverter.reqDto2Model(request)));
        return BackstageDepartmentConverter.model2Dto(backstageDepartment);
    }

    @Override
    public Page<BackstageDepartmentDto> queryBackstageDepartmentPage(BackstageDepartmentReqDto request) {
        Page<BackstageDepartment> page = backstageDepartmentDao.page(new Page<>(request.getPageNumber(), request.getPageSize()),
                new QueryWrapper<>(BackstageDepartmentConverter.reqDto2Model(request)));

        List<BackstageDepartment> departments = page.getRecords();
        List<BackstageDepartmentDto> dtos = new ArrayList<>(departments.size());
        page.getRecords().stream().forEach(backstageDepartment -> {
            BackstageDepartmentDto dto = BackstageDepartmentConverter.model2Dto(backstageDepartment);
            dto.setUserCount(backstageDepartmentUserService.getUserCount(backstageDepartment.getId()));
            dtos.add(dto);
        });
        return new Page<BackstageDepartmentDto>(page.getCurrent(), page.getSize(), page.getTotal()).setRecords(dtos);
    }

    @Override
    public List<BackstageDepartmentDto> queryBackstageDepartment(BackstageDepartmentReqDto request) {
        List<BackstageDepartment> departments = backstageDepartmentDao.list(new QueryWrapper<>(BackstageDepartmentConverter.reqDto2Model(request)).select("id", "name"));
        return BackstageDepartmentConverter.modelList2DtoList(departments);
    }

    @Override
    public List<AuthPageResp> authPage(BackstageDepartmentReqDto request) {
        List<Long> chosenFunction = backstageDepartmentFunctionService.getDepartmentFunctionId(request.getId());
        List<AuthPageResp> backstageFunctionChooseDtoList = backstageFunctionService.getFunctionList(request.getPlatform(), chosenFunction);
        return backstageFunctionChooseDtoList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateDepartmentFunction(DepartmentFunctionReqDto request) {
        if (!canEdit(request.getDepartmentId())) {
            throw new HzsxBizException("-1", "无权修改该部门权限");
        }
        //更新部门权限列表
        backstageDepartmentFunctionService.updateDepartmentFunction(request);
        //删除用户拥有的部门权限
        backstageUserFunctionService.deleteFunctionByDepartmentId(request.getDepartmentId());
        //给该部门的用户添加权限
        List<Long> departmentUserIds = backstageDepartmentUserService.getDepartmentUserId(request.getDepartmentId());
        backstageUserFunctionService.addBackstageUserFunctionBatch(departmentUserIds, request.getFunctionIds(), "DEPARTMENT", request.getDepartmentId());
        return Boolean.TRUE;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteBackstageDepartment(BackstageDepartmentDto request) {
        //删除部门
        if (!backstageDepartmentDao.deleteBackstageDepartment(request)) {
            return Boolean.FALSE;
        }
        //删除部门拥有的权限
        backstageDepartmentFunctionService.deleteByDepartmentId(request.getId());
        return Boolean.TRUE;
    }

    @Override
    public String getNameById(Long id) {
        BackstageDepartment backstageDepartment = backstageDepartmentDao.getById(id);
        return backstageDepartment == null ? null : backstageDepartment.getName();
    }

    @Override
    public Boolean canEdit(Long id) {
        BackstageDepartment backstageDepartment = backstageDepartmentDao.getById(id);
        return  true;
//        return backstageDepartment.getPlatform() != null && !backstageDepartment.getPlatform().getCode().equals(EnumBackstageUserPlatform.SYS.getCode());
    }

}