        
package com.rent.common.converter.marketing;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.rent.common.dto.marketing.CubesConfigListDto;
import com.rent.model.marketing.CubesConfig;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * @author xiaotong
 */
public class CubesConfigConverter {

    public static CubesConfigListDto model2Dto(CubesConfig model){
        if(model == null){
            return  null;
        }
        CubesConfigListDto dto = new CubesConfigListDto();
        dto.setId(model.getId());
        dto.setPaperwork(model.getPaperwork());
        dto.setPaperworkCopy(model.getPaperworkCopy());
        dto.setProductIds(Arrays.asList(model.getProductIds().split(",")));
        dto.setUrl(model.getUrl());
        dto.setJumpUrl(model.getJumpUrl());
        return dto;
    }

    public static List<CubesConfigListDto> modelList2DtoList(List<CubesConfig> modelList) {
        if (CollectionUtil.isEmpty(modelList)) {
            return new ArrayList<>(0);
        }
        return Lists.newArrayList(Iterators.transform(modelList.iterator(), CubesConfigConverter::model2Dto));
    }

}