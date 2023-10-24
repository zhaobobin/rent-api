package com.rent.common.cache.product;


import com.rent.util.RedisUtil;

/**
 * @author zhaowenchao
 */
public class ProductSalesCache {

    private static final String HOT_SEARCH_KEY = "PRODUCT_SALES";

    public static Integer getProductSales(String itemId){
        Object count = RedisUtil.hget(HOT_SEARCH_KEY,itemId);
        return count==null ? null : Integer.parseInt((String) count) ;
    }

    public static void setProductSales(String itemId,Integer initValue){
        if(initValue==null){
            return;
        }
        RedisUtil.hset(HOT_SEARCH_KEY,itemId,initValue.toString());
    }

    public static void incrProductSales(String itemId){
        RedisUtil.hincr(HOT_SEARCH_KEY,itemId,1);
    }

    public static boolean hasInit(String itemId){
        return RedisUtil.hHasKey(HOT_SEARCH_KEY,itemId);
    }



}
