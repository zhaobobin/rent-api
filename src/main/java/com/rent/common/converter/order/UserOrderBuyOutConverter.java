package com.rent.common.converter.order;

import com.rent.common.dto.order.UserOrderBuyOutDto;
import com.rent.model.order.UserOrderBuyOut;

/**
 * 用户买断Service
 *
 * @author xiaoyao
 * @Date 2020-06-23 14:50
 */
public class UserOrderBuyOutConverter {

    /**
     * model转dto
     *
     * @param model
     * @return
     */
    public static UserOrderBuyOutDto model2Dto(UserOrderBuyOut model) {
        if (model == null) {
            return null;
        }
        UserOrderBuyOutDto dto = new UserOrderBuyOutDto();
        dto.setUid(model.getUid());
        dto.setOrderId(model.getOrderId());
        dto.setBuyOutOrderId(model.getBuyOutOrderId());
        dto.setMarketPrice(model.getMarketPrice());
        dto.setSalePrice(model.getSalePrice());
        dto.setRealSalePrice(model.getRealSalePrice());
        dto.setPaidRent(model.getPaidRent());
        dto.setEndFund(model.getEndFund());
        dto.setState(model.getState());
        dto.setOutTransNo(model.getOutTransNo());
        dto.setRemark(model.getRemark());
        dto.setCreateTime(model.getCreateTime());
        dto.setChannelId(model.getChannelId());
        dto.setSplitBillTime(model.getSplitBillTime());
        return dto;
    }
}