package com.rent.service.components;


import com.rent.common.dto.components.dto.AntChainSyncLeasePromise;
import com.rent.common.dto.components.dto.AntChainSyncLogistic;
import com.rent.common.dto.components.dto.AntChainSyncOrder;
import com.rent.common.dto.components.dto.AntChainSyncPerformance;

/**
 * 蚂蚁链服务
 * @author zhaowenchao
 */
public interface AntChainService {

    /**
     * 蚂蚁链第一步-通知注册
     */
    void notifyRegister();


    /**
     * 蚂蚁链-租赁平台统一接口服务-租赁订单承诺信息
     * @param req
     * @return
     */
    Boolean syncLeasePromise(AntChainSyncLeasePromise req);


    /**
     * 蚂蚁链 用户下单同步
     * 1.蚂蚁链-租赁平台统一接口服务-商品信息
     * 2.蚂蚁链-租赁平台统一接口服务-租赁订单用户信息
     * 3.蚂蚁链-租赁平台统一接口服务-租赁订单信息
     * 4.蚂蚁链-租赁平台统一接口服务-租赁订单商品信息
     * @param req
     * @return
     */
    Boolean syncOrder(AntChainSyncOrder req);


    /**
     * 蚂蚁链-租赁平台统一接口服务-租赁订单物流信息
     * @param req
     * @return
     */
    Boolean syncLogistic(AntChainSyncLogistic req);


    /**
     * 蚂蚁链-租赁平台统一接口服务-租赁订单履约信息
     * @param req
     * @return
     */
    Boolean syncPerformance(AntChainSyncPerformance req);


    void queryOrder(String orderId);
    void queryOrderProduct(String orderId);
    void queryOrderUSer(String orderId);
    void queryProduct(String productId,String productVersion);
}
