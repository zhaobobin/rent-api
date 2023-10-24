package com.rent.handler.ordersettle.handle;

import com.rent.dao.order.UserOrdersDao;
import com.rent.handler.ordersettle.OrderSettlementHandle;
import com.rent.handler.ordersettle.bean.OrderSettlementBean;
import com.rent.model.order.UserOrders;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author xiaoyao
 * @version V1.0
 * @date 2020-6-19 13:47
 * @since 1.0
 */
public abstract class AbstractOrderSettlementHandler implements OrderSettlementHandle {

    @Autowired
    private UserOrdersDao userOrdersDao;

    /**
     * 由具体的子类实现
     *
     * @param settlementBean 结算信息
     */
    @Override
    public abstract void orderSettlement(OrderSettlementBean settlementBean);

    /**
     * 根据订单id更新订单状态
     *
     * @param userOrders
     */
    public void updateUserOrdersStatus(UserOrders userOrders) {
        userOrdersDao.updateByOrderId(userOrders);
    }
}
