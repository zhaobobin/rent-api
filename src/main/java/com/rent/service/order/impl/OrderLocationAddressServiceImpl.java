
package com.rent.service.order.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.rent.common.converter.order.OrderLocationAddressConverter;
import com.rent.common.dto.order.OrderLocationAddressDto;
import com.rent.dao.order.OrderLocationAddressDao;
import com.rent.model.order.OrderLocationAddress;
import com.rent.service.order.OrderLocationAddressService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 订单当前位置定位表Service
 *
 * @author youruo
 * @Date 2021-01-14 15:15
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class OrderLocationAddressServiceImpl implements OrderLocationAddressService {

    private final OrderLocationAddressDao orderLocationAddressDao;

    @Override
    public Boolean addOrderLocationAddress(OrderLocationAddressDto request) {
        if (null != request && StringUtils.isNotEmpty(request.getLatitude()) && StringUtils.isNotEmpty(request.getLongitude())) {
            OrderLocationAddress model = OrderLocationAddressConverter.dto2Model(request);
            Date now = new Date();
            model.setCreateTime(now);
            model.setUpdateTime(now);
            if (orderLocationAddressDao.save(model)) {
                return Boolean.TRUE;
            } else {
                throw new MybatisPlusException("保存数据失败");
            }
        } else {
            return Boolean.FALSE;
        }
    }

    @Override
    public OrderLocationAddressDto getOrderLocationAddress(String orderId) {
        if(StringUtils.isNotEmpty(orderId)){
            OrderLocationAddress orderLocationAddress = orderLocationAddressDao.getOne(new QueryWrapper<OrderLocationAddress>()
                    .eq("order_id",orderId)
                    .orderByDesc("id")
                    .last("limit 1"));
            return OrderLocationAddressConverter.model2Dto(orderLocationAddress);
        }else{
            return null;
        }
    }

}