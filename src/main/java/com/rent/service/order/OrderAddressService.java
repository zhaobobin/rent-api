        
package com.rent.service.order;

import com.rent.common.dto.order.resquest.UserOrderAddressModifyRequest;

public interface OrderAddressService {


    /**
     * 修改订单地址
     * @param request
     */
    void orderAddressModifyWithoutCheck(UserOrderAddressModifyRequest request);

    /**
     * 修改订单地址
     * @param request
     */
    void orderAddressModify(UserOrderAddressModifyRequest request);
}