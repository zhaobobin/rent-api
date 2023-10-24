package com.rent.common.converter.order;

import com.rent.common.dto.order.OrderAddressDto;
import com.rent.common.dto.user.UserAddressDto;
import com.rent.model.order.OrderAddress;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.Map;

/**
 * 订单地址Service
 *
 * @author xiaoyao
 * @Date 2020-06-16 10:09
 */
@Slf4j
public class OrderAddressConverter {

    /**
     * model转dto
     *
     * @param model
     * @return
     */
    public static OrderAddressDto model2Dto(OrderAddress model, Map<String, String> distinctNameMap) {
        if (model == null) {
            return null;
        }
        OrderAddressDto dto = new OrderAddressDto();
        dto.setId(model.getId());
        dto.setCreateTime(model.getCreateTime());
        dto.setUpdateTime(model.getUpdateTime());
        dto.setDeleteTime(model.getDeleteTime());
        dto.setProvince(model.getProvince());
        dto.setCity(model.getCity());
        dto.setUid(model.getUid());
        dto.setArea(model.getArea());
        dto.setStreet(model.getStreet());
        dto.setZcode(model.getZcode());
        dto.setTelephone(model.getTelephone());
        dto.setRealname(model.getRealname());
        dto.setOrderId(model.getOrderId());
        dto.setProvinceStr(distinctNameMap.get(model.getProvince().toString()));
        dto.setCityStr(distinctNameMap.get(model.getCity().toString()));
        dto.setAreaStr(distinctNameMap.get(model.getArea().toString()));
        return dto;
    }

    public static OrderAddressDto model2Dto(OrderAddress model) {
        if (model == null) {
            return null;
        }
        OrderAddressDto dto = new OrderAddressDto();
        dto.setId(model.getId());
        dto.setCreateTime(model.getCreateTime());
        dto.setUpdateTime(model.getUpdateTime());
        dto.setDeleteTime(model.getDeleteTime());
        dto.setProvince(model.getProvince());
        dto.setCity(model.getCity());
        dto.setUid(model.getUid());
        dto.setArea(model.getArea());
        dto.setStreet(model.getStreet());
        dto.setZcode(model.getZcode());
        dto.setTelephone(model.getTelephone());
        dto.setRealname(model.getRealname());
        dto.setOrderId(model.getOrderId());
        return dto;
    }



    public static OrderAddress userAddress2Model(Date now, String orderId, UserAddressDto userAddress) {
        if (null == userAddress) {
            return null;
        }
        OrderAddress orderAddress = new OrderAddress();
        orderAddress.setArea(userAddress.getArea());
        orderAddress.setCity(userAddress.getCity());
        orderAddress.setOrderId(orderId);
        orderAddress.setCreateTime(now);
        orderAddress.setTelephone(userAddress.getTelephone());
        orderAddress.setRealname(userAddress.getRealname());
        orderAddress.setStreet(userAddress.getStreet());
        orderAddress.setProvince(userAddress.getProvince());
        orderAddress.setZcode(userAddress.getZcode());
        orderAddress.setUid(userAddress.getUid());

        return orderAddress;

    }
}