package com.rent.dao.order;

import com.rent.common.dto.order.DepositOrderLogDto;
import com.rent.config.mybatis.IBaseDao;
import com.rent.model.order.OrderPayDepositLog;

import java.math.BigDecimal;
import java.util.List;

/**
 * 用户支付押金表修改日志
 * @author zhaowenchao
 */
public interface OrderPayDepositLogDao extends IBaseDao<OrderPayDepositLog> {


    /**
     * 新增一条日志
     * @param orderId
     * @param originAmount
     * @param afterAmount
     * @param backstageUserId
     * @param backstageUserName
     * @param remark
     */
    OrderPayDepositLog saveLog(String orderId,BigDecimal originAmount,BigDecimal afterAmount,Long backstageUserId,String backstageUserName,String remark);


    /**
     * 查询订单押金修改记录
     * @param orderId
     * @return
     */
    List<DepositOrderLogDto> getByOrderId(String orderId);
}
