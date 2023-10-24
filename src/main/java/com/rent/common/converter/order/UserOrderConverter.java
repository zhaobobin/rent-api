package com.rent.common.converter.order;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.rent.common.dto.order.HotRentDto;
import com.rent.model.order.UserOrders;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户订单分期Service
 *
 * @author xiaoyao
 * @Date 2020-06-11 17:25
 */
public class UserOrderConverter {

    /**
     * model转dto
     *
     * @param model
     * @return
     */
    public static HotRentDto model2HotRentDto(UserOrders model) {
        if (model == null) {
            return null;
        }
        HotRentDto dto = new HotRentDto();
        dto.setShopId(model.getShopId());
        dto.setOrderId(model.getOrderId());
        dto.setUid(model.getUid());
        dto.setProductId(model.getProductId());
        return dto;
    }

    /**
     * modelList转dtoList
     *
     * @param modelList
     * @return
     */
    public static List<HotRentDto> modelList2HotRentDtoList(List<UserOrders> modelList) {
        if (CollectionUtil.isEmpty(modelList)) {
            return new ArrayList<>();
        }
        return Lists.newArrayList(Iterators.transform(modelList.iterator(), UserOrderConverter::model2HotRentDto));
    }

}