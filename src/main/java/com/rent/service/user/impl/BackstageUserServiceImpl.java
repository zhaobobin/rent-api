
package com.rent.service.user.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.constant.BackstageUserConstant;
import com.rent.common.converter.user.BackstageUserConverter;
import com.rent.common.dto.backstage.request.PageBackstageUserReq;
import com.rent.common.dto.backstage.resp.AuthPageResp;
import com.rent.common.dto.backstage.resp.PageBackstageUserResp;
import com.rent.common.dto.user.*;
import com.rent.dao.user.BackstageUserDao;
import com.rent.exception.HzsxBizException;
import com.rent.model.user.BackstageUser;
import com.rent.service.user.*;
import com.rent.util.LoginUserUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 后台用户Service
 *
 * @author zhao
 * @Date 2020-06-24 11:46
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class BackstageUserServiceImpl implements BackstageUserService {

    private final BackstageUserDao backstageUserDao;
    private final BackstageUserFunctionService backstageUserFunctionService;
    private final BackstageDepartmentUserService backstageDepartmentUserService;
    private final BackstageDepartmentService backstageDepartmentService;
    private final BackstageDepartmentFunctionService backstageDepartmentFunctionService;
    private final BackstageFunctionService backstageFunctionService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long addBackstageUser(BackstageUserDto request) {
        BackstageUser model = BackstageUserConverter.dto2Model(request);
        Date now = new Date();
        if (backstageUserDao.save(model)) {
            //添加用户和部门关系映射
            BackstageDepartmentUserDto backstageDepartmentUserDto = new BackstageDepartmentUserDto().setBackstageUserId(model.getId()).setDepartmentId(request.getDepartmentId()).setCreateTime(now);
            backstageDepartmentUserService.addBackstageDepartmentUser(backstageDepartmentUserDto);
            //添加用户权限
            List<Long> functionIds = backstageDepartmentFunctionService.getDepartmentFunctionId(request.getDepartmentId());
            backstageUserFunctionService.addBackstageUserFunctionBatch(model.getId(), functionIds, "DEPARTMENT", request.getDepartmentId());
            return model.getId();
        }
        return model.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean modifyBackstageUser(BackstageUserDto request) {
        BackstageUser model = BackstageUserConverter.dto2Model(request);
        if (backstageUserDao.updateById(model)) {
            if (request.getDepartmentId() != null) {
                updateUserDepartment(model, request.getDepartmentId());
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    /**
     * 修改用户所属部门
     *
     * @param user
     * @param newDepartmentId
     */
    private void updateUserDepartment(BackstageUser user, Long newDepartmentId) {
        //查询原来的部门ID
        Long oldDepartmentId = backstageDepartmentUserService.getDepartmentIdByUserId(user.getId());
        //如果原来的部门ID和修改后的部门ID相同，则直接返回
        if (newDepartmentId != null && oldDepartmentId.equals(newDepartmentId)) {
            return;
        }

        //删除用户和旧部门关系映射
        backstageDepartmentUserService.deleteByBackstageUserId(user.getId());
        //删除原来部门赋予用户的权限
        backstageUserFunctionService.deleteFunctionByDepartmentIdAndUserId(oldDepartmentId, user.getId());

        //新增用户和新部门关系映射
        Date now = new Date();
        BackstageDepartmentUserDto backstageDepartmentUserDto = new BackstageDepartmentUserDto()
                .setBackstageUserId(user.getId())
                .setDepartmentId(newDepartmentId).setCreateTime(now);
        backstageDepartmentUserService.addBackstageDepartmentUser(backstageDepartmentUserDto);
        //添加用户新的部门的权限
        List<Long> functionIds = backstageDepartmentFunctionService.getDepartmentFunctionId(newDepartmentId);
        backstageUserFunctionService.addBackstageUserFunctionBatch(user.getId(), functionIds, "DEPARTMENT", newDepartmentId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteBackstageUser(BackstageUserReqDto request) {
        //删除用户
        backstageUserDao.deleteBackstageUser(request);
        //删除用户权限列表
        backstageUserFunctionService.deleteFunctionByUserId(request.getId());
        //删除用户部门关系映射
        backstageDepartmentUserService.deleteByBackstageUserId(request.getId());
        return Boolean.TRUE;
    }

    @Override
    public BackstageUserDto queryBackstageUserDetail(BackstageUserReqDto request) {
        BackstageUser backstageUser = backstageUserDao.getOne(new QueryWrapper<>(BackstageUserConverter.reqDto2Model(request)).isNull("delete_time"));
        if (backstageUser == null) {
            return null;
        }
        BackstageUserDto dto = BackstageUserConverter.model2Dto(backstageUser);
        dto.setDepartmentId(backstageDepartmentUserService.getDepartmentIdByUserId(dto.getId()));
        dto.setDepartmentName(backstageDepartmentService.getNameById(dto.getDepartmentId()));
        return dto;
    }

    @Override
    public BackstageUserDto queryBackstageUserByPhone(String phone) {
        BackstageUser backstageUser = backstageUserDao.getOne(new QueryWrapper<BackstageUser>().eq("mobile", phone));
        if (backstageUser == null) {
            return null;
        }
        BackstageUserDto dto = BackstageUserConverter.model2Dto(backstageUser);
        dto.setDepartmentId(backstageDepartmentUserService.getDepartmentIdByUserId(dto.getId()));
        dto.setDepartmentName(backstageDepartmentService.getNameById(dto.getDepartmentId()));
        return dto;
    }

    @Override
    public Page<PageBackstageUserResp> queryBackstageUserPage(PageBackstageUserReq request) {
        List<Long> backstageUserIds = null;
        if (request.getDepartmentId() != null) {
            backstageUserIds = backstageDepartmentUserService.getDepartmentUserId(request.getDepartmentId());
            if (CollectionUtils.isEmpty(backstageUserIds)) {
                return new Page<PageBackstageUserResp>(1, 10, 0).setRecords(Collections.emptyList());
            }
        }
        Page<BackstageUser> page = backstageUserDao.page(
                new Page<>(request.getPageNumber(), request.getPageSize()),
                new QueryWrapper<BackstageUser>()
                        .like(!StringUtils.isEmpty(request.getName()), "name", request.getName())
                        .in(CollectionUtils.isNotEmpty(backstageUserIds), "id", backstageUserIds)
                        .eq("shop_id", LoginUserUtil.getLoginUser().getShopId())
                        .isNull("delete_time")
                        .orderByDesc("id")
        );
        List<PageBackstageUserResp> dtos = new ArrayList<>(page.getRecords().size());
        for (BackstageUser backstageUser : page.getRecords()) {
            PageBackstageUserResp dto = BackstageUserConverter.model2PageDto(backstageUser);
            dto.setDepartmentId(backstageDepartmentUserService.getDepartmentIdByUserId(dto.getId()));
            dto.setDepartmentName(backstageDepartmentService.getNameById(dto.getDepartmentId()));
            dtos.add(dto);
        }
        return new Page<PageBackstageUserResp>(page.getCurrent(), page.getSize(), page.getTotal()).setRecords(dtos);
    }


    @Override
    public List<AuthPageResp> authPage(BackstageUserReqDto request) {
        List<Long> chosenFunction = backstageUserFunctionService.getBackstageUserFunction(request.getId()).stream().map(BackstageFunctionDto::getId).collect(Collectors.toList());
        List<AuthPageResp> backstageFunctionChooseDtoList = backstageFunctionService.getFunctionList(request.getType(), chosenFunction);
        return backstageFunctionChooseDtoList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateBackstageUserAuth(BackstageUserFunctionReqDto request) {
        Long departmentId = backstageDepartmentUserService.getDepartmentIdByUserId(request.getBackstageUserId());
        if (backstageDepartmentService.canEdit(departmentId)) {
            backstageUserFunctionService.deleteFunctionByUserId(request.getBackstageUserId());
            backstageUserFunctionService.addBackstageUserFunctionBatch(request.getBackstageUserId(), request.getFunctionIds(), "ADD", null);
            return Boolean.TRUE;
        } else {
            throw new HzsxBizException("-1", "无权修改改用户权限");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateDepartmentAfterShopExaminePass(String shopId) {
        BackstageUser backstageUser = backstageUserDao.getRegisterByShopId(shopId);
        updateUserDepartment(backstageUser, BackstageUserConstant.BACKSTAGE_USER_SHOP_MASTER_DEPARTMENT_ID);
        return Boolean.TRUE;
    }
}