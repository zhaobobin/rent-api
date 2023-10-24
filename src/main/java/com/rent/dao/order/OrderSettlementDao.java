package com.rent.dao.order;

import com.rent.config.mybatis.IBaseDao;
import com.rent.model.order.OrderSettlement;

import java.util.List;

/**
 * OrderSettlementDao
 *
 * @author xiaoyao
 * @Date 2020-06-18 18:16
 */
public interface OrderSettlementDao extends IBaseDao<OrderSettlement> {
    /**
     * 根据订单id查询结算单
     *
     * @param orderId 订单编号
     * @return 结算单
     */
    OrderSettlement selectOneByOrderId(String orderId);

    List<OrderSettlement> queryUnSplitList();

    void updateStateSplitBill(Long recordId);
}
