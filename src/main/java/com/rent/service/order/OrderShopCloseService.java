package com.rent.service.order;

import com.rent.common.dto.order.resquest.ShopRiskReviewOrderIdDto;


/**
 * 商家风控关单Service
 *
 * @author xiaoyao
 * @Date 2020-06-17 16:54
 */
public interface OrderShopCloseService {


    /**
     * 商家风控关单
     *
     * @param request 入参
     */
    void shopRiskCloseOrder(ShopRiskReviewOrderIdDto request);
}