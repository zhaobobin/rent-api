package com.rent.handler.ordersettle.bean;

import com.rent.common.enums.order.EnumOrderSettlementType;
import com.rent.model.order.OrderSettlement;
import com.rent.model.order.UserOrders;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author xiaoyao
 * @version V1.0
 * @date 2020-6-19 15:20
 * @since 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderSettlementBean {
    /** 订单信息 */
    private UserOrders userOrders;
    /** 损坏赔偿金额 */
    private BigDecimal damageAmount;
    /** 丢失赔偿金额 */
    private BigDecimal lossAmount;
    /** 违约赔偿金额 */
    private BigDecimal penaltyAmount;
    /** 结算类型 */
    private EnumOrderSettlementType settlementType;
    /** 是否为修改结算单 */
    private boolean isModify;
    /** 结算信息 */
    private OrderSettlement orderSettlement;
}
