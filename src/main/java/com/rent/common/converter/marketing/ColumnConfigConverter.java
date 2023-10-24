        
package com.rent.common.converter.marketing;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.rent.common.dto.marketing.ActivityConfigListDto;
import com.rent.common.dto.marketing.ColumnConfigListDto;
import com.rent.model.marketing.ColumnConfig;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * @author xiaotong
 */
public class ColumnConfigConverter {

    public static ColumnConfigListDto model2Dto(ColumnConfig model){
        if(model == null){
            return  null;
        }
        ColumnConfigListDto dto = new ColumnConfigListDto();
        dto.setId(model.getId());
        dto.setChannelId(model.getChannelId());
        dto.setName(model.getName());
        dto.setUrl(model.getUrl());
        dto.setProductIds(Arrays.asList(model.getProductIds().split(",")));
        dto.setPaperwork(model.getPaperwork());
        return dto;
    }

    public static List<ColumnConfigListDto> modelList2DtoList(List<ColumnConfig> modelList) {
        if (CollectionUtil.isEmpty(modelList)) {
            return new ArrayList<>(0);
        }
        return Lists.newArrayList(Iterators.transform(modelList.iterator(), ColumnConfigConverter::model2Dto));
    }


    public static ActivityConfigListDto model2DtoForActivity(ColumnConfig model){
        if(model == null){
            return  null;
        }
        ActivityConfigListDto dto = new ActivityConfigListDto();
        dto.setId(model.getId());
        dto.setUrl(model.getUrl());
        dto.setJumpUrl(model.getJumpUrl());
        return dto;
    }

    public static List<ActivityConfigListDto> modelList2DtoListForActivity(List<ColumnConfig> modelList) {
        if (CollectionUtil.isEmpty(modelList)) {
            return new ArrayList<>(0);
        }
        return Lists.newArrayList(Iterators.transform(modelList.iterator(), ColumnConfigConverter::model2DtoForActivity));
    }
}