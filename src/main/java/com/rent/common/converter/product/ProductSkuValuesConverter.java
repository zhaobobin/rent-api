        
package com.rent.common.converter.product;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.rent.common.dto.product.ProductSkuValuesDto;
import com.rent.model.product.ProductSkuValues;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品sku规格属性value表Service
 *
 * @author youruo
 * @Date 2020-06-16 15:28
 */
public class ProductSkuValuesConverter {

    /**
     * model转dto
     *
     * @param model
     * @return
     */
    public static ProductSkuValuesDto model2Dto(ProductSkuValues model) {
        if (model == null) {
            return null;
        }
        ProductSkuValuesDto dto = new ProductSkuValuesDto();
        dto.setId(model.getId());
        dto.setCreateTime(model.getCreateTime());
        dto.setUpdateTime(model.getUpdateTime());
        dto.setDeleteTime(model.getDeleteTime());
        dto.setSpecValueId(model.getSpecValueId());
        dto.setSkuId(model.getSkuId());
        return dto;
    }

    /**
     * dto转do
     *
     * @param dto
     * @return
     */
    public static ProductSkuValues dto2Model(ProductSkuValuesDto dto) {
        if (dto == null) {
            return null;
        }
        ProductSkuValues model = new ProductSkuValues();
        model.setId(dto.getId());
        model.setCreateTime(dto.getCreateTime());
        model.setUpdateTime(dto.getUpdateTime());
        model.setDeleteTime(dto.getDeleteTime());
        model.setSpecValueId(dto.getSpecValueId());
        model.setSkuId(dto.getSkuId());
        return model;
    }

    /**
     * modelList转dtoList
     *
     * @param modelList
     * @return
     */
    public static List<ProductSkuValuesDto> modelList2DtoList(List<ProductSkuValues> modelList) {
        if (CollectionUtil.isEmpty(modelList)) {
            return new ArrayList<>(0);
        }
        return Lists.newArrayList(Iterators.transform(modelList.iterator(), ProductSkuValuesConverter::model2Dto));
    }
}