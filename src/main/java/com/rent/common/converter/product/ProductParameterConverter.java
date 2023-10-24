        
package com.rent.common.converter.product;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.rent.common.dto.product.ProductParameterDto;
import com.rent.model.product.ProductParameter;

import java.util.ArrayList;
import java.util.List;


/**
 * 商品参数信息表Service
 *
 * @author youruo
 * @Date 2020-06-29 18:33
 */
public class ProductParameterConverter {

    /**
     * model转dto
     *
     * @param model
     * @return
     */
    public static ProductParameterDto model2Dto(ProductParameter model) {
        if (model == null) {
            return null;
        }
        ProductParameterDto dto = new ProductParameterDto();
        dto.setId(model.getId());
        dto.setCreateTime(model.getCreateTime());
        dto.setUpdateTime(model.getUpdateTime());
        dto.setDeleteTime(model.getDeleteTime());
        dto.setName(model.getName());
        dto.setValue(model.getValue());
        dto.setProductId(model.getProductId());
        return dto;
    }

    /**
     * dto转do
     *
     * @param dto
     * @return
     */
    public static ProductParameter dto2Model(ProductParameterDto dto) {
        if (dto == null) {
            return null;
        }
        ProductParameter model = new ProductParameter();
        model.setId(dto.getId());
        model.setCreateTime(dto.getCreateTime());
        model.setUpdateTime(dto.getUpdateTime());
        model.setDeleteTime(dto.getDeleteTime());
        model.setName(dto.getName());
        model.setValue(dto.getValue());
        model.setProductId(dto.getProductId());
        return model;
    }

    /**
     * modelList转dtoList
     *
     * @param modelList
     * @return
     */
    public static List<ProductParameterDto> modelList2DtoList(List<ProductParameter> modelList) {
        if (CollectionUtil.isEmpty(modelList)) {
            return new ArrayList<>(0);
        }
        return Lists.newArrayList(Iterators.transform(modelList.iterator(), ProductParameterConverter::model2Dto));
    }
}