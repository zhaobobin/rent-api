package com.rent.common.converter.order;

import com.rent.common.enums.order.EnumOrderSettlementStatus;
import com.rent.common.enums.order.EnumOrderSettlementType;
import com.rent.model.order.OrderSettlement;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单结算记录Service
 *
 * @author xiaoyao
 * @Date 2020-06-19 10:49
 */
public class OrderSettlementConverter {


    public static OrderSettlement assemblyOrderSettlementModel(Date now, String orderId,
                                                               EnumOrderSettlementType settlementType, BigDecimal lossAmount, BigDecimal damageAmount,
                                                               BigDecimal penaltyAmount) {
        OrderSettlement model = new OrderSettlement();
        model.setCreateTime(now);
        model.setUpdateTime(now);
        model.setOrderId(orderId);
        model.setSettlementStatus(EnumOrderSettlementStatus.UNPAID);
        model.setSettlementType(settlementType);
        model.setApplyModifyTimes(0);
        model.setLoseAmount(lossAmount);
        model.setDamageAmount(damageAmount);
        model.setPenaltyAmount(penaltyAmount);
        return model;

    }
}