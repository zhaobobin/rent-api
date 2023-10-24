package com.rent.common.converter.order;

import com.rent.common.dto.order.resquest.ShopRiskReviewOrderIdDto;
import com.rent.model.order.OrderShopClose;
import org.apache.commons.collections.CollectionUtils;

import java.util.Date;

/**
 * 商家风控关单Service
 *
 * @author xiaoyao
 * @Date 2020-06-17 16:54
 */
public class OrderShopCloseConverter {


    public static OrderShopClose riskOrderDto2Model(ShopRiskReviewOrderIdDto request) {
        if (null == request) {
            return null;
        }
        OrderShopClose orderShopClose = new OrderShopClose();
        if (CollectionUtils.isNotEmpty(request.getCertificateImages())) {
            orderShopClose.setCertificateImages(String.join("_", request.getCertificateImages()));
        }
        orderShopClose.setOrderId(request.getOrderId());
        orderShopClose.setShopId(request.getShopId());
        orderShopClose.setShopOperatorId(request.getShopOperatorId());
        orderShopClose.setCreateTime(new Date());
        orderShopClose.setCloseReason(request.getCloseReason());
        return orderShopClose;
    }
}