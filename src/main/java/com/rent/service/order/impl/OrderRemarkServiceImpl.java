package com.rent.service.order.impl;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.converter.order.OrderRemarkConverter;
import com.rent.common.dto.order.OrderRemarkDto;
import com.rent.common.dto.order.resquest.OrderRemarkReqDto;
import com.rent.common.enums.order.EnumOrderRemarkSource;
import com.rent.dao.order.OrderRemarkDao;
import com.rent.dao.order.UserOrdersDao;
import com.rent.model.order.OrderRemark;
import com.rent.model.order.UserOrders;
import com.rent.service.order.OrderRemarkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 订单备注Service
 *
 * @author xiaoyao
 * @Date 2020-06-16 10:09
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class OrderRemarkServiceImpl implements OrderRemarkService {

    private final OrderRemarkDao orderRemarkDao;
    private final UserOrdersDao userOrdersDao;

    @Override
    public Long addOrderRemark(OrderRemarkDto request) {
        OrderRemark model = OrderRemarkConverter.dto2Model(request);
        if (orderRemarkDao.save(model)) {
            return model.getId();
        } else {
            throw new MybatisPlusException("保存数据失败");
        }
    }

    @Override
    public Page<OrderRemarkDto> queryOrderRemarkPage(OrderRemarkReqDto request) {
        Page<OrderRemark> page = orderRemarkDao
                .pageByOrderId(new Page<>(request.getPageNumber(), request.getPageSize()),
                        request.getOrderId(),
                        request.getSource());
        return new Page<OrderRemarkDto>(page.getCurrent(), page.getSize(), page.getTotal()).setRecords(
            OrderRemarkConverter.modelList2DtoList(page.getRecords()));
    }

    @Override
    public void orderRemark(String orderId, String remark, String userName, EnumOrderRemarkSource source) {
        UserOrders userOrders = userOrdersDao.selectOneByOrderId(orderId);
        OrderRemark orderRemark = new OrderRemark();
        orderRemark.setOrderId(orderId);
        orderRemark.setSource(source);
        orderRemark.setUserName(userName);
        orderRemark.setRemark(remark);
        orderRemark.setCreateTime(new Date());
        orderRemark.setOrderType(userOrders.getType());
        orderRemarkDao.save(orderRemark);
    }

}