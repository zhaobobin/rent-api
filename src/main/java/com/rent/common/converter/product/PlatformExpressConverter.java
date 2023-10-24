        
package com.rent.common.converter.product;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.rent.common.dto.product.PlatformExpressDto;
import com.rent.common.dto.vo.ApiPlatformExpressVo;
import com.rent.model.product.PlatformExpress;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * 平台物流表Service
 *
 * @author youruo
 * @Date 2020-06-16 11:46
 */
public class PlatformExpressConverter {

    /**
     * model转dto
     *
     * @param model
     * @return
     */
    public static PlatformExpressDto model2Dto(PlatformExpress model) {
        if (model == null) {
            return null;
        }
        PlatformExpressDto dto = new PlatformExpressDto();
        dto.setId(model.getId());
        dto.setCreateTime(model.getCreateTime());
        dto.setUpdateTime(model.getUpdateTime());
        dto.setDeleteTime(model.getDeleteTime());
        dto.setName(model.getName());
        dto.setLogo(model.getLogo());
        dto.setShortName(model.getShortName());
        dto.setTelephone(model.getTelephone());
        dto.setIndex(model.getIndex());
        dto.setAliCode(model.getAliCode());
        return dto;
    }


    /**
     * modelList转dtoList
     *
     * @param modelList
     * @return
     */
    public static List<PlatformExpressDto> modelList2DtoList(List<PlatformExpress> modelList) {
        if (CollectionUtil.isEmpty(modelList)) {
            return new ArrayList<>(0);
        }
        return Lists.newArrayList(Iterators.transform(modelList.iterator(), PlatformExpressConverter::model2Dto));
    }



    public static List<ApiPlatformExpressVo> modelList2ApiVo(List<PlatformExpress> platformExpress) {
        if (CollectionUtil.isEmpty(platformExpress)) {
            return Collections.EMPTY_LIST;
        }
        List<ApiPlatformExpressVo> voList = new ArrayList<>(platformExpress.size());

        for (PlatformExpress express : platformExpress) {
            ApiPlatformExpressVo vo = new ApiPlatformExpressVo();
            vo.setName(express.getName());
            vo.setId(express.getId());
            voList.add(vo);
        }
        return voList;
    }
}