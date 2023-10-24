
package com.rent.dao.order;


import com.rent.config.mybatis.IBaseDao;
import com.rent.model.order.OrderAuditUser;

import java.util.List;


public interface OrderAuditUserDao extends IBaseDao<OrderAuditUser> {

    OrderAuditUser getByOrderId(String orderId);

    List<OrderAuditUser> getOrderByBackstageUserId(Long backstageUserId);
}
