    
package com.rent.dao.product.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rent.common.dto.order.OrderAdditionalServicesDto;
import com.rent.common.dto.product.AdditonalDto;
import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.product.ShopAdditionalServicesDao;
import com.rent.mapper.product.ShopAdditionalServicesMapper;
import com.rent.model.product.ShopAdditionalServices;
import org.apache.commons.compress.utils.Lists;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * ShopAdditionalServicesDao
 *
 * @author youruo
 * @Date 2020-06-17 10:35
 */
@Repository
public class ShopAdditionalServicesDaoImpl extends AbstractBaseDaoImpl<ShopAdditionalServices, ShopAdditionalServicesMapper> implements ShopAdditionalServicesDao {


    @Override
    public AdditonalDto saveBatchAdditional(List<Integer> shopAdditionalServicesId, String transferShopId, List<OrderAdditionalServicesDto> orderAdditionalServicesDtos) {
        Date now = new Date();
        AdditonalDto result = new AdditonalDto();
        Map<Integer, Integer> newestShopAdditionalMap = new HashMap<Integer, Integer>();
        Map<Integer, BigDecimal> orderShopAdditionalMap = new HashMap<Integer, BigDecimal>();
        if (CollectionUtil.isNotEmpty(orderAdditionalServicesDtos)) {
            orderShopAdditionalMap = orderAdditionalServicesDtos.stream().collect(Collectors.toMap(OrderAdditionalServicesDto::getShopAdditionalServicesId, OrderAdditionalServicesDto::getPrice));
        }
        List<ShopAdditionalServices> services = Lists.newArrayList();
        List<ShopAdditionalServices> shopAdditionalServices = list(new QueryWrapper<ShopAdditionalServices>().in("id", shopAdditionalServicesId));
        Map<Integer, BigDecimal> finalOrderShopAdditionalMap = orderShopAdditionalMap;
        shopAdditionalServices.forEach(x -> {
            Integer id = x.getId();
            if (!finalOrderShopAdditionalMap.isEmpty()) {
                if (finalOrderShopAdditionalMap.keySet().contains(id)) {
                    ShopAdditionalServices additionalServices = this.getOne(new QueryWrapper<ShopAdditionalServices>()
                            .eq("shop_id", transferShopId)
                            .and(QueryWrapper -> QueryWrapper.eq("original_add_id", id)
                                    .or().eq("id", id))
                            .eq("price", finalOrderShopAdditionalMap.get(id))
                            .orderByDesc("id")
                            .last("limit 1")
                    );
                    if (null != additionalServices) {
                        services.add(additionalServices);
                        newestShopAdditionalMap.put(id, additionalServices.getId());
                    } else {
                        x.setShopId(transferShopId);
                        x.setCreateTime(now);
                        x.setUpdateTime(now);
                        //增值服务转单标记
                        x.setStatus(1);
                        x.setPrice(finalOrderShopAdditionalMap.get(id));
                        x.setOriginalAddId(id);
                        this.save(x);
                        Integer afterId = x.getId();
                        services.add(x);
                        newestShopAdditionalMap.put(id, afterId);
                    }
                }
            }
        });
        result.setServices(services);
        result.setNewestShopAdditionalMap(newestShopAdditionalMap);
        return result;

    }
}
