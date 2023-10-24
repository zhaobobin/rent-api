        
package com.rent.dao.order;

import com.rent.config.mybatis.IBaseDao;
import com.rent.model.order.OrderLocationAddress;

/**
 * OrderLocationAddressDao
 *
 * @author youruo
 * @Date 2021-01-14 15:15
 */
public interface OrderLocationAddressDao extends IBaseDao<OrderLocationAddress> {

    /**
     * 根据订单编号查询
     * @param orderId
     * @return
     */
    OrderLocationAddress getByOrderId(String orderId);

}
