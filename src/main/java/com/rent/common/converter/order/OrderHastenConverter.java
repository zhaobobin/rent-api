package com.rent.common.converter.order;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.rent.common.dto.order.OrderHastenDto;
import com.rent.model.order.OrderHasten;

import java.util.ArrayList;
import java.util.List;

/**
 * 订单备注Service
 *
 * @author xiaoyao
 * @Date 2020-06-16 10:09
 */
public class OrderHastenConverter {

    /**
     * model转dto
     * @param model
     * @return
     */
    public static OrderHastenDto model2Dto(OrderHasten model) {
        if (model == null) {
            return null;
        }
        OrderHastenDto dto = new OrderHastenDto();
        dto.setId(model.getId());
        dto.setSource(model.getSource());
        dto.setOrderId(model.getOrderId());
        dto.setUserName(model.getUserName());
        dto.setResult(model.getResult());
        dto.setNotes(model.getNotes());
        dto.setCreateTime(model.getCreateTime());
        dto.setDeleteTime(model.getDeleteTime());
        return dto;
    }

    /**
     * dto转do
     * @param dto
     * @return
     */
    public static OrderHasten dto2Model(OrderHastenDto dto) {
        if (dto == null) {
            return null;
        }
        OrderHasten model = new OrderHasten();
        model.setId(dto.getId());
        model.setSource(dto.getSource());
        model.setOrderId(dto.getOrderId());
        model.setUserName(dto.getUserName());
        model.setResult(dto.getResult());
        model.setNotes(dto.getNotes());
        model.setCreateTime(dto.getCreateTime());
        model.setDeleteTime(dto.getDeleteTime());
        return model;
    }

    /**
     * modelList转dtoList
     * @param modelList
     * @return
     */
    public static List<OrderHastenDto> modelList2DtoList(List<OrderHasten> modelList) {
        if (CollectionUtil.isEmpty(modelList)) {
            return new ArrayList<>();
        }
        return Lists.newArrayList(Iterators.transform(modelList.iterator(), OrderHastenConverter::model2Dto));
    }
}