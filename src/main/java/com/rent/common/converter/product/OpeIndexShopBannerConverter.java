        
package com.rent.common.converter.product;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.rent.common.dto.product.OpeIndexShopBannerDto;
import com.rent.model.product.OpeIndexShopBanner;

import java.util.ArrayList;
import java.util.List;


/**
 * 商家详情轮播配置Service
 *
 * @author youruo
 * @Date 2020-07-23 17:37
 */
public class OpeIndexShopBannerConverter {

    /**
     * model转dto
     *
     * @param model
     * @return
     */
    public static OpeIndexShopBannerDto model2Dto(OpeIndexShopBanner model) {
        if (model == null) {
            return null;
        }
        OpeIndexShopBannerDto dto = new OpeIndexShopBannerDto();
        dto.setId(model.getId());
        dto.setCreateTime(model.getCreateTime());
        dto.setUpdateTime(model.getUpdateTime());
        dto.setDeleteTime(model.getDeleteTime());
        dto.setOnlineTime(model.getOnlineTime());
        dto.setShopId(model.getShopId());
        dto.setName(model.getName());
        dto.setImgSrc(model.getImgSrc());
        dto.setJumpUrl(model.getJumpUrl());
        dto.setStatus(model.getStatus());
        dto.setIndexSort(model.getIndexSort());
        return dto;
    }

    /**
     * dto转do
     *
     * @param dto
     * @return
     */
    public static OpeIndexShopBanner dto2Model(OpeIndexShopBannerDto dto) {
        if (dto == null) {
            return null;
        }
        OpeIndexShopBanner model = new OpeIndexShopBanner();
        model.setId(dto.getId());
        model.setCreateTime(dto.getCreateTime());
        model.setUpdateTime(dto.getUpdateTime());
        model.setDeleteTime(dto.getDeleteTime());
        model.setOnlineTime(dto.getOnlineTime());
        model.setShopId(dto.getShopId());
        model.setName(dto.getName());
        model.setImgSrc(dto.getImgSrc());
        model.setJumpUrl(dto.getJumpUrl());
        model.setStatus(dto.getStatus());
        model.setIndexSort(dto.getIndexSort());
        return model;
    }

    /**
     * modelList转dtoList
     *
     * @param modelList
     * @return
     */
    public static List<OpeIndexShopBannerDto> modelList2DtoList(List<OpeIndexShopBanner> modelList) {
        if (CollectionUtil.isEmpty(modelList)) {
            return new ArrayList<>(0);
        }
        return Lists.newArrayList(Iterators.transform(modelList.iterator(), OpeIndexShopBannerConverter::model2Dto));
    }
}