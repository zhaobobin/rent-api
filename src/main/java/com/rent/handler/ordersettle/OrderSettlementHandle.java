package com.rent.handler.ordersettle;

import com.rent.handler.ordersettle.bean.OrderSettlementBean;

/**
 * @author xiaoyao
 * @version V1.0
 * @date 2020-6-19 13:39
 * @since 1.0
 */
public interface OrderSettlementHandle {

    /**
     * 订单结算
     *
     * @param settlementBean 结算信息
     */
    void orderSettlement(OrderSettlementBean settlementBean);
}
