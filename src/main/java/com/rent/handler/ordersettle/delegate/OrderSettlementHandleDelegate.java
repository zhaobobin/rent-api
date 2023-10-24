package com.rent.handler.ordersettle.delegate;


import com.rent.common.enums.order.EnumOrderError;
import com.rent.common.enums.order.EnumOrderSettlementType;
import com.rent.exception.HzsxBizException;
import com.rent.handler.ordersettle.OrderSettlementHandle;
import com.rent.handler.ordersettle.bean.OrderSettlementBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * 结算申请委派器
 *
 * @author xiaoyao
 * @version V1.0
 * @date 2020-6-19 13:58
 * @since 1.0
 */
@Slf4j
@Component
public class OrderSettlementHandleDelegate {

    @Autowired
    @Qualifier("intactOrderSettlementHandler")
    private OrderSettlementHandle intactOrderSettlementHandler;

    @Autowired
    @Qualifier("damageOrderSettlementHandler")
    private OrderSettlementHandle damageOrderSettlementHandler;

    @Autowired
    @Qualifier("lossOrderSettlementHandler")
    private OrderSettlementHandle lossOrderSettlementHandler;

    @Autowired
    @Qualifier("violateOrderSettlementHandler")
    private OrderSettlementHandle violateOrderSettlementHandler;

    /**
     * 获取结算的实际处理者
     *
     * @param settlementType 结算类型
     * @return 实际的结算处理器
     */
    private OrderSettlementHandle getSettlementHandler(EnumOrderSettlementType settlementType) {
        if (settlementType != null) {
            switch (settlementType) {
                case INTACT:
                    return intactOrderSettlementHandler;
                case LOSS:
                    return lossOrderSettlementHandler;
                case DAMAGE:
                    return damageOrderSettlementHandler;
                case VIOLATE:
                    return violateOrderSettlementHandler;
                default:
                    throw new HzsxBizException(EnumOrderError.SETTLEMENT_TYPE_NOT_SUPPORTED.getCode(),EnumOrderError.SETTLEMENT_TYPE_NOT_SUPPORTED.getMsg(), this.getClass());
            }
        }
        throw new HzsxBizException(EnumOrderError.SETTLEMENT_TYPE_NOT_SUPPORTED.getCode(),
            EnumOrderError.SETTLEMENT_TYPE_NOT_SUPPORTED.getMsg(), this.getClass());
    }

    /**
     * 处理结算单
     *
     * @param orderSettlementBean 结算
     */
    public void orderSettlement(OrderSettlementBean orderSettlementBean) {
        this.getSettlementHandler(orderSettlementBean.getSettlementType()).orderSettlement(orderSettlementBean);
    }

}
