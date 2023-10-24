        
package com.rent.common.converter.marketing;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.rent.common.dto.marketing.BannerConfigListDto;
import com.rent.model.marketing.BannerConfig;

import java.util.ArrayList;
import java.util.List;


/**
 * @author xiaotong
 */
public class BannerConfigConverter {

    public static BannerConfigListDto model2Dto(BannerConfig model){
        if(model == null){
            return  null;
        }
        BannerConfigListDto dto = new BannerConfigListDto();
        dto.setId(model.getId());
        dto.setBeginTime(model.getBeginTime());
        dto.setEndTime(model.getEndTime());
        dto.setUrl(model.getUrl());
        dto.setJumpUrl(model.getJumpUrl());
        dto.setSort(model.getSort());
        dto.setOpenStatus(model.getOpenStatus());
        dto.setCreateTime(model.getCreateTime());
        return dto;
    }

    public static List<BannerConfigListDto> modelList2DtoList(List<BannerConfig> modelList) {
        if (CollectionUtil.isEmpty(modelList)) {
            return new ArrayList<>(0);
        }
        return Lists.newArrayList(Iterators.transform(modelList.iterator(), BannerConfigConverter::model2Dto));
    }

}