        
package com.rent.common.converter.user;

import com.rent.common.dto.user.BackstageDepartmentUserDto;
import com.rent.model.user.BackstageDepartmentUser;


/**
 * 后台部门用户映射表Service
 *
 * @author zhao
 * @Date 2020-06-24 11:47
 */
public class BackstageDepartmentUserConverter {

    /**
     * dto转do
     *
     * @param dto
     * @return
     */
    public static BackstageDepartmentUser dto2Model(BackstageDepartmentUserDto dto) {
        if (dto == null) {
            return null;
        }
        BackstageDepartmentUser model = new BackstageDepartmentUser();
        model.setId(dto.getId());
        model.setDepartmentId(dto.getDepartmentId());
        model.setBackstageUserId(dto.getBackstageUserId());
        model.setCreateTime(dto.getCreateTime());
        return model;
    }
}