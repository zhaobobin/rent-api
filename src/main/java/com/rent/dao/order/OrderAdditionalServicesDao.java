package com.rent.dao.order;

import com.rent.config.mybatis.IBaseDao;
import com.rent.model.order.OrderAdditionalServices;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * OrderAdditionalServicesDao
 *
 * @author xiaoyao
 * @Date 2020-06-16 10:08
 */
public interface OrderAdditionalServicesDao extends IBaseDao<OrderAdditionalServices> {

    /**
     * 根据订单号查询增值服务
     * @param orderId 订单号
     * @return
     */
    List<OrderAdditionalServices> queryRecordByOrderId(String orderId);

    /**
     * 根据订单号查询增值服务
     * @param orderIds 订单号
     * @return
     */
    List<OrderAdditionalServices> queryRecordByOrderIds(Set<String> orderIds);

    /**
     * 批量添加订单增值服务
     *
     * @param newestShopAdditionalServicesId
     */
    void batchSaveOrderAdditional(List<OrderAdditionalServices> additionalServices, Map<Integer, Integer> newestShopAdditionalServicesId, Boolean isHasAdditonal);
}
