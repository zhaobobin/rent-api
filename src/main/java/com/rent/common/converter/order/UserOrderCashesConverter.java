package com.rent.common.converter.order;

import com.rent.common.dto.order.OrderPricesDto;
import com.rent.common.dto.order.UserOrderCashesDto;
import com.rent.common.util.BeanUtils;
import com.rent.common.util.StringUtil;
import com.rent.model.order.UserOrderCashes;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户订单金额Service
 *
 * @author xiaoyao
 * @Date 2020-06-15 17:08
 */
public class UserOrderCashesConverter {

    /**
     * model转dto
     *
     * @param model
     * @return
     */
    public static UserOrderCashesDto model2Dto(UserOrderCashes model) {
        if (model == null) {
            return null;
        }
        UserOrderCashesDto dto = new UserOrderCashesDto();
        dto.setId(model.getId());
        dto.setCreateTime(model.getCreateTime());
        dto.setUpdateTime(model.getUpdateTime());
        dto.setDeleteTime(model.getDeleteTime());
        dto.setOrderId(model.getOrderId());
        dto.setTotalDeposit(model.getTotalDeposit());
        dto.setDepositReduction(model.getDepositReduction());
        dto.setDeposit(model.getDeposit());
        dto.setCouponReduction(model.getCouponReduction());
        dto.setFullReduction(model.getFullReduction());
        dto.setPlatformCouponReduction(model.getPlatformCouponReduction());
        dto.setPlatformFullReduction(model.getPlatformFullReduction());
        dto.setActivityReduction(model.getActivityReduction());
        dto.setRent(model.getRent());
        dto.setTotalRent(model.getTotalRent());
        dto.setSettlementRent(model.getSettlementRent());
        dto.setTotal(model.getTotal());
        dto.setTotalPremium(model.getTotalPremium());
        dto.setFreightPrice(model.getFreightPrice());
        dto.setLostPrice(model.getLostPrice());
        dto.setDamagePrice(model.getDamagePrice());
        dto.setPenaltyAmount(model.getPenaltyAmount());
        dto.setRefund(model.getRefund());
        dto.setSupplementPrice(model.getSupplementPrice());
        dto.setFreightReduction(model.getFreightReduction());
        dto.setShouldRefundPrice(model.getShouldRefundPrice());
        dto.setAdditionalServicesPrice(model.getAdditionalServicesPrice());
        dto.setOriginalRent(model.getOriginalRent());
        dto.setOriginalTotalRent(model.getOriginalTotalRent());
        dto.setFreezePrice(model.getFreezePrice());
        dto.setCouponRecallReduction(model.getCouponRecallReduction());
        dto.setRetainDeductionAmount(model.getRetainDeductionAmount());

        return dto;
    }

    public static UserOrderCashes prices2Model(Date now, String orderId, OrderPricesDto orderPricesDto) {
        if (StringUtil.isEmpty(orderId) || null == orderPricesDto) {
            return null;
        }
        UserOrderCashes orderCashesData = new UserOrderCashes();
        orderCashesData.setOrderId(orderId);
        orderCashesData.setTotalDeposit(orderPricesDto.getDepositAmount());
        orderCashesData.setDepositReduction(orderPricesDto.getDepositReduce());
        orderCashesData.setDeposit(orderPricesDto.getDeposit());
        orderCashesData.setCouponReduction(orderPricesDto.getShopCouponPrice());
        orderCashesData.setPlatformCouponReduction(orderPricesDto.getPlatformCouponPrice());
        orderCashesData.setRent(orderPricesDto.getSkuPrice());
        orderCashesData.setOriginalRent(orderPricesDto.getOriginalMonthRentPrice());
        orderCashesData.setTotalRent(orderPricesDto.getTotalRent());
        orderCashesData.setSettlementRent(orderPricesDto.getTotalRent());
        orderCashesData.setTotal(BigDecimal.ZERO);
        orderCashesData.setAdditionalServicesPrice(orderPricesDto.getAdditionalServicesPrice());
        orderCashesData.setCreateTime(now);
        orderCashesData.setUpdateTime(now);
        orderCashesData.setOriginalTotalRent(orderPricesDto.getRentPrice());
        orderCashesData.setFreezePrice(orderPricesDto.getFreezePrice());
        orderCashesData.setActivityReduction(orderPricesDto.getActivityDiscountAmount());
        orderCashesData.setFreightPrice(orderPricesDto.getLogisticPrice());
        orderCashesData.setRetainDeductionAmount(orderPricesDto.getRetainDeductionAmount());
        BeanUtils.moneyNull2Zreo(orderCashesData);
        return orderCashesData;
    }
}