package com.rent.common.cache.product;

import com.rent.util.RedisUtil;

/**
 * 刷单商品缓存
 * @author zhaowenchao
 */
public class ProductSwipingCache {

    private static final String SWIPING_ACTIVITY_PRODUCT = "SWIPING_ACTIVITY_PRODUCT";

    /**
     * 刷单商品新增
     * @param productId
     */
    public static void add(String productId){
        RedisUtil.sSet(SWIPING_ACTIVITY_PRODUCT,productId);
    }

    /**
     * 移除刷单商品
     * @param productId
     */
    public static void remove(String productId){
        RedisUtil.setRemove(SWIPING_ACTIVITY_PRODUCT,productId);
    }

    /**
     * 是否是刷单商品
     * @param productId
     * @return
     */
    public static Boolean contains(String productId){
        return RedisUtil.sHasKey(SWIPING_ACTIVITY_PRODUCT,productId);
    }
}
