        
package com.rent.service.user.impl;


import com.rent.common.dto.user.DepartmentFunctionReqDto;
import com.rent.dao.user.BackstageDepartmentFunctionDao;
import com.rent.model.user.BackstageDepartmentFunction;
import com.rent.service.user.BackstageDepartmentFunctionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 后台部门可以使用的功能Service
 *
 * @author zhao
 * @Date 2020-06-24 11:47
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class BackstageDepartmentFunctionServiceImpl implements BackstageDepartmentFunctionService {

    private final BackstageDepartmentFunctionDao backstageDepartmentFunctionDao;

    @Override
    public List<Long> getDepartmentFunctionId(Long departmentId) {
        return backstageDepartmentFunctionDao.getDepartmentFunctionId(departmentId);
    }

    @Override
    public Boolean updateDepartmentFunction(DepartmentFunctionReqDto request) {
        backstageDepartmentFunctionDao.deleteByDepartmentId(request.getDepartmentId());
        List<BackstageDepartmentFunction> list = new ArrayList<>(request.getFunctionIds().size());
        Date now = new Date();
        for(Long functionId:request.getFunctionIds()){
            BackstageDepartmentFunction model = new BackstageDepartmentFunction();
            model.setDepartmentId(request.getDepartmentId());
            model.setFunctionId(functionId);
            model.setCreateTime(now);
            list.add(model);
        }
        backstageDepartmentFunctionDao.saveBatch(list);
        return Boolean.TRUE;
    }

    @Override
    public void deleteByDepartmentId(Long id) {
        backstageDepartmentFunctionDao.deleteByDepartmentId(id);
    }
}