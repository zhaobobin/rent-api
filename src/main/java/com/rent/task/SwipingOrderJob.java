package com.rent.task;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rent.common.enums.order.EnumOrderStatus;
import com.rent.dao.order.UserOrdersDao;
import com.rent.model.order.UserOrders;
import com.rent.service.order.OrderReceiptCore;
import com.rent.service.order.SwipingActivityOrderService;
import com.rent.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * @author zhaowenchao
 */
@Slf4j
@RequiredArgsConstructor
public class SwipingOrderJob extends QuartzJobBean {

    private final UserOrdersDao userOrdersDao;
    private final SwipingActivityOrderService swipingActivityOrderService;
    private final OrderReceiptCore orderReceiptCore;


    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext){
        log.info("Quartz_task_SwipingOrderJob=============================================================================");
        //租用中的刷单订单就完结订单
        List<UserOrders> userOrdersList = this.userOrdersDao.list(new QueryWrapper<UserOrders>()
                .eq("status", EnumOrderStatus.RENTING));
        for (UserOrders userOrders : userOrdersList) {
            swipingActivityOrderService.finishSwipingActivity(userOrders);
        }


        //7天前待确认收货的订单自动确认收货
        Date sevenBeforeDay = DateUtil.dateReduceDay(new Date(), 7);
        List<UserOrders> waitingReceiveList = userOrdersDao.list(new QueryWrapper<>(new UserOrders())
                .eq("status", EnumOrderStatus.WAITING_USER_RECEIVE_CONFIRM)
                .isNull("delete_time")
                .lt("payment_time", sevenBeforeDay));
        if (CollectionUtil.isEmpty(waitingReceiveList)) {
            log.info("没有7天前待用户确认收货订单，直接退出");
            return;
        }
        List<String> orderIds = waitingReceiveList.stream().map(UserOrders::getOrderId).collect(Collectors.toList());
        Set<String> swipingActivityOrderIdSet = swipingActivityOrderService.getSwipingOrderMapByIds(orderIds);
        if (CollectionUtil.isEmpty(swipingActivityOrderIdSet)) {
            log.info("没有7天前待用户确认收货刷单订单，直接退出");
            return;
        }
        for (UserOrders userOrders : waitingReceiveList) {
            if (swipingActivityOrderIdSet.contains(userOrders.getOrderId())) {
                orderReceiptCore.swipingAutoConfirm(userOrders);
            }
        }
    }
}
