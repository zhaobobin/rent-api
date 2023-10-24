
package com.rent.common.converter.product;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.rent.common.dto.product.ProductCyclePricesDto;
import com.rent.model.product.ProductCyclePrices;

import java.util.ArrayList;
import java.util.List;


/**
 * 商品周期价格表Service
 *
 * @author youruo
 * @Date 2020-06-16 15:08
 */
public class ProductCyclePricesConverter {

    /**
     * model转dto
     *
     * @param model
     * @return
     */
    public static ProductCyclePricesDto model2Dto(ProductCyclePrices model) {
        if (model == null) {
            return null;
        }
        ProductCyclePricesDto dto = new ProductCyclePricesDto();
        dto.setId(model.getId());
        dto.setCreateTime(model.getCreateTime());
        dto.setUpdateTime(model.getUpdateTime());
        dto.setDeleteTime(model.getDeleteTime());
        dto.setItemId(model.getItemId());
        dto.setSkuId(model.getSkuId());
        dto.setDays(model.getDays());
        dto.setPrice(model.getPrice());
        dto.setSesameDeposit(model.getSesameDeposit());
        dto.setSalePrice(model.getSalePrice());
        return dto;
    }

    /**
     * dto转do
     *
     * @param dto
     * @return
     */
    public static ProductCyclePrices dto2Model(ProductCyclePricesDto dto) {
        if (dto == null) {
            return null;
        }
        ProductCyclePrices model = new ProductCyclePrices();
        model.setId(dto.getId());
        model.setCreateTime(dto.getCreateTime());
        model.setUpdateTime(dto.getUpdateTime());
        model.setDeleteTime(dto.getDeleteTime());
        model.setItemId(dto.getItemId());
        model.setSkuId(dto.getSkuId());
        model.setDays(dto.getDays());
        model.setPrice(dto.getPrice());
        model.setSalePrice(dto.getSalePrice());
        model.setSesameDeposit(dto.getSesameDeposit());
        return model;
    }

    /**
     * modelList转dtoList
     *
     * @param modelList
     * @return
     */
    public static List<ProductCyclePricesDto> modelList2DtoList(List<ProductCyclePrices> modelList) {
        if (CollectionUtil.isEmpty(modelList)) {
            return new ArrayList<>(0);
        }
        return Lists.newArrayList(Iterators.transform(modelList.iterator(), ProductCyclePricesConverter::model2Dto));
    }
}