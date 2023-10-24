package com.rent.service.order;

import com.rent.common.dto.order.UserOrdersStatusTransferDto;

import java.util.List;

/**
 * @author xiaoyao
 * @version V1.0
 * @date 2020-10-26 下午 4:31:05
 * @since 1.0
 */
public interface UserOrdersStatusTransferService {

    /**
     * 根据订单号查询订单流转信息
     * @param orderId 订单编号
     * @return
     */
    List<UserOrdersStatusTransferDto> queryRecordByOrderId(String orderId);

}
