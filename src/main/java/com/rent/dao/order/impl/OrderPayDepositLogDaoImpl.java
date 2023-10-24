package com.rent.dao.order.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rent.common.dto.order.DepositOrderLogDto;
import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.order.OrderPayDepositLogDao;
import com.rent.mapper.order.OrderPayDepositLogMapper;
import com.rent.model.order.OrderPayDepositLog;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 用户支付押金表修改日志
 * @author zhaowenchao
 */
@Repository
public class OrderPayDepositLogDaoImpl extends AbstractBaseDaoImpl<OrderPayDepositLog, OrderPayDepositLogMapper> implements OrderPayDepositLogDao {


    @Override
    public OrderPayDepositLog saveLog(String orderId, BigDecimal originAmount, BigDecimal afterAmount, Long backstageUserId, String backstageUserName,String remark) {
        OrderPayDepositLog orderPayDepositLog = new OrderPayDepositLog();
        orderPayDepositLog.setOrderId(orderId);
        orderPayDepositLog.setOriginAmount(originAmount);
        orderPayDepositLog.setAfterAmount(afterAmount);
        orderPayDepositLog.setBackstageUserId(backstageUserId);
        orderPayDepositLog.setBackstageUserName(backstageUserName);
        orderPayDepositLog.setRemark(remark);
        orderPayDepositLog.setCreateTime(new Date());
        save(orderPayDepositLog);
        return orderPayDepositLog;
    }

    @Override
    public List<DepositOrderLogDto> getByOrderId(String orderId) {
        List<OrderPayDepositLog> list=list(new QueryWrapper<OrderPayDepositLog>().eq("order_id",orderId).orderByDesc("id"));
        List<DepositOrderLogDto> logs = new ArrayList<>(list.size());
        for (OrderPayDepositLog orderPayDepositLog : list) {
            DepositOrderLogDto dto = new DepositOrderLogDto();
            dto.setAfterAmount(orderPayDepositLog.getAfterAmount());
            dto.setBackstageUserName(orderPayDepositLog.getBackstageUserName());
            dto.setCreateTime(orderPayDepositLog.getCreateTime());
            logs.add(dto);
        }
        return logs;
    }
}
