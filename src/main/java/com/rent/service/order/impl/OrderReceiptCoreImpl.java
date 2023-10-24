package com.rent.service.order.impl;


import com.rent.common.enums.components.OrderCenterStatus;
import com.rent.common.enums.order.EnumOrderError;
import com.rent.common.enums.order.EnumOrderStatus;
import com.rent.common.util.AsyncUtil;
import com.rent.dao.order.UserOrdersDao;
import com.rent.exception.HzsxBizException;
import com.rent.model.order.UserOrders;
import com.rent.service.components.OrderCenterService;
import com.rent.service.order.OrderAntChainService;
import com.rent.service.order.OrderOperateCore;
import com.rent.service.order.OrderReceiptCore;
import com.rent.service.order.SwipingActivityOrderService;
import com.rent.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author zhaowenchao
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class OrderReceiptCoreImpl implements OrderReceiptCore {

    private final UserOrdersDao userOrdersDao;
    private final OrderOperateCore orderOperateCore;
    private final OrderCenterService orderCenterService;
    private final OrderAntChainService orderAntChainService;
    private final SwipingActivityOrderService swipingActivityOrderService;

    @Override
    public void confirmReceipt(String orderId,Date confirmDate,String operatorId,String operatorName) {
        UserOrders userOrders = userOrdersDao.selectOneByOrderId(orderId);
        EnumOrderStatus oldStatus = userOrders.getStatus();
        receiptUpdateUserOrder(userOrders, confirmDate);
        AsyncUtil.runAsync(() -> orderOperateCore.orderOperationRegister(userOrders.getOrderId(), oldStatus, EnumOrderStatus.RENTING,operatorId, operatorName, "平台确认收货"));
        AsyncUtil.runAsync(() ->orderCenterService.merchantOrderSync(userOrders.getOrderId(), OrderCenterStatus.IN_THE_LEASE));

    }

    @Override
    public void pickUp(String orderId,String operatorName) {
        UserOrders userOrders = userOrdersDao.selectOneByOrderId(orderId);
        EnumOrderStatus oldStatus = userOrders.getStatus();
        userOrders.setStatus(EnumOrderStatus.WAITING_USER_RECEIVE_CONFIRM);
        receiptUpdateUserOrder(userOrders, new Date());
        AsyncUtil.runAsync(() -> orderOperateCore.orderOperationRegister(userOrders.getOrderId(), oldStatus, userOrders.getStatus(), "SYS", operatorName, "用户自提"));
        AsyncUtil.runAsync(() ->orderCenterService.merchantOrderSync(userOrders.getOrderId(), OrderCenterStatus.IN_THE_LEASE));
    }

    @Override
    public void taskConfirm(UserOrders userOrders, Date confirmDate) {
        EnumOrderStatus oldStatus = userOrders.getStatus();
        receiptUpdateUserOrder(userOrders,confirmDate);
        AsyncUtil.runAsync(() -> orderOperateCore.orderOperationRegister(userOrders.getOrderId(), oldStatus, EnumOrderStatus.RENTING,"SYS", "SYS", "系统确认收货"));
        AsyncUtil.runAsync(() ->orderCenterService.merchantOrderSync(userOrders.getOrderId(), OrderCenterStatus.IN_THE_LEASE));
    }

    @Override
    public void userConfirm(String orderId) {
        UserOrders userOrders = userOrdersDao.selectOneByOrderId(orderId);
        EnumOrderStatus oldStatus = userOrders.getStatus();
        receiptUpdateUserOrder(userOrders, DateUtil.getStartTimeOfDay(new Date()));
        AsyncUtil.runAsync(() -> orderOperateCore.orderOperationRegister(userOrders.getOrderId(), oldStatus, EnumOrderStatus.RENTING,userOrders.getUid(), userOrders.getUserName(), "用户确认收货"));
    }

    @Override
    public void swipingAutoConfirm(UserOrders userOrders) {
        EnumOrderStatus oldStatus = userOrders.getStatus();
        receiptUpdateUserOrder(userOrders,new Date());
        swipingActivityOrderService.finishSwipingActivity(userOrders);
        AsyncUtil.runAsync(() -> orderOperateCore.orderOperationRegister(userOrders.getOrderId(), oldStatus, EnumOrderStatus.RENTING,userOrders.getUid(), userOrders.getUserName(), "刷单自动确认收货"));
    }

    private void receiptUpdateUserOrder(UserOrders userOrders, Date confirmDate) {
        if (null == userOrders) {
            throw new HzsxBizException(EnumOrderError.ORDER_NOT_EXISTS.getCode(),EnumOrderError.ORDER_NOT_EXISTS.getMsg());
        }
        if (!EnumOrderStatus.WAITING_USER_RECEIVE_CONFIRM.equals(userOrders.getStatus())) {
            log.error("订单:{}状态错误，期望的状态是:[{}],实际状态是:[{}]", userOrders.getOrderId(),EnumOrderStatus.WAITING_USER_RECEIVE_CONFIRM.getDescription(), userOrders.getStatus().getDescription());
            throw new HzsxBizException(EnumOrderError.ORDER_STATUS_NOT_ALLOW_APPLY.getCode(),EnumOrderError.ORDER_STATUS_NOT_ALLOW_APPLY.getMsg());
        }
        Date now = new Date();
        userOrders.setRentStart(confirmDate);
        userOrders.setUnrentTime(DateUtil.getEndTimeOfDay(DateUtil.addDate(userOrders.getRentStart(), userOrders.getRentDuration() - 1)));
        userOrders.setUpdateTime(now);
        userOrders.setReceiveTime(now);
        userOrders.setStatus(EnumOrderStatus.RENTING);
        userOrdersDao.updateById(userOrders);
        orderAntChainService.syncLogistic(userOrders,Boolean.FALSE);
        swipingActivityOrderService.finishSwipingActivity(userOrders);
    }
}
