package com.rent.dao.order;

import com.rent.config.mybatis.IBaseDao;
import com.rent.model.order.UserOrdersStatusTranfer;

import java.util.List;

/**
 * UserOrdersStatusTranferDao
 *
 * @author xiaoyao
 * @Date 2020-07-23 16:06
 */
public interface UserOrdersStatusTranferDao extends IBaseDao<UserOrdersStatusTranfer> {

    /**
     * 根据订单编号查询记录
     * @param orderId
     * @return
     */
    List<UserOrdersStatusTranfer> queryByOrderId(String orderId);

}
