        
package com.rent.common.converter.order;

import com.rent.common.dto.order.OrderLocationAddressDto;
import com.rent.model.order.OrderLocationAddress;

/**
 * 订单当前位置定位表Service
 *
 * @author youruo
 * @Date 2021-01-14 15:15
 */
public class OrderLocationAddressConverter {

    /**
     * model转dto
     * @param model
     * @return
     */
    public static OrderLocationAddressDto model2Dto(OrderLocationAddress model) {
        if (model == null) {
            return null;
        }
        OrderLocationAddressDto dto = new OrderLocationAddressDto();
        dto.setId(model.getId());
        dto.setCreateTime(model.getCreateTime());
        dto.setUpdateTime(model.getUpdateTime());
        dto.setDeleteTime(model.getDeleteTime());
        dto.setOrderId(model.getOrderId());
        dto.setLongitude(model.getLongitude());
        dto.setLatitude(model.getLatitude());
        dto.setAccuracy(model.getAccuracy());
        dto.setHorizontalAccuracy(model.getHorizontalAccuracy());
        dto.setCountry(model.getCountry());
        dto.setCountryCode(model.getCountryCode());
        dto.setProvince(model.getProvince());
        dto.setCity(model.getCity());
        dto.setCityAdcode(model.getCityAdcode());
        dto.setDistrict(model.getDistrict());
        dto.setDistrictAdcode(model.getDistrictAdcode());
        dto.setStreetNumber(model.getStreetNumber());
        dto.setPois(model.getPois());
        return dto;
    }

    /**
     * dto转do
     * @param dto
     * @return
     */
    public static OrderLocationAddress dto2Model(OrderLocationAddressDto dto) {
        if (dto == null) {
            return null;
        }
        OrderLocationAddress model = new OrderLocationAddress();
        model.setId(dto.getId());
        model.setCreateTime(dto.getCreateTime());
        model.setUpdateTime(dto.getUpdateTime());
        model.setDeleteTime(dto.getDeleteTime());
        model.setOrderId(dto.getOrderId());
        model.setLongitude(dto.getLongitude());
        model.setLatitude(dto.getLatitude());
        model.setAccuracy(dto.getAccuracy());
        model.setHorizontalAccuracy(dto.getHorizontalAccuracy());
        model.setCountry(dto.getCountry());
        model.setCountryCode(dto.getCountryCode());
        model.setProvince(dto.getProvince());
        model.setCity(dto.getCity());
        model.setCityAdcode(dto.getCityAdcode());
        model.setDistrict(dto.getDistrict());
        model.setDistrictAdcode(dto.getDistrictAdcode());
        model.setStreetNumber(dto.getStreetNumber());
        model.setPois(dto.getPois());
        return model;
    }
}