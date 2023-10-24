        
package com.rent.common.converter.product;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.rent.common.dto.product.ChannelStoreDto;
import com.rent.model.product.ChannelStore;

import java.util.ArrayList;
import java.util.List;


/**
 * 渠道账号分佣表
 *
 * @author xiaotong
 * @Date 2020-06-17 10:49
 */
public class ChannelStoreConverter {
    /**
     * model转dto
     *
     * @param model
     * @return
     */
    public static ChannelStoreDto model2Dto(ChannelStore model) {
        if (model == null) {
            return null;
        }
        ChannelStoreDto dto = new ChannelStoreDto();
        dto.setId(model.getId());
        dto.setLink(model.getLink());
        dto.setMarketingId(model.getMarketingId());
        dto.setChannelSplitId(model.getMarketingChannelId());
        dto.setCreateTime(model.getCreateTime());
        dto.setDeleteTime(model.getDeleteTime());

        dto.setShopName(model.getShopName());
        dto.setTelephone(model.getTelephone());
        dto.setRealName(model.getRealName());
        dto.setUid(model.getUid());
        dto.setExpireDay(model.getExpireDay());
        dto.setExpireSwitch(model.getExpireSwitch());
        return dto;
    }

    /**
     * dto转do
     *
     * @param dto
     * @return
     */
    public static ChannelStore dto2Model(ChannelStoreDto dto) {
        if (dto == null) {
            return null;
        }
        ChannelStore model = new ChannelStore();
        model.setId(dto.getId());
        model.setLink(dto.getLink());
        model.setMarketingId(dto.getMarketingId());
        model.setMarketingChannelId(dto.getChannelSplitId());
        model.setCreateTime(dto.getCreateTime());
        model.setDeleteTime(dto.getDeleteTime());
        model.setShopName(dto.getShopName());
        model.setTelephone(dto.getTelephone());
        model.setRealName(dto.getRealName());
        model.setUid(dto.getUid());
        model.setExpireDay(dto.getExpireDay());
        model.setExpireSwitch(dto.getExpireSwitch());
        return model;
    }

    /**
     * modelList转dtoList
     *
     * @param modelList
     * @return
     */
    public static List<ChannelStoreDto> modelList2DtoList(List<ChannelStore> modelList) {
        if (CollectionUtil.isEmpty(modelList)) {
            return new ArrayList<>(0);
        }
        return Lists.newArrayList(Iterators.transform(modelList.iterator(), ChannelStoreConverter::model2Dto));
    }
}