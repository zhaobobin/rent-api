
package com.rent.common.converter.product;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.rent.common.dto.product.ShopGiveBackAddressesDto;
import com.rent.common.dto.product.ShopGiveBackAddressesReqDto;
import com.rent.model.product.ShopGiveBackAddresses;

import java.util.ArrayList;
import java.util.List;


/**
 * 店铺归还地址表Service
 *
 * @author youruo
 * @Date 2020-06-17 10:47
 */
public class ShopGiveBackAddressesConverter {

    /**
     * model转dto
     *
     * @param model
     * @return
     */
    public static ShopGiveBackAddressesDto model2Dto(ShopGiveBackAddresses model) {
        if (model == null) {
            return null;
        }
        ShopGiveBackAddressesDto dto = new ShopGiveBackAddressesDto();
        dto.setId(model.getId());
        dto.setCreateTime(model.getCreateTime());
        dto.setUpdateTime(model.getUpdateTime());
        dto.setDeleteTime(model.getDeleteTime());
        dto.setShopId(model.getShopId());
        dto.setProvinceId(model.getProvinceId());
        dto.setCityId(model.getCityId());
        dto.setAreaId(model.getAreaId());
        dto.setStreet(model.getStreet());
        dto.setTelephone(model.getTelephone());
        dto.setZcode(model.getZcode());
        dto.setName(model.getName());
        dto.setFreightType(model.getFreightType());
        return dto;
    }

    /**
     * dto转do
     *
     * @param dto
     * @return
     */
    public static ShopGiveBackAddresses dto2Model(ShopGiveBackAddressesDto dto) {
        if (dto == null) {
            return null;
        }
        ShopGiveBackAddresses model = new ShopGiveBackAddresses();
        model.setId(dto.getId());
        model.setCreateTime(dto.getCreateTime());
        model.setUpdateTime(dto.getUpdateTime());
        model.setDeleteTime(dto.getDeleteTime());
        model.setShopId(dto.getShopId());
        model.setProvinceId(dto.getProvinceId());
        model.setCityId(dto.getCityId());
        model.setAreaId(dto.getAreaId());
        model.setStreet(dto.getStreet());
        model.setTelephone(dto.getTelephone());
        model.setZcode(dto.getZcode());
        model.setName(dto.getName());
        model.setFreightType(dto.getFreightType());

        return model;
    }

    /**
     * modelList转dtoList
     *
     * @param modelList
     * @return
     */
    public static List<ShopGiveBackAddressesDto> modelList2DtoList(List<ShopGiveBackAddresses> modelList) {
        if (CollectionUtil.isEmpty(modelList)) {
            return new ArrayList<>(0);
        }
        return Lists.newArrayList(Iterators.transform(modelList.iterator(), ShopGiveBackAddressesConverter::model2Dto));
    }

    /**
     * dto转model
     *
     * @param dto
     * @return
     */
    public static ShopGiveBackAddresses reqDto2Model(ShopGiveBackAddressesReqDto dto) {
        if (dto == null) {
            return null;
        }
        ShopGiveBackAddresses model = new ShopGiveBackAddresses();
        model.setId(dto.getId());
        model.setCreateTime(dto.getCreateTime());
        model.setUpdateTime(dto.getUpdateTime());
        model.setDeleteTime(dto.getDeleteTime());
        model.setShopId(dto.getShopId());
        model.setProvinceId(dto.getProvinceId());
        model.setCityId(dto.getCityId());
        model.setAreaId(dto.getAreaId());
        model.setStreet(dto.getStreet());
        model.setTelephone(dto.getTelephone());
        model.setZcode(dto.getZcode());
        model.setName(dto.getName());
        model.setFreightType(dto.getFreightType());

        return model;
    }


}