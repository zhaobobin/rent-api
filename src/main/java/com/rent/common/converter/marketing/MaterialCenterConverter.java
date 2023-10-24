        
package com.rent.common.converter.marketing;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.rent.common.dto.marketing.MaterialCenterCategoryDto;
import com.rent.common.dto.marketing.MaterialCenterItemDto;
import com.rent.model.marketing.MaterialCenterCategory;
import com.rent.model.marketing.MaterialCenterItem;

import java.util.ArrayList;
import java.util.List;

/**
 * 投诉
 *
 * @author zhao
 * @Date 2020-07-07 15:04
 */
public class MaterialCenterConverter {


    public static MaterialCenterCategory categoryDtoToModel(MaterialCenterCategoryDto dto){
        if(dto==null){
            return null;
        }
        MaterialCenterCategory model = new MaterialCenterCategory();
        model.setId(dto.getId());
        model.setHeight(dto.getHeight());
        model.setWidth(dto.getWidth());
        model.setName(dto.getName());
        return model;
    }

    public static List<MaterialCenterCategoryDto> categoryModelsToDtoList(List<MaterialCenterCategory> models){
        if (CollectionUtil.isEmpty(models)) {
            return new ArrayList<>(0);
        }
        return Lists.newArrayList(Iterators.transform(models.iterator(), MaterialCenterConverter::categoryModelToDto));
    }

    public static MaterialCenterCategoryDto categoryModelToDto(MaterialCenterCategory model){
        if(model==null){
            return null;
        }
        MaterialCenterCategoryDto dto = new MaterialCenterCategoryDto();
        dto.setId(model.getId());
        dto.setHeight(model.getHeight());
        dto.setWidth(model.getWidth());
        dto.setName(model.getName());
        return dto;
    }

    public static MaterialCenterItem itemDtoToModel(MaterialCenterItemDto dto){
        if(dto==null){
            return null;
        }
        MaterialCenterItem model = new MaterialCenterItem();
        model.setId(dto.getId());
        model.setName(dto.getName());
        model.setCategoryId(dto.getCategoryId());
        model.setFileUrl(dto.getFileUrl());
        return model;
    }

    public static List<MaterialCenterItemDto> itemModelsToDtoList(List<MaterialCenterItem> models){
        if (CollectionUtil.isEmpty(models)) {
            return new ArrayList<>(0);
        }
        return Lists.newArrayList(Iterators.transform(models.iterator(), MaterialCenterConverter::itemModelToDto));
    }

    public static MaterialCenterItemDto itemModelToDto(MaterialCenterItem model){
        if(model==null){
            return null;
        }
        MaterialCenterItemDto dto = new MaterialCenterItemDto();
        dto.setId(model.getId());
        dto.setCategoryId(model.getCategoryId());
        dto.setFileUrl(model.getFileUrl());
        dto.setName(model.getName());
        return dto;
    }


}