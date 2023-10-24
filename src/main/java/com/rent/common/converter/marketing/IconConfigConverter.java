        
package com.rent.common.converter.marketing;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.rent.common.dto.marketing.IconConfigListDto;
import com.rent.model.marketing.IconConfig;


import java.util.ArrayList;
import java.util.List;


/**
 * @author xiaotong
 */
public class IconConfigConverter {

    public static IconConfigListDto model2Dto(IconConfig model){
        if(model == null){
            return  null;
        }
        IconConfigListDto dto = new IconConfigListDto();
        dto.setId(model.getId());
        dto.setUrl(model.getUrl());
        dto.setTitle(model.getTitle());
        dto.setJumpUrl(model.getJumpUrl());
        dto.setCreateTime(model.getCreateTime());
        dto.setSort(model.getSort());
        dto.setTag(model.getTag());
        return dto;
    }

    public static List<IconConfigListDto> modelList2DtoList(List<IconConfig> modelList) {
        if (CollectionUtil.isEmpty(modelList)) {
            return new ArrayList<>(0);
        }
        return Lists.newArrayList(Iterators.transform(modelList.iterator(), IconConfigConverter::model2Dto));
    }

}