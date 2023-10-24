package com.rent.common.cache.marketing;

import com.rent.util.RedisUtil;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

/**
 * @author zhaowenchao
 */
public class HotSearchCache {

    private static final String HOT_SEARCH_KEY = "HOT_SEARCH:";

    public static List<String> getHotSearchCache(String channelId){
        String key = HOT_SEARCH_KEY+channelId;
        List cache = RedisUtil.lGet(key,0,-1);
        return cache;
    }

    public static void setHotSearchCache(String channelId,List hotSearch){
        if(CollectionUtils.isEmpty(hotSearch)){
            return;
        }
        String key = HOT_SEARCH_KEY+channelId;
        RedisUtil.del(key);
        RedisUtil.lSet(key,hotSearch);
    }

}
