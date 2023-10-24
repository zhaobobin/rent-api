        
package com.rent.common.converter.user;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.rent.common.dto.api.request.AddUserAddressReq;
import com.rent.common.dto.api.request.ModifyUserAddressReq;
import com.rent.common.dto.api.resp.ListUserAddressResp;
import com.rent.common.dto.user.UserAddressDto;
import com.rent.model.user.UserAddress;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * 用户地址表Service
 *
 * @author zhao
 * @Date 2020-06-18 13:54
 */
public class UserAddressConverter {

    /**
     * model转dto
     *
     * @param model
     * @return
     */
    public static UserAddressDto model2Dto(UserAddress model) {
        if (model == null) {
            return null;
        }
        UserAddressDto dto = new UserAddressDto();
        dto.setId(model.getId());
        dto.setCreateTime(model.getCreateTime());
        dto.setUpdateTime(model.getUpdateTime());
        dto.setDeleteTime(model.getDeleteTime());
        dto.setProvince(model.getProvince());
        dto.setCity(model.getCity());
        dto.setUid(model.getUid());
        dto.setArea(model.getArea());
        dto.setStreet(model.getStreet());
        dto.setZcode(model.getZcode());
        dto.setTelephone(model.getTelephone());
        dto.setRealname(model.getRealname());
        dto.setIsDefault(model.getIsDefault());
        return dto;
    }

    /**
     * dto转do
     *
     * @param dto
     * @return
     */
    public static UserAddress dto2Model(UserAddressDto dto) {
        if (dto == null) {
            return null;
        }
        UserAddress model = new UserAddress();
        model.setId(dto.getId());
        model.setCreateTime(dto.getCreateTime());
        model.setUpdateTime(dto.getUpdateTime());
        model.setDeleteTime(dto.getDeleteTime());
        model.setProvince(dto.getProvince());
        model.setCity(dto.getCity());
        model.setUid(dto.getUid());
        model.setArea(dto.getArea());
        model.setStreet(dto.getStreet());
        model.setZcode(dto.getZcode());
        model.setTelephone(dto.getTelephone());
        model.setRealname(dto.getRealname());
        model.setIsDefault(dto.getIsDefault());
        return model;
    }

    /**
     * dto转do
     *
     * @param dto
     * @return
     */
    public static UserAddress addReq2Model(AddUserAddressReq dto) {
        if (dto == null) {
            return null;
        }
        UserAddress model = new UserAddress();
        model.setProvince(dto.getProvince());
        model.setCity(dto.getCity());
        model.setUid(dto.getUid());
        model.setArea(dto.getArea());
        model.setStreet(dto.getStreet());
        model.setTelephone(dto.getTelephone());
        model.setRealname(dto.getRealname());
        model.setIsDefault(dto.getIsDefault());
        return model;
    }

    /**
     * dto转do
     *
     * @param dto
     * @return
     */
    public static UserAddress modifyReq2Model(ModifyUserAddressReq dto) {
        if (dto == null) {
            return null;
        }
        UserAddress model = new UserAddress();
        model.setProvince(dto.getProvince());
        model.setCity(dto.getCity());
        model.setArea(dto.getArea());
        model.setStreet(dto.getStreet());
        model.setTelephone(dto.getTelephone());
        model.setRealname(dto.getRealname());
        model.setIsDefault(dto.getIsDefault());
        return model;
    }

    /**
     * modelList转dtoList
     *
     * @param modelList
     * @return
     */
    public static List<UserAddressDto> modelList2DtoList(List<UserAddress> modelList) {
        if (CollectionUtil.isEmpty(modelList)) {
            return Collections.emptyList();
        }
        return Lists.newArrayList(Iterators.transform(modelList.iterator(), UserAddressConverter::model2Dto));
    }

    /**
     * modelList转dtoList
     *
     * @param modelList
     * @return
     */
    public static List<ListUserAddressResp> modelList2ListResp(List<UserAddress> modelList) {
        if (CollectionUtil.isEmpty(modelList)) {
            return Collections.emptyList();
        }
        List<ListUserAddressResp> respList = new ArrayList<>(modelList.size());
        for (UserAddress model : modelList) {
            ListUserAddressResp dto = new ListUserAddressResp();
            dto.setId(model.getId());
            dto.setProvince(model.getProvince());
            dto.setCity(model.getCity());
            dto.setUid(model.getUid());
            dto.setArea(model.getArea());
            dto.setStreet(model.getStreet());
            dto.setTelephone(model.getTelephone());
            dto.setRealname(model.getRealname());
            dto.setIsDefault(model.getIsDefault());
            respList.add(dto);
        }
        return respList;
    }
}