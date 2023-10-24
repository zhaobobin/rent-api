
package com.rent.service.order.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rent.common.cache.product.ProductSwipingCache;
import com.rent.common.enums.components.EnumTradeType;
import com.rent.dao.order.OrderByStagesDao;
import com.rent.dao.order.SwipingActivityOrderDao;
import com.rent.dao.order.UserOrdersDao;
import com.rent.model.order.OrderByStages;
import com.rent.model.order.SwipingActivityOrder;
import com.rent.model.order.UserOrders;
import com.rent.service.components.AliPayCapitalService;
import com.rent.service.order.OrderFinishCore;
import com.rent.service.order.SwipingActivityOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 刷单活动表Service
 *
 * @author youruo
 * @Date 2021-12-20 09:45
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class SwipingActivityOrderServiceImpl implements SwipingActivityOrderService {

    private static final String SWIPING_ACTIVITY = "SWIPING_ACTIVITY";

    private final SwipingActivityOrderDao swipingActivityOrderDao;
    private final OrderFinishCore orderFinishCore;
    private final OrderByStagesDao orderByStagesDao;
    private final AliPayCapitalService aliPayCapitalService;
    private final UserOrdersDao userOrdersDao;

    @Override
    public Boolean saveOrUpdateSwiping(String orderId) {
        UserOrders userOrders = userOrdersDao.selectOneByOrderId(orderId);
        if(ProductSwipingCache.contains(userOrders.getProductId())){
            SwipingActivityOrder swipingActivityOrder = new SwipingActivityOrder();
            swipingActivityOrder.setOrderId(orderId);
            swipingActivityOrder.setProductId(userOrders.getProductId());
            swipingActivityOrder.setType(SWIPING_ACTIVITY);
            Date now = new Date();
            swipingActivityOrder.setCreateTime(now);
            swipingActivityOrder.setUpdateTime(now);
            swipingActivityOrderDao.saveOrUpdate(swipingActivityOrder);
            return Boolean.TRUE;
        }else {
            return Boolean.FALSE;
        }
    }

    @Override
    public Boolean isHasSwipingActivity(String orderId) {
        List<SwipingActivityOrder> swipingActivityOrderList = swipingActivityOrderDao.list(new QueryWrapper<SwipingActivityOrder>()
                .select("order_id")
                .eq("order_id", orderId));
        return CollectionUtil.isNotEmpty(swipingActivityOrderList);
    }

    @Override
    public void finishSwipingActivity(UserOrders userOrders) {
        Boolean isHasSwipingActivity = this.isHasSwipingActivity(userOrders.getOrderId());
        if (null != isHasSwipingActivity && isHasSwipingActivity) {
            orderFinishCore.orderFinishCommHandle(userOrders);
        }
    }

    @Override
    public Set<String> getSwipingOrderMapByIds(List<String> orderIds) {
        List<SwipingActivityOrder> list = swipingActivityOrderDao.list(new QueryWrapper<SwipingActivityOrder>()
                .select("order_id")
                .in("order_id", orderIds));
        return list.stream().map(SwipingActivityOrder::getOrderId).collect(Collectors.toSet());
    }
}