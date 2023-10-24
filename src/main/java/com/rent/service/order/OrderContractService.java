package com.rent.service.order;

import com.rent.common.dto.components.dto.OrderContractDto;

public interface OrderContractService {

    /**
     * 获取订单合同内容信息
     * @param orderId 订单id
     * @return
     */
    OrderContractDto getOrderContractInfo(String orderId);

    /**
     * 生成订单合同
     * @param orderId 订单id
     * @return
     */
    String signOrderContract(String orderId);

    /**
     * 生成订单合同-不盖章
     * @param orderId 订单id
     * @return
     */
    String generateOrderContractUnsignedFile(String orderId);

}
