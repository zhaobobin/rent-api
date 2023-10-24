        
package com.rent.common.converter.product;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.rent.common.dto.product.ShopAdditionalServicesDto;
import com.rent.model.product.ShopAdditionalServices;

import java.util.ArrayList;
import java.util.List;


/**
 * 店铺增值服务表Service
 *
 * @author youruo
 * @Date 2020-06-17 10:35
 */
public class ShopAdditionalServicesConverter {

    /**
     * model转dto
     *
     * @param model
     * @return
     */
    public static ShopAdditionalServicesDto model2Dto(ShopAdditionalServices model) {
        if (model == null) {
            return null;
        }
        ShopAdditionalServicesDto dto = new ShopAdditionalServicesDto();
        dto.setId(model.getId());
        dto.setCreateTime(model.getCreateTime());
        dto.setUpdateTime(model.getUpdateTime());
        dto.setDeleteTime(model.getDeleteTime());
        dto.setShopId(model.getShopId());
        dto.setName(model.getName());
        dto.setContent(model.getContent());
        dto.setPrice(model.getPrice());
        dto.setApprovalStatus(model.getApprovalStatus());
        dto.setStatus(model.getStatus());
        dto.setDescription(model.getDescription());
        return dto;
    }

    /**
     * dto转do
     *
     * @param dto
     * @return
     */
    public static ShopAdditionalServices dto2Model(ShopAdditionalServicesDto dto) {
        if (dto == null) {
            return null;
        }
        ShopAdditionalServices model = new ShopAdditionalServices();
        model.setId(dto.getId());
        model.setCreateTime(dto.getCreateTime());
        model.setUpdateTime(dto.getUpdateTime());
        model.setDeleteTime(dto.getDeleteTime());
        model.setShopId(dto.getShopId());
        model.setName(dto.getName());
        model.setContent(dto.getContent());
        model.setPrice(dto.getPrice());
        model.setApprovalStatus(dto.getApprovalStatus());
        model.setStatus(dto.getStatus());
        model.setDescription(dto.getDescription());
        return model;
    }

    /**
     * modelList转dtoList
     *
     * @param modelList
     * @return
     */
    public static List<ShopAdditionalServicesDto> modelList2DtoList(List<ShopAdditionalServices> modelList) {
        if (CollectionUtil.isEmpty(modelList)) {
            return new ArrayList<>(0);
        }
        return Lists.newArrayList(Iterators.transform(modelList.iterator(), ShopAdditionalServicesConverter::model2Dto));
    }
}