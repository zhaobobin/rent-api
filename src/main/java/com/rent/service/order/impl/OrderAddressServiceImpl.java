package com.rent.service.order.impl;

import com.rent.common.dto.order.resquest.UserOrderAddressModifyRequest;
import com.rent.common.enums.order.EnumOrderStatus;
import com.rent.common.util.OrderCheckUtil;
import com.rent.dao.order.OrderAddressDao;
import com.rent.dao.order.UserOrdersDao;
import com.rent.model.order.OrderAddress;
import com.rent.model.order.UserOrders;
import com.rent.service.order.OrderAddressService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
@Slf4j
@RequiredArgsConstructor
public class OrderAddressServiceImpl implements OrderAddressService {

    private final OrderAddressDao orderAddressDao;
    private final UserOrdersDao userOrdersDao;

    @Override
    public void orderAddressModifyWithoutCheck(UserOrderAddressModifyRequest request) {
        OrderAddress orderAddress = orderAddressDao.queryByOrderId(request.getOrderId());
        orderAddress.setUpdateTime(new Date());
        orderAddress.setProvince(request.getProvince());
        orderAddress.setCity(request.getCity());
        orderAddress.setArea(request.getArea());
        orderAddress.setStreet(request.getStreet());
        orderAddress.setZcode(request.getZcode());
        orderAddress.setTelephone(request.getTelephone());
        orderAddress.setRealname(request.getRealName());
        orderAddressDao.updateById(orderAddress);
    }

    @Override
    public void orderAddressModify(UserOrderAddressModifyRequest request) {
        UserOrders userOrders = userOrdersDao.selectOneByOrderId(request.getOrderId());
        OrderCheckUtil.checkUserOrdersStatus(userOrders, EnumOrderStatus.WAITING_PAYMENT, "修改收货地址");
        orderAddressModifyWithoutCheck(request);
    }
}
