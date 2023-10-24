package com.rent.common.cache.api;

import com.alibaba.fastjson.JSON;
import com.rent.common.dto.components.dto.OrderContractDto;
import com.rent.util.RedisUtil;

/**
 * 用户下单页面数据缓存，预览合同需要
 * @author zhaowenchao
 */
public class OrderContractDataCache {

    private static final String ORDER_CONTRACT_DATA_KEY = "ORDER_CONFIRM_DATA::";

    /**
     *
     * @param orderId
     * @return
     */
    public static OrderContractDto getOrderContractDataCache(String orderId){
        String key = getKey(orderId);
        Object cache = RedisUtil.get(key);
        if(cache!=null){
            return JSON.parseObject((String) cache,OrderContractDto.class);
        }else {
            return null;
        }
    }

    /**
     *
     * @param orderId
     * @param orderContractDto
     */
    public static void setOrderContractDataCache(String orderId,OrderContractDto orderContractDto){
        String key = getKey(orderId);
        RedisUtil.set(key,JSON.toJSONString(orderContractDto),60);
    }

    private static String getKey(String orderId){
        return ORDER_CONTRACT_DATA_KEY+orderId;
    }

}
