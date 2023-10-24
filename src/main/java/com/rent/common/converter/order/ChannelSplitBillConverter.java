        
package com.rent.common.converter.order;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.rent.common.dto.order.ChannelSplitBillRentDto;
import com.rent.model.order.ChannelSplitBill;

import java.util.ArrayList;
import java.util.List;


/**
 * 分账信息Service
 *
 * @author zhao
 * @Date 2020-08-11 09:59
 */
public class ChannelSplitBillConverter {

    /**
     * model转dto
     * @param model
     * @return
     */
    public static ChannelSplitBillRentDto model2RentDto(ChannelSplitBill model) {
        if (model == null) {
            return null;
        }
        ChannelSplitBillRentDto dto = new ChannelSplitBillRentDto();
        dto.setId(model.getId());
        dto.setOrderId(model.getOrderId());
        dto.setPeriod(model.getPeriod());
        dto.setScale(model.getScale());
        dto.setUserPayAmount(model.getUserPayAmount());
        dto.setToChannelAmount(model.getChannelAmount());
        dto.setToOpeAmount(model.getUserPayAmount().subtract(model.getChannelAmount()));
        dto.setStatus(model.getStatus());
        dto.setUserPayTime(model.getUserPayTime());
        dto.setCreateTime(model.getCreateTime());
        dto.setUpdateTime(model.getUpdateTime());
        return dto;
    }

    /**
     * modelList转dtoList
     *
     * @param modelList
     * @return
     */
    public static List<ChannelSplitBillRentDto> modelList2RentDtoList(List<ChannelSplitBill> modelList) {
        if (CollectionUtil.isEmpty(modelList)) {
            return new ArrayList<>();
        }
        return Lists.newArrayList(Iterators.transform(modelList.iterator(), ChannelSplitBillConverter::model2RentDto));
    }

}