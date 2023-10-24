
package com.rent.service.order;

import com.rent.model.order.UserOrders;

import java.util.List;
import java.util.Set;

/**
 * 刷单活动表Service
 *
 * @author youruo
 * @Date 2021-12-20 09:45
 */
public interface SwipingActivityOrderService {


    /**
     * 保存刷单商品订单为刷单订单
     * @param orderId
     * @return
     */
    Boolean saveOrUpdateSwiping(String orderId);

    /**
     * 判断是否是刷单订单
     * @param orderId
     * @return
     */
    Boolean isHasSwipingActivity(String orderId);

    /**
     * 刷单商品完结订单
     * @param userOrders
     */
    void finishSwipingActivity(UserOrders userOrders);


    /**
     * 批量判断是否是刷单商品
     * @param orderIds
     * @return
     */
    Set<String> getSwipingOrderMapByIds(List<String> orderIds);
}