package com.rent.common.converter.order;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.rent.common.dto.order.OrderRemarkDto;
import com.rent.model.order.OrderRemark;

import java.util.ArrayList;
import java.util.List;

/**
 * 订单备注Service
 *
 * @author xiaoyao
 * @Date 2020-06-16 10:09
 */
public class OrderRemarkConverter {

    /**
     * model转dto
     *
     * @param model
     * @return
     */
    public static OrderRemarkDto model2Dto(OrderRemark model) {
        if (model == null) {
            return null;
        }
        OrderRemarkDto dto = new OrderRemarkDto();
        dto.setId(model.getId());
        dto.setSource(model.getSource());
        dto.setOrderId(model.getOrderId());
        dto.setOrderType(model.getOrderType());
        dto.setUserName(model.getUserName());
        dto.setRemark(model.getRemark());
        dto.setCreateTime(model.getCreateTime());
        dto.setDeleteTime(model.getDeleteTime());
        return dto;
    }

    /**
     * dto转do
     *
     * @param dto
     * @return
     */
    public static OrderRemark dto2Model(OrderRemarkDto dto) {
        if (dto == null) {
            return null;
        }
        OrderRemark model = new OrderRemark();
        model.setId(dto.getId());
        model.setSource(dto.getSource());
        model.setOrderType(dto.getOrderType());
        model.setOrderId(dto.getOrderId());
        model.setUserName(dto.getUserName());
        model.setRemark(dto.getRemark());
        model.setCreateTime(dto.getCreateTime());
        model.setDeleteTime(dto.getDeleteTime());
        return model;
    }

    /**
     * modelList转dtoList
     *
     * @param modelList
     * @return
     */
    public static List<OrderRemarkDto> modelList2DtoList(List<OrderRemark> modelList) {
        if (CollectionUtil.isEmpty(modelList)) {
            return new ArrayList<>();
        }
        return Lists.newArrayList(Iterators.transform(modelList.iterator(), OrderRemarkConverter::model2Dto));
    }
}