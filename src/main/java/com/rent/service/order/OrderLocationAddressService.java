
package com.rent.service.order;

import com.rent.common.dto.order.OrderLocationAddressDto;

/**
 * 订单当前位置定位表Service
 *
 * @author youruo
 * @Date 2021-01-14 15:15
 */
public interface OrderLocationAddressService {

    /**
     * 新增订单当前位置定位表
     *
     * @param request 条件
     * @return boolean
     */
    Boolean addOrderLocationAddress(OrderLocationAddressDto request);


    OrderLocationAddressDto getOrderLocationAddress(String orderId);


}