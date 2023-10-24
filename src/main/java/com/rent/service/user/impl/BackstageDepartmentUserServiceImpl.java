        
package com.rent.service.user.impl;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.rent.common.converter.user.BackstageDepartmentUserConverter;
import com.rent.common.dto.user.BackstageDepartmentUserDto;
import com.rent.dao.user.BackstageDepartmentUserDao;
import com.rent.model.user.BackstageDepartmentUser;
import com.rent.service.user.BackstageDepartmentUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


/**
 * 后台部门用户映射表Service
 * @author zhao
 * @Date 2020-06-24 11:47
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class BackstageDepartmentUserServiceImpl implements BackstageDepartmentUserService {

    private final BackstageDepartmentUserDao backstageDepartmentUserDao;

    @Override
    public Long addBackstageDepartmentUser(BackstageDepartmentUserDto request) {
        BackstageDepartmentUser model = BackstageDepartmentUserConverter.dto2Model(request);
        if (backstageDepartmentUserDao.save(model)) {
            return model.getId();
        } else {
            throw new MybatisPlusException("保存数据失败");
        }
     }

    @Override
    public void deleteByBackstageUserId(Long backstageUserId) {
        backstageDepartmentUserDao.deleteByBackstageUserId(backstageUserId);
    }

    @Override
    public Integer getUserCount(Long departmentId) {
        return backstageDepartmentUserDao.getUserCount(departmentId);
    }

    @Override
    public List<Long> getDepartmentUserId(Long departmentId) {
        List<BackstageDepartmentUser> departmentUsers =  backstageDepartmentUserDao.getDepartmentUser(departmentId);
        return departmentUsers.stream().map(BackstageDepartmentUser::getBackstageUserId).collect(Collectors.toList());
    }

    @Override
    public Long getDepartmentIdByUserId(Long backstageUserId) {
        BackstageDepartmentUser backstageDepartmentUser = backstageDepartmentUserDao.getDepartmentIdByUserId(backstageUserId);
        return backstageDepartmentUser==null ? null :backstageDepartmentUser.getDepartmentId();
    }


}