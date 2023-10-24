        
package com.rent.common.converter.product;

import cn.hutool.core.collection.CollectionUtil;
import com.rent.common.dto.product.ChannelSplitBillDto;
import com.rent.model.product.MarketingChannel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * 渠道账号分佣表
 *
 * @author xiaotong
 * @Date 2020-06-17 10:49
 */
public class ChannelSplitBillConverter {



    public static List<ChannelSplitBillDto> models2ListDtoList(List<MarketingChannel> modelList) {
        if(CollectionUtil.isEmpty(modelList)){
            return Collections.emptyList();
        }
        List<ChannelSplitBillDto> listDtoList = new ArrayList<>(modelList.size());
        for (MarketingChannel model : modelList) {
            ChannelSplitBillDto dto = new ChannelSplitBillDto();
            dto.setId(model.getId());
            dto.setUid(model.getUid());
            dto.setName(model.getName());
            dto.setScale(model.getScale());
            dto.setStatus(model.getStatus());
            dto.setAccount(model.getAccount());
            dto.setIdentity(model.getIdentity());
            dto.setAliName(model.getAliName());
            dto.setAuditTime(model.getAuditTime());
            dto.setCreateTime(model.getCreateTime());
            dto.setUpdateTime(model.getUpdateTime());
            listDtoList.add(dto);
        }
        return listDtoList;
    }

    /**
     * model转dto
     *
     * @param model
     * @return
     */
    public static ChannelSplitBillDto model2Dto(MarketingChannel model) {
        if (model == null) {
            return null;
        }
        ChannelSplitBillDto dto = new ChannelSplitBillDto();
        dto.setId(model.getId());
        dto.setUid(model.getUid());
        dto.setUid(model.getUid());
        dto.setName(model.getName());
        dto.setScale(model.getScale());
        dto.setStatus(model.getStatus());
        dto.setAccount(model.getAccount());
        dto.setIdentity(model.getIdentity());
        dto.setAliName(model.getAliName());
        dto.setAuditTime(model.getAuditTime());
        dto.setCreateTime(model.getCreateTime());
        dto.setUpdateTime(model.getUpdateTime());
        dto.setAddUser(model.getAddUser());
        dto.setAuditUser(model.getAuditUser());
        return dto;
    }

    /**
     * dto转do
     *
     * @param dto
     * @return
     */
    public static MarketingChannel dto2Model(ChannelSplitBillDto dto) {
        if (dto == null) {
            return null;
        }
        MarketingChannel model = new MarketingChannel();
        model.setId(dto.getId());
        model.setUid(dto.getUid());
        model.setName(dto.getName());
        model.setScale(dto.getScale());
        model.setStatus(dto.getStatus());
        model.setAccount(dto.getAccount());
        model.setIdentity(dto.getIdentity());
        model.setAliName(dto.getAliName());
        model.setAuditTime(dto.getAuditTime());
        model.setCreateTime(dto.getCreateTime());
        model.setUpdateTime(dto.getUpdateTime());
        model.setAddUser(dto.getAddUser());
        model.setAuditUser(dto.getAuditUser());
        return model;
    }
}