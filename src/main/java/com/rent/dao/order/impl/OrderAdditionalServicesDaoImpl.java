package com.rent.dao.order.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.order.OrderAdditionalServicesDao;
import com.rent.mapper.order.OrderAdditionalServicesMapper;
import com.rent.model.order.OrderAdditionalServices;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * OrderAdditionalServicesDao
 *
 * @author xiaoyao
 * @Date 2020-06-16 10:08
 */
@Repository
public class OrderAdditionalServicesDaoImpl
    extends AbstractBaseDaoImpl<OrderAdditionalServices, OrderAdditionalServicesMapper>
    implements OrderAdditionalServicesDao {

    @Override
    public List<OrderAdditionalServices> queryRecordByOrderId(String orderId) {
        return this.list(new QueryWrapper<>(OrderAdditionalServices.builder()
            .orderId(orderId)
            .build()));
    }

    @Override
    public List<OrderAdditionalServices> queryRecordByOrderIds(Set<String> orderIds) {
        return this.list(new QueryWrapper<OrderAdditionalServices>().in("order_id",orderIds));
    }

    @Override
    public void batchSaveOrderAdditional(List<OrderAdditionalServices> additionalServices, Map<Integer, Integer> newestShopAdditionalServicesId, Boolean isHasAdditonal) {
        if (isHasAdditonal) {
            Date now = new Date();
            additionalServices.forEach(item -> {
                OrderAdditionalServices services = new OrderAdditionalServices();
                String orderId = item.getOrderId();
                services.setShopAdditionalServicesId(newestShopAdditionalServicesId.get(item.getShopAdditionalServicesId()));
                services.setUpdateTime(now);
                this.update(services, new UpdateWrapper<OrderAdditionalServices>().eq("order_id", orderId).eq("shop_additional_services_id", item.getShopAdditionalServicesId()));

            });
        }
    }
}
