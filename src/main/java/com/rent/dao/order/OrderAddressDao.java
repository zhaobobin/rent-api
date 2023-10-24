package com.rent.dao.order;

import com.rent.config.mybatis.IBaseDao;
import com.rent.model.order.OrderAddress;

import java.util.Collection;
import java.util.List;

/**
 * OrderAddressDao
 *
 * @author xiaoyao
 * @Date 2020-06-16 10:09
 */
public interface OrderAddressDao extends IBaseDao<OrderAddress> {

    /**
     * 根据订单号查询地址信息
     * @param orderId 订单编号
     * @return
     */
    OrderAddress queryByOrderId(String orderId);

    /**
     * 根据订单号查询地址信息
     * @param orderIds 订单编号
     * @return
     */
    List<OrderAddress> queryByOrderIds(Collection<String> orderIds);

    /**
     * 根据手机号码获取订单ID列表
     * @param addressUserPhone
     * @return
     */
    List<String> getOrderIdByTelephone(String addressUserPhone);
}
