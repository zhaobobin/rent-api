package com.rent.common.converter.product;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.rent.common.dto.product.ProductImageDto;
import com.rent.common.dto.product.ProductImageReqDto;
import com.rent.model.product.ProductImage;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品主图表Service
 *
 * @author youruo
 * @Date 2020-06-16 15:17
 */
public class ProductImageConverter {

    /**
     * model转dto
     *
     * @param model
     * @return
     */
    public static ProductImageDto model2Dto(ProductImage model) {
        if (model == null) {
            return null;
        }
        ProductImageDto dto = new ProductImageDto();
        dto.setId(model.getId());
        dto.setCreateTime(model.getCreateTime());
        dto.setUpdateTime(model.getUpdateTime());
        dto.setDeleteTime(model.getDeleteTime());
        dto.setSrc(model.getSrc());
        dto.setProductId(model.getProductId());
        dto.setIsMain(model.getIsMain());
        return dto;
    }

    /**
     * dto转do
     *
     * @param dto
     * @return
     */
    public static ProductImage dto2Model(ProductImageDto dto) {
        if (dto == null) {
            return null;
        }
        ProductImage model = new ProductImage();
        model.setId(dto.getId());
        model.setCreateTime(dto.getCreateTime());
        model.setUpdateTime(dto.getUpdateTime());
        model.setDeleteTime(dto.getDeleteTime());
        model.setSrc(dto.getSrc());
        model.setProductId(dto.getProductId());
        model.setIsMain(dto.getIsMain());
        return model;
    }

    /**
     * modelList转dtoList
     *
     * @param modelList
     * @return
     */
    public static List<ProductImageDto> modelList2DtoList(List<ProductImage> modelList) {
        if (CollectionUtil.isEmpty(modelList)) {
            return new ArrayList<>(0);
        }
        return Lists.newArrayList(Iterators.transform(modelList.iterator(), ProductImageConverter::model2Dto));
    }

    /**
     * dtoList转doList
     *
     * @param dtoList
     * @return
     */
    public static List<ProductImage> dtoList2ModelList(List<ProductImageDto> dtoList) {
        if (CollectionUtil.isEmpty(dtoList)) {
            return new ArrayList<>(0);
        }
        return Lists.newArrayList(Iterators.transform(dtoList.iterator(), ProductImageConverter::dto2Model));
    }

    /**
     * dto转model
     *
     * @param dto
     * @return
     */
    public static ProductImage reqDto2Model(ProductImageReqDto dto) {
        if (dto == null) {
            return null;
        }
        ProductImage model = new ProductImage();
        model.setId(dto.getId());
        model.setCreateTime(dto.getCreateTime());
        model.setUpdateTime(dto.getUpdateTime());
        model.setDeleteTime(dto.getDeleteTime());
        model.setSrc(dto.getSrc());
        model.setProductId(dto.getProductId());
        model.setIsMain(dto.getIsMain());
        return model;
    }

}