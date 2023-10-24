package com.rent.dao.order;

import com.rent.config.mybatis.IBaseDao;
import com.rent.model.order.UserOrderItems;

import java.util.List;

/**
 * UserOrderItemsDao
 *
 * @author xiaoyao
 * @Date 2020-06-16 10:10
 */
public interface UserOrderItemsDao extends IBaseDao<UserOrderItems> {

    /**
     * 根据订单id查询一条记录
     *
     * @param orderId
     * @return
     */
    UserOrderItems selectOneByOrderId(String orderId);

    /**
     * 根据订单id列表查询订单商品信息
     *
     * @param orderIdList 订单id列表
     * @return
     */
    List<UserOrderItems> queryListByOrderIds(List<String> orderIdList);

    /**
     * 根据订单id列表查询订单商品信息
     *
     * @param productIds 订单id列表
     * @return
     */
    List<String> queryOrderIdsByProductIdList(List<String> productIds);
}
