package com.rent.dao.order.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rent.common.enums.order.EnumOrderSettlementStatus;
import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.order.OrderSettlementDao;
import com.rent.mapper.order.OrderSettlementMapper;
import com.rent.model.order.OrderSettlement;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * OrderSettlementDao
 *
 * @author xiaoyao
 * @Date 2020-06-18 18:16
 */
@Repository
public class OrderSettlementDaoImpl extends AbstractBaseDaoImpl<OrderSettlement, OrderSettlementMapper>
    implements OrderSettlementDao {

    @Override
    public OrderSettlement selectOneByOrderId(String orderId) {
        return getOne(new QueryWrapper<>(OrderSettlement.builder()
            .orderId(orderId)
            .build()));
    }

    @Override
    public List<OrderSettlement> queryUnSplitList() {
        return list(new QueryWrapper<OrderSettlement>()
            .eq("settlement_status", EnumOrderSettlementStatus.SETTLED)
            .isNull("split_time"));
    }

    @Override
    public void updateStateSplitBill(Long recordId) {
        OrderSettlement settlement = getById(recordId);
        settlement.setSplitTime(new Date());
        updateById(settlement);
    }
}
