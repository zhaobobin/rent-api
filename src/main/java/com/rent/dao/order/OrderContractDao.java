        
package com.rent.dao.order;

import com.rent.config.mybatis.IBaseDao;
import com.rent.model.order.OrderContract;

/**
 * OrderContractDao
 *
 * @author zhao
 * @Date 2020-11-09 15:41
 */
public interface OrderContractDao extends IBaseDao<OrderContract> {


    /**
     * 根据订单ID获取合同签署情况
     * @param orderId
     * @return
     */
    OrderContract getByOrderId(String orderId);


    /**
     * 新增或者保存合同信息
     * @param orderContract
     */
    void saveOrUpdateContract(OrderContract orderContract);
}
