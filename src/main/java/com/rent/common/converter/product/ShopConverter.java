
package com.rent.common.converter.product;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.rent.common.dto.product.ShopDto;
import com.rent.common.dto.product.ShopReqDto;
import com.rent.model.product.Shop;

import java.util.ArrayList;
import java.util.List;


/**
 * 店铺表Service
 *
 * @author youruo
 * @Date 2020-06-17 10:33
 */
public class ShopConverter {

    /**
     * model转dto
     *
     * @param model
     * @return
     */
    public static ShopDto model2Dto(Shop model) {
        if (model == null) {
            return null;
        }
        ShopDto dto = new ShopDto();
        dto.setId(model.getId());
        dto.setCreateTime(model.getCreateTime());
        dto.setUpdateTime(model.getUpdateTime());
        dto.setDeleteTime(model.getDeleteTime());
        dto.setName(model.getName());
        dto.setLogo(model.getLogo());
        dto.setBackground(model.getBackground());
        dto.setDescription(model.getDescription());
        dto.setShopTypeId(model.getShopTypeId());
        dto.setMainCategoryId(model.getMainCategoryId());
        dto.setStatus(model.getStatus());
        dto.setApprovalTime(model.getApprovalTime());
        dto.setReason(model.getReason());
        dto.setRegistTime(model.getRegistTime());
        dto.setShopId(model.getShopId());
        dto.setIsLocked(model.getIsLocked());
        dto.setLockedTime(model.getLockedTime());
        dto.setIsDisabled(model.getIsDisabled());
        dto.setServiceTel(model.getServiceTel());
        dto.setRecvMsgTel(model.getRecvMsgTel());
        dto.setIsHighQuality(model.getIsHighQuality());
        dto.setIsSigning(model.getIsSigning());
        dto.setIsPhoneExamination(model.getIsPhoneExamination());
        dto.setIsAutoAuditUser(model.getIsAutoAuditUser());
        dto.setUserEmail(model.getUserEmail());
        dto.setUserName(model.getUserName());
        dto.setUserTel(model.getUserTel());
        dto.setZfbCode(model.getZfbCode());
        dto.setZfbName(model.getZfbName());
        dto.setShopContractLink(model.getShopContractLink());
        dto.setShopAddress(model.getShopAddress());
        return dto;
    }

    /**
     * dto转do
     *
     * @param dto
     * @return
     */
    public static Shop dto2Model(ShopDto dto) {
        if (dto == null) {
            return null;
        }
        Shop model = new Shop();
        model.setId(dto.getId());
        model.setCreateTime(dto.getCreateTime());
        model.setUpdateTime(dto.getUpdateTime());
        model.setDeleteTime(dto.getDeleteTime());
        model.setName(dto.getName());
        model.setLogo(dto.getLogo());
        model.setBackground(dto.getBackground());
        model.setDescription(dto.getDescription());
        model.setShopTypeId(dto.getShopTypeId());
        model.setMainCategoryId(dto.getMainCategoryId());
        model.setStatus(dto.getStatus());
        model.setApprovalTime(dto.getApprovalTime());
        model.setReason(dto.getReason());
        model.setRegistTime(dto.getRegistTime());
        model.setShopId(dto.getShopId());
        model.setIsLocked(dto.getIsLocked());
        model.setLockedTime(dto.getLockedTime());
        model.setIsDisabled(dto.getIsDisabled());
        model.setServiceTel(dto.getServiceTel());
        model.setRecvMsgTel(dto.getRecvMsgTel());
        model.setIsHighQuality(dto.getIsHighQuality());
        model.setIsSigning(dto.getIsSigning());
        model.setIsPhoneExamination(dto.getIsPhoneExamination());
        model.setIsAutoAuditUser(dto.getIsAutoAuditUser());
        model.setUserEmail(dto.getUserEmail());
        model.setUserName(dto.getUserName());
        model.setUserTel(dto.getUserTel());
        model.setZfbCode(dto.getZfbCode());
        model.setZfbName(dto.getZfbName());
        model.setShopContractLink(dto.getShopContractLink());
        model.setShopAddress(dto.getShopAddress());
        return model;
    }

    /**
     * modelList转dtoList
     *
     * @param modelList
     * @return
     */
    public static List<ShopDto> modelList2DtoList(List<Shop> modelList) {
        if (CollectionUtil.isEmpty(modelList)) {
            return new ArrayList<>(0);
        }
        return Lists.newArrayList(Iterators.transform(modelList.iterator(), ShopConverter::model2Dto));
    }


    /**
     * dto转model
     *
     * @param dto
     * @return
     */
    public static Shop reqDto2Model(ShopReqDto dto) {
        if (dto == null) {
            return null;
        }
        Shop model = new Shop();
        model.setId(dto.getId());
        model.setCreateTime(dto.getCreateTime());
        model.setUpdateTime(dto.getUpdateTime());
        model.setDeleteTime(dto.getDeleteTime());
        model.setLogo(dto.getLogo());
        model.setBackground(dto.getBackground());
        model.setDescription(dto.getDescription());
        model.setShopTypeId(dto.getShopTypeId());
        model.setMainCategoryId(dto.getMainCategoryId());
        model.setStatus(dto.getStatus());
        model.setApprovalTime(dto.getApprovalTime());
        model.setReason(dto.getReason());
        model.setRegistTime(dto.getRegistTime());
        model.setShopId(dto.getShopId());
        model.setIsLocked(dto.getIsLocked());
        model.setLockedTime(dto.getLockedTime());
        model.setIsDisabled(dto.getIsDisabled());
        model.setServiceTel(dto.getServiceTel());
        model.setRecvMsgTel(dto.getRecvMsgTel());
        model.setIsHighQuality(dto.getIsHighQuality());
        model.setIsSigning(dto.getIsSigning());
        model.setIsPhoneExamination(dto.getIsPhoneExamination());
        model.setIsAutoAuditUser(dto.getIsAutoAuditUser());
        model.setUserEmail(dto.getUserEmail());
        model.setUserName(dto.getUserName());
        model.setUserTel(dto.getUserTel());
        model.setZfbCode(dto.getZfbCode());
        model.setZfbName(dto.getZfbName());
        model.setShopContractLink(dto.getShopContractLink());
        model.setShopAddress(dto.getShopAddress());
        return model;
    }


}