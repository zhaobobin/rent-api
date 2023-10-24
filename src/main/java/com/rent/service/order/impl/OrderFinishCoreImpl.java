package com.rent.service.order.impl;


import com.rent.common.enums.components.EnumTradeType;
import com.rent.common.enums.components.OrderCenterStatus;
import com.rent.common.enums.order.EnumOrderByStagesStatus;
import com.rent.common.enums.order.EnumOrderStatus;
import com.rent.common.enums.order.EnumViolationStatus;
import com.rent.common.util.AsyncUtil;
import com.rent.dao.order.OrderByStagesDao;
import com.rent.dao.order.UserOrdersDao;
import com.rent.model.order.UserOrders;
import com.rent.service.components.OrderCenterService;
import com.rent.service.order.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author xiaoyao
 * @version V1.0
 * @date 2020-6-22 11:47
 * @since 1.0
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class OrderFinishCoreImpl implements OrderFinishCore {

    private final UserOrdersDao userOrdersDao;
    private final OrderByStagesDao orderByStagesDao;
    private final CapitalOperateCore capitalOperateCore;
    private final OrderCenterService orderCenterService;
    private final OrderOperateCore orderOperateCore;
    private final OrderAntChainService orderAntChainService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void orderFinishCommHandle(UserOrders userOrders) {
        log.info("订单:{}开始进行完成订单操作", userOrders.getOrderId());
        EnumOrderStatus oldStatus = userOrders.getStatus();
        //1.更新订单状态，分期账单状态 2.同步订单信息到小程序 3.解冻押金
        userOrders.setUpdateTime(new Date());
        userOrders.setStatus(EnumOrderStatus.FINISH);
        userOrders.setIsViolation(EnumViolationStatus.NORMAL);
        userOrdersDao.updateByOrderId(userOrders);
        //更新还款账单记录状态
        orderByStagesDao.updateStatusByOrderId(userOrders.getOrderId(), EnumOrderByStagesStatus.CANCEL,EnumOrderByStagesStatus.UN_PAY);
        orderByStagesDao.updateStatusByOrderId(userOrders.getOrderId(), EnumOrderByStagesStatus.CANCEL,EnumOrderByStagesStatus.OVERDUE_NOT_PAY);
        //解冻押金
        capitalOperateCore.alipayUnfreeze(userOrders, 1, "订单完成解冻订单", null, EnumTradeType.FINISH_ORDER);
        //同步订单信息已经结束到小程序订单中心
        AsyncUtil.runAsync(() -> orderCenterService.merchantOrderSync(userOrders.getOrderId(), OrderCenterStatus.FINISHED));
        //添加订单操作记录 add at 2020年10月27日13:58:41
        AsyncUtil.runAsync(
            () -> orderOperateCore.orderOperationRegister(userOrders.getOrderId(), oldStatus, EnumOrderStatus.FINISH,
                userOrders.getUid(), userOrders.getUserName(), "订单完结"));
        AsyncUtil.runAsync(() -> orderAntChainService.syncFinish(userOrders.getOrderId()));
    }

}
