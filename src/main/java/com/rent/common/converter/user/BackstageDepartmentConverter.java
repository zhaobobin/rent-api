        
package com.rent.common.converter.user;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.rent.common.dto.user.BackstageDepartmentDto;
import com.rent.common.dto.user.BackstageDepartmentReqDto;
import com.rent.model.user.BackstageDepartment;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;


/**
 * 后台部门Service
 *
 * @author zhao
 * @Date 2020-06-24 11:47
 */
@Slf4j
public class BackstageDepartmentConverter {

    /**
     * model转dto
     *
     * @param model
     * @return
     */
    public static BackstageDepartmentDto model2Dto(BackstageDepartment model){
        if (model == null) {
            return null;
        }
        try {
            BackstageDepartmentDto dto = new BackstageDepartmentDto();
            dto.setShopId(model.getShopId());
            dto.setPlatform(model.getPlatform());
            dto.setCreateTime(model.getCreateTime());
            dto.setDescription(model.getDescription());
            dto.setId(model.getId());
            dto.setName(model.getName());
            dto.setUpdateTime(model.getUpdateTime());
            return dto;
        } catch (Exception e) {
            log.error("异常",e);
            return null;
        }
    }

    /**
     * dto转do
     *
     * @param dto
     * @return
     */
    public static BackstageDepartment dto2Model(BackstageDepartmentDto dto){
        if (dto == null) {
            return null;
        }
        try {
            BackstageDepartment model = new BackstageDepartment();
            model.setCreateTime(dto.getCreateTime());
            model.setDescription(dto.getDescription());
            model.setId(dto.getId());
            model.setName(dto.getName());
            model.setPlatform(dto.getPlatform());
            model.setShopId(dto.getShopId());
            model.setUpdateTime(dto.getUpdateTime());
            return model;
        } catch (Exception e) {
            log.error("异常",e);
            return null;
        }
    }

    /**
     * modelList转dtoList
     *
     * @param modelList
     * @return
     */
    public static List<BackstageDepartmentDto> modelList2DtoList(List<BackstageDepartment> modelList){
        if (CollectionUtil.isEmpty(modelList)) {
            return Collections.emptyList();
        }
        return Lists.newArrayList(Iterators.transform(modelList.iterator(), BackstageDepartmentConverter::model2Dto));
    }


    /**
     * dto转model
     *
     * @param dto
     * @return
     */
    public static BackstageDepartment reqDto2Model(BackstageDepartmentReqDto dto) {
        if (dto == null) {
            return null;
        }
        try {
            BackstageDepartment model = new BackstageDepartment();
            model.setCreateTime(dto.getCreateTime());
            model.setDescription(dto.getDescription());
            model.setId(dto.getId());
            model.setName(dto.getName());
            model.setPlatform(dto.getPlatform());
            model.setShopId(dto.getShopId());
            model.setUpdateTime(dto.getUpdateTime());
            return model;
        } catch (Exception e) {
            log.error("异常",e);
            return null;
        }
    }


}