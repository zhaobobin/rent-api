package com.rent.service.order;

import com.rent.common.enums.product.AntChainProductClassEnum;
import com.rent.model.order.OrderByStages;
import com.rent.model.order.UserOrders;

import java.util.List;

/**
 * 蚁盾分
 * @author zhaowenchao
 */
public interface OrderAntChainService {

    /**
     * 查询蚁盾分
     * @param orderId
     * @return
     */
    String queryAntChainShieldScore(String orderId);


    /**
     * 订单上链
     * @param orderId
     * @return
     */
    Boolean syncToAntChain(String orderId);


    /**
     * 同步订单承若信息
     * @param orderId
     * @param orderByStages
     * @return
     */
    Boolean syncLeasePromise(String orderId, List<OrderByStages> orderByStages);

    /**
     * 同步订单信息
     * @param orderId
     * @param orderByStages
     * @param antChainProductClassEnum
     * @return
     */
    Boolean syncOrder(String orderId, List<OrderByStages> orderByStages, AntChainProductClassEnum antChainProductClassEnum);

    /**
     * 同步物流信息
     * @param userOrders
     * @param first 是否是第一次同步
     * @return
     */
    Boolean syncLogistic(UserOrders userOrders, Boolean first);



    /**
     * 【用户支付租金|租金代扣】后同步履约信息
     * @param orderByStagesList
     */
    void syncPerformance(List<OrderByStages> orderByStagesList);

    /**
     * 同步订单完成
     * @param orderId
     * @return
     */
    Boolean syncFinish(String orderId);

    /**
     * 蚂蚁链投保
     * @param orderId
     * @return
     */
    Boolean antChainInsure(String orderId);


    /**
     * 蚂蚁链退保
     * @param orderId
     * @return
     */
    Boolean antChainCancelInsurance(String orderId);


}
