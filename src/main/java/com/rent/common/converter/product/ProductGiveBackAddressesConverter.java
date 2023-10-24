        
package com.rent.common.converter.product;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.rent.common.dto.product.ProductGiveBackAddressesDto;
import com.rent.model.product.ProductGiveBackAddresses;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品归还地址Service
 *
 * @author youruo
 * @Date 2020-06-16 15:12
 */
public class ProductGiveBackAddressesConverter {

    /**
     * model转dto
     *
     * @param model
     * @return
     */
    public static ProductGiveBackAddressesDto model2Dto(ProductGiveBackAddresses model) {
        if (model == null) {
            return null;
        }
        ProductGiveBackAddressesDto dto = new ProductGiveBackAddressesDto();
        dto.setId(model.getId());
        dto.setCreateTime(model.getCreateTime());
        dto.setUpdateTime(model.getUpdateTime());
        dto.setDeleteTime(model.getDeleteTime());
        dto.setItemId(model.getItemId());
        dto.setAddressId(model.getAddressId());
        return dto;
    }

    /**
     * dto转do
     *
     * @param dto
     * @return
     */
    public static ProductGiveBackAddresses dto2Model(ProductGiveBackAddressesDto dto) {
        if (dto == null) {
            return null;
        }
        ProductGiveBackAddresses model = new ProductGiveBackAddresses();
        model.setId(dto.getId());
        model.setCreateTime(dto.getCreateTime());
        model.setUpdateTime(dto.getUpdateTime());
        model.setDeleteTime(dto.getDeleteTime());
        model.setItemId(dto.getItemId());
        model.setAddressId(dto.getAddressId());
        return model;
    }

    /**
     * modelList转dtoList
     *
     * @param modelList
     * @return
     */
    public static List<ProductGiveBackAddressesDto> modelList2DtoList(List<ProductGiveBackAddresses> modelList) {
        if (CollectionUtil.isEmpty(modelList)) {
            return new ArrayList<>(0);
        }
        return Lists.newArrayList(Iterators.transform(modelList.iterator(), ProductGiveBackAddressesConverter::model2Dto));
    }


    public static List<ProductGiveBackAddressesDto> modelList2DtoListV1(ProductGiveBackAddresses model) {
        if (null == model) {
            return new ArrayList<>(0);
        }
        ProductGiveBackAddressesDto dto = ProductGiveBackAddressesConverter.model2Dto(model);
        return Lists.newArrayList(dto);
    }

}