        
package com.rent.common.converter.product;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.rent.common.dto.product.ProductAdditionalServicesDto;
import com.rent.model.product.ProductAdditionalServices;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品增值服务表Service
 *
 * @author youruo
 * @Date 2020-06-16 15:07
 */
public class ProductAdditionalServicesConverter {

    /**
     * model转dto
     *
     * @param model
     * @return
     */
    public static ProductAdditionalServicesDto model2Dto(ProductAdditionalServices model) {
        if (model == null) {
            return null;
        }
        ProductAdditionalServicesDto dto = new ProductAdditionalServicesDto();
        dto.setId(model.getId());
        dto.setCreateTime(model.getCreateTime());
        dto.setUpdateTime(model.getUpdateTime());
        dto.setDeleteTime(model.getDeleteTime());
        dto.setProductId(model.getProductId());
        dto.setShopAdditionalServicesId(model.getShopAdditionalServicesId());
        return dto;
    }

    /**
     * dto转do
     *
     * @param dto
     * @return
     */
    public static ProductAdditionalServices dto2Model(ProductAdditionalServicesDto dto) {
        if (dto == null) {
            return null;
        }
        ProductAdditionalServices model = new ProductAdditionalServices();
        model.setId(dto.getId());
        model.setCreateTime(dto.getCreateTime());
        model.setUpdateTime(dto.getUpdateTime());
        model.setDeleteTime(dto.getDeleteTime());
        model.setProductId(dto.getProductId());
        model.setShopAdditionalServicesId(dto.getShopAdditionalServicesId());
        return model;
    }

    /**
     * modelList转dtoList
     *
     * @param modelList
     * @return
     */
    public static List<ProductAdditionalServicesDto> modelList2DtoList(List<ProductAdditionalServices> modelList) {
        if (CollectionUtil.isEmpty(modelList)) {
            return new ArrayList<>(0);
        }
        return Lists.newArrayList(Iterators.transform(modelList.iterator(), ProductAdditionalServicesConverter::model2Dto));
    }
}