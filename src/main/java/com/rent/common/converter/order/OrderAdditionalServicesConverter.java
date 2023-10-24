package com.rent.common.converter.order;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.rent.common.dto.order.OrderAdditionalServicesDto;
import com.rent.common.dto.product.ShopAdditionalServicesDto;
import com.rent.model.order.OrderAdditionalServices;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 订单增值服务信息Service
 *
 * @author xiaoyao
 * @Date 2020-06-16 10:08
 */
public class OrderAdditionalServicesConverter {

    /**
     * model转dto
     *
     * @param model
     * @return
     */
    public static OrderAdditionalServicesDto model2Dto(OrderAdditionalServices model) {
        if (model == null) {
            return null;
        }
        OrderAdditionalServicesDto dto = new OrderAdditionalServicesDto();
        dto.setShopAdditionalServicesId(model.getShopAdditionalServicesId());
        dto.setPrice(model.getPrice());
        return dto;
    }


    /**
     * modelList转dtoList
     *
     * @param modelList
     * @return
     */
    public static List<OrderAdditionalServicesDto> modelList2DtoList(List<OrderAdditionalServices> modelList) {
        if (CollectionUtil.isEmpty(modelList)) {
            return new ArrayList<>();
        }
        return Lists.newArrayList(
            Iterators.transform(modelList.iterator(), OrderAdditionalServicesConverter::model2Dto));
    }

    /**
     * 增值服务列表转换为订单增值服务对象
     *
     * @param now 当前时间
     * @param orderId 订单编号
     * @param additionalServicesList 增值服务列表
     * @return 编号
     */
    public static List<OrderAdditionalServices> additionalServices2Model(Date now, String orderId,
        List<ShopAdditionalServicesDto> additionalServicesList) {
        List<OrderAdditionalServices> orderAdditionalServicesList = Lists.newArrayList();
        if (CollectionUtil.isEmpty(additionalServicesList)) {
            return orderAdditionalServicesList;
        }
        additionalServicesList.forEach(additionalServices -> {
            OrderAdditionalServices orderAdditionalServices = new OrderAdditionalServices();
            orderAdditionalServices.setCreateTime(now);
            orderAdditionalServices.setUpdateTime(now);
            orderAdditionalServices.setOrderId(orderId);
            orderAdditionalServices.setShopAdditionalServicesId(additionalServices.getId());
            orderAdditionalServices.setPrice(additionalServices.getPrice());
            orderAdditionalServicesList.add(orderAdditionalServices);
        });
        return orderAdditionalServicesList;
    }
}