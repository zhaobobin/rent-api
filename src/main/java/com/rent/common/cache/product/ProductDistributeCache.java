package com.rent.common.cache.product;

import com.alibaba.fastjson.JSONObject;
import com.rent.util.RedisUtil;

import java.util.List;

/**
 * 芝麻商品和租物租商品映射
 * @author zhaowenchao
 */
public class ProductDistributeCache {

    private static final String PRODUCT_DISTRIBUTE = "PRODUCT_DISTRIBUTE::";
    private static final String SEAM_PRODUCT_ID_MAP = "SEAM_PRODUCT_ID_MAP::";

    public static void init(String virtualProductId, List<Object> realProductId){
        RedisUtil.del(virtualProductId);
        RedisUtil.lSet(PRODUCT_DISTRIBUTE+virtualProductId,realProductId);
    }


    public static String loop(String virtualProductId){
        Object object = RedisUtil.loop(PRODUCT_DISTRIBUTE+virtualProductId);
        return object==null ? null : object.toString();
    }


    public static void del(String virtualProductId){
        RedisUtil.del(PRODUCT_DISTRIBUTE+virtualProductId);
    }

    public static void setSeamProduct(String channelId,String realProduct,String seamProductId){
        RedisUtil.hset(SEAM_PRODUCT_ID_MAP+channelId,realProduct,seamProductId);
    }

    public static JSONObject getSeamProduct(String channelId, String realProduct){
        Object object = RedisUtil.hget(SEAM_PRODUCT_ID_MAP+channelId,realProduct);
        if(object!=null){
            return JSONObject.parseObject((String)object);
        }else{
            return null;
        }
    }

}
