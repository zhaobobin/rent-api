package com.rent.common.converter.order;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.rent.common.dto.order.ChannelUserOrdersDto;
import com.rent.model.order.ChannelUserOrders;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户订单Service
 *
 * @author xiaoyao
 * @Date 2020-06-10 17:02
 */
public class ChannelUserOrdersConverter {

    /**
     * model转dto
     * @param model
     * @return
     */
    public static ChannelUserOrdersDto model2Dto(ChannelUserOrders model) {
        if (model == null) {
            return null;
        }
        ChannelUserOrdersDto dto = new ChannelUserOrdersDto();
        dto.setId(model.getId());
        dto.setCreateTime(model.getCreateTime());
        dto.setOrderId(model.getOrderId());
        dto.setUserName(model.getUserName());
        dto.setTotalPeriods(model.getTotalPeriods());
        dto.setPayRent(model.getTotalAmount());
        dto.setTelephone(model.getPhone());
        dto.setProductName(model.getProductName());
        dto.setShopName(model.getShopName());
        dto.setSettleStatus(model.getSettleStatus());
        dto.setChannelId(model.getMarketingChannelId());
        return dto;
    }

    /**
     * modelList转dtoList
     *
     * @param modelList
     * @return
     */
    public static List<ChannelUserOrdersDto> modelList2DtoList(List<ChannelUserOrders> modelList) {
        if (CollectionUtil.isEmpty(modelList)) {
            return new ArrayList<>();
        }
        return Lists.newArrayList(Iterators.transform(modelList.iterator(), ChannelUserOrdersConverter::model2Dto));
    }
}