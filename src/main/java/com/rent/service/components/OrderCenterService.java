package com.rent.service.components;


import com.rent.common.enums.components.OrderCenterStatus;

/**
 * @author udo
 * 订单中心服务
 */
public interface OrderCenterService {


    /**
     * 同步订单到订单中心
     * https://opendocs.alipay.com/mini/024hj6?pathHash=0555713a&ref=api
     * 【权限集列表-订单中心-接入指南-各行业订单同步/查询详情-线上租赁（3C_RENT）】
     * @param orderId
     * @param status
     */
    void merchantOrderSync(String orderId,OrderCenterStatus status);

}
