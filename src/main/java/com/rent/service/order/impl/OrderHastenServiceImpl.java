package com.rent.service.order.impl;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.converter.order.OrderHastenConverter;
import com.rent.common.dto.order.OrderHastenDto;
import com.rent.common.dto.order.OrderHastenReqDto;
import com.rent.dao.order.OrderHastenDao;
import com.rent.model.order.OrderHasten;
import com.rent.service.order.OrderHastenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 催收记录Service
 *
 * @author
 * @Date
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class OrderHastenServiceImpl implements OrderHastenService {

    private final OrderHastenDao orderHastenDao;

    @Override
    public Long addOrderHasten(OrderHastenDto request) {
        OrderHasten model  = OrderHastenConverter.dto2Model(request);
        if(orderHastenDao.save(model)){
            return model.getId();
        }
        else{
            throw new MybatisPlusException("保存数据失败");
        }
    }

    @Override
    public Page<OrderHastenDto> queryOrderHastenPage(OrderHastenReqDto request) {
        Page<OrderHasten> page = orderHastenDao.pageByOrderId(new Page<>(request.getPageNumber(), request.getPageSize()),
                request.getOrderId(),
                request.getSource());
        return new Page<OrderHastenDto>(page.getCurrent(), page.getSize(), page.getTotal()).setRecords(
                OrderHastenConverter.modelList2DtoList(page.getRecords()));
    }

}