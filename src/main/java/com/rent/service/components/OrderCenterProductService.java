package com.rent.service.components;


import com.rent.model.components.OrderCenterProduct;

/**
 * @author udo
 * 订单中心服务
 */
public interface OrderCenterProductService {

    /**
     * 查询商品在订单中心的信息，如果没有就同步到订单中心
     * https://opendocs.alipay.com/mini/024hj6?pathHash=0555713a&ref=api
     * 【权限集列表-订单中心-接入指南-各行业订单同步/查询详情-线上租赁（3C_RENT）】
     * @param productId
     */
    OrderCenterProduct getByProductId(String productId);

}
