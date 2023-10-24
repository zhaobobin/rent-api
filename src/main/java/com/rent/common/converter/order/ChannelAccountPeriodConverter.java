package com.rent.common.converter.order;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.rent.common.dto.order.ChannelAccountPeriodDto;
import com.rent.model.order.ChannelAccountPeriod;

import java.util.ArrayList;
import java.util.List;

/**
 * 账单表Service
 * @author xiaoyao
 */
public class ChannelAccountPeriodConverter {

    /**
     * model转dto
     * @param model
     * @return
     */
    public static ChannelAccountPeriodDto model2Dto(ChannelAccountPeriod model) {
        if (model == null) {
            return null;
        }
        ChannelAccountPeriodDto dto = new ChannelAccountPeriodDto();
        dto.setId(model.getId());
        dto.setMarketingChannelName(model.getMarketingChannelName());
        dto.setStatus(model.getStatus());
        dto.setTotalAmount(model.getTotalAmount());
        dto.setTotalBrokerage(model.getTotalBrokerage());
        dto.setTotalSettleAmount(model.getSettleAmount());
        dto.setSettleDate(model.getSettleDate());
        return dto;
    }
    /**
     * modelList转dtoList
     * @param modelList
     * @return
     */
    public static List<ChannelAccountPeriodDto> modelList2DtoList(List<ChannelAccountPeriod> modelList) {
        if (CollectionUtil.isEmpty(modelList)) {
            return new ArrayList<>();
        }
        return Lists.newArrayList(Iterators.transform(modelList.iterator(), ChannelAccountPeriodConverter::model2Dto));
    }
}