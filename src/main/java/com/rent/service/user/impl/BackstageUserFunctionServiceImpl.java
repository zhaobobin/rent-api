
package com.rent.service.user.impl;


import com.rent.common.cache.backstage.BackstageUserCache;
import com.rent.common.dto.user.BackstageFunctionDto;
import com.rent.dao.user.BackstageFunctionDao;
import com.rent.dao.user.BackstageUserFunctionDao;
import com.rent.model.user.BackstageUserFunction;
import com.rent.service.user.BackstageUserFunctionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 后台用户可以用的功能点Service
 *
 * @author zhao
 * @Date 2020-06-24 11:47
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class BackstageUserFunctionServiceImpl implements BackstageUserFunctionService {

    private final BackstageUserFunctionDao backstageUserFunctionDao;
    private final BackstageFunctionDao backstageFunctionDao;

    @Override
    public List<BackstageFunctionDto> getBackstageUserFunction(Long backstageUserId) {
        List<Long> functionIds = backstageUserFunctionDao.getBackstageUserFunctionId(backstageUserId);
        List<BackstageFunctionDto> functionDtoList = backstageFunctionDao.getDtoByIds(functionIds);
        return functionDtoList;
    }


    @Override
    public void deleteFunctionByDepartmentId(Long departmentId) {
        backstageUserFunctionDao.deleteFunctionByDepartmentId(departmentId);
    }

    @Override
    public void deleteFunctionByDepartmentIdAndUserId(Long departmentId, Long backstageUserId) {
        backstageUserFunctionDao.deleteFunctionByDepartmentIdAndUserId(departmentId, backstageUserId);
    }

    @Override
    public void deleteFunctionByUserId(Long backstageUserId) {
        backstageUserFunctionDao.deleteFunctionByUserId(backstageUserId);
    }

    @Override
    public void addBackstageUserFunctionBatch(List<Long> departmentUserIds, List<Long> functionIds, String sourceType, Long sourceValue) {
        if (CollectionUtils.isEmpty(departmentUserIds) || CollectionUtils.isEmpty(functionIds)) {
            return;
        }
        Date now = new Date();
        List<BackstageUserFunction> models = new ArrayList<>(departmentUserIds.size() * functionIds.size());

        for (Long userId : departmentUserIds) {
            List<Long> hasFunctions = backstageUserFunctionDao.getBackstageUserFunctionId(userId);

            for (Long functionId : functionIds) {
                // TODO 耗时操作，是否可以逻辑上优化
                if (hasFunctions.contains(functionId)) {
                    continue;
                }
//                if (backstageUserFunctionDao.checkHasFunction(userId, functionId)) {
//                    continue;
//                }
                BackstageUserFunction model = new BackstageUserFunction();
                model.setBackstageUserId(userId).setFunctionId(functionId).setSourceType(sourceType).setSourceValue(sourceValue).setCreateTime(now);
                models.add(model);
            }
        }
        backstageUserFunctionDao.saveBatch(models);
    }

    @Override
    public void addBackstageUserFunctionBatch(Long backstageUserId, List<Long> functionIds, String sourceType, Long sourceValue) {
        if (CollectionUtils.isEmpty(functionIds)) {
            return;
        }
        Date now = new Date();
        List<BackstageUserFunction> models = new ArrayList<>(functionIds.size());
        for (Long functionId : functionIds) {
            BackstageUserFunction model = new BackstageUserFunction();
            model.setBackstageUserId(backstageUserId)
                    .setFunctionId(functionId)
                    .setSourceType(sourceType)
                    .setSourceValue(sourceValue)
                    .setCreateTime(now);
            models.add(model);
        }
        backstageUserFunctionDao.saveBatch(models);
        BackstageUserCache.removeLoginUserInfo(backstageUserId);
    }


}