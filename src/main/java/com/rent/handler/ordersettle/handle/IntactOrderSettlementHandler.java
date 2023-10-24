package com.rent.handler.ordersettle.handle;


import com.rent.handler.ordersettle.bean.OrderSettlementBean;
import com.rent.model.order.UserOrders;
import com.rent.service.order.OrderFinishCore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author xiaoyao
 * @version V1.0
 * @date 2020-6-19 13:47
 * @since 1.0
 */
@Slf4j
@Component
public class IntactOrderSettlementHandler extends AbstractOrderSettlementHandler {

    private OrderFinishCore orderFinishCore;

    public IntactOrderSettlementHandler(OrderFinishCore orderFinishCore) {
        this.orderFinishCore = orderFinishCore;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void orderSettlement(OrderSettlementBean settlementBean) {
        UserOrders userOrders = settlementBean.getUserOrders();
        orderFinishCore.orderFinishCommHandle(userOrders);
    }
}
