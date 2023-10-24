        
package com.rent.common.converter.product;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.rent.common.dto.product.ShopEnterpriseCertificatesDto;
import com.rent.model.product.ShopEnterpriseCertificates;

import java.util.ArrayList;
import java.util.List;


/**
 * 店铺资质证书表Service
 *
 * @author youruo
 * @Date 2020-06-17 10:45
 */
public class ShopEnterpriseCertificatesConverter {

    /**
     * model转dto
     *
     * @param model
     * @return
     */
    public static ShopEnterpriseCertificatesDto model2Dto(ShopEnterpriseCertificates model) {
        if (model == null) {
            return null;
        }
        ShopEnterpriseCertificatesDto dto = new ShopEnterpriseCertificatesDto();
        dto.setId(model.getId());
        dto.setCreateTime(model.getCreateTime());
        dto.setUpdateTime(model.getUpdateTime());
        dto.setDeleteTime(model.getDeleteTime());
        dto.setSeInfoId(model.getSeInfoId());
        dto.setImage(model.getImage());
        dto.setType(model.getType());
        return dto;
    }

    /**
     * dto转do
     *
     * @param dto
     * @return
     */
    public static ShopEnterpriseCertificates dto2Model(ShopEnterpriseCertificatesDto dto) {
        if (dto == null) {
            return null;
        }
        ShopEnterpriseCertificates model = new ShopEnterpriseCertificates();
        model.setId(dto.getId());
        model.setCreateTime(dto.getCreateTime());
        model.setUpdateTime(dto.getUpdateTime());
        model.setDeleteTime(dto.getDeleteTime());
        model.setSeInfoId(dto.getSeInfoId());
        model.setImage(dto.getImage());
        model.setType(dto.getType());
        return model;
    }

    /**
     * modelList转dtoList
     *
     * @param modelList
     * @return
     */
    public static List<ShopEnterpriseCertificatesDto> modelList2DtoList(List<ShopEnterpriseCertificates> modelList) {
        if (CollectionUtil.isEmpty(modelList)) {
            return new ArrayList<>(0);
        }
        return Lists.newArrayList(Iterators.transform(modelList.iterator(), ShopEnterpriseCertificatesConverter::model2Dto));
    }
}