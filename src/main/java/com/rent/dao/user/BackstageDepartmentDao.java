        
package com.rent.dao.user;

import com.rent.common.dto.user.BackstageDepartmentDto;
import com.rent.config.mybatis.IBaseDao;
import com.rent.model.user.BackstageDepartment;

/**
 * BackstageDepartmentDao
 *
 * @author zhao
 * @Date 2020-06-24 11:47
 */
public interface BackstageDepartmentDao extends IBaseDao<BackstageDepartment> {

    /**
     * 后台修改部门信息
     * @param request
     * @return
     */
    Boolean modifyBackstageDepartment(BackstageDepartmentDto request);

    /**
     * 删除部门
     * @param request
     */
    boolean deleteBackstageDepartment(BackstageDepartmentDto request);
}
