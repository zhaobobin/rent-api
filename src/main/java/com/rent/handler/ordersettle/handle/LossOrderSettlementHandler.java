package com.rent.handler.ordersettle.handle;


import com.rent.common.enums.order.EnumOrderStatus;
import com.rent.dao.order.OrderSettlementDao;
import com.rent.handler.ordersettle.bean.OrderSettlementBean;
import com.rent.model.order.UserOrders;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author xiaoyao
 * @version V1.0
 * @date 2020-6-19 13:52
 * @since 1.0
 */
@Slf4j
@Component
public class LossOrderSettlementHandler extends AbstractOrderSettlementHandler {

    private OrderSettlementDao orderSettlementDao;

    public LossOrderSettlementHandler(OrderSettlementDao orderSettlementDao) {
        this.orderSettlementDao = orderSettlementDao;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void orderSettlement(OrderSettlementBean settlementBean) {
        UserOrders userOrders = settlementBean.getUserOrders();
        userOrders.setStatus(EnumOrderStatus.WAITING_SETTLEMENT_PAYMENT);
        this.updateUserOrdersStatus(userOrders);
        orderSettlementDao.saveOrUpdate(settlementBean.getOrderSettlement());
    }
}
