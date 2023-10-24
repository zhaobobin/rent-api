package com.rent.service.order;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.dto.order.OrderHastenDto;
import com.rent.common.dto.order.OrderHastenReqDto;

/**
 * 催收记录Service
 *
 * @author
 * @Date
 */
public interface OrderHastenService {


    /**
     * 添加一条催收记录
     * @param request
     * @return
     */
    Long addOrderHasten(OrderHastenDto request);

    /**
     * 分页查询催收记录
     * @param request
     * @return
     */
    Page<OrderHastenDto> queryOrderHastenPage(OrderHastenReqDto request);

}