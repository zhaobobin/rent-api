package com.rent.dao.order;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.enums.order.EnumOrderRemarkSource;
import com.rent.config.mybatis.IBaseDao;
import com.rent.model.order.OrderRemark;

/**
 * OrderRemarkDao
 *
 * @author xiaoyao
 * @Date 2020-06-16 10:09
 */
public interface OrderRemarkDao extends IBaseDao<OrderRemark> {

    /**
     * 据订单id和备注源查询备注
     * @param page 分页
     * @param orderId 订单编号
     * @param remarkSource 备注源
     * @return
     */
    Page<OrderRemark> pageByOrderId(Page<OrderRemark> page, String orderId, EnumOrderRemarkSource remarkSource);
}
