package com.rent.common.cache.api;

import com.alibaba.fastjson.JSON;
import com.rent.common.dto.vo.IndexActionListByPageVo;
import com.rent.common.dto.vo.TabProductListVo;
import com.rent.util.RedisUtil;
import com.rent.util.StringUtil;

/**
 * @author zhaowenchao
 */
public class IndexCache {

    private static final String ALIPAY_CHANNEL_INDEX = "index:action:";
    private static final String ALIPAY_CHANNEL_TAB = "index:tab:";
    private static final String ALIPAY_CHANNEL_NOTICE = "notice:api:content:";




    public static IndexActionListByPageVo getIndexActionCache(String channelId,int pageNum,int pageSize){
        Object cache = RedisUtil.get(geIndexActionKey(channelId,pageNum,pageSize));
        if (cache != null) {
            return JSON.parseObject(cache.toString(),IndexActionListByPageVo.class);
        }else {
            return null;
        }
    }

    public static void setIndexActionCache(String channelId, int pageNum, int pageSize, IndexActionListByPageVo ob){
        RedisUtil.set(geIndexActionKey(channelId,pageNum,pageSize), JSON.toJSONString(ob), 120);
    }

    private static String geIndexActionKey(String channelId,int pageNum,int pageSize){
        return  ALIPAY_CHANNEL_INDEX + channelId + pageNum + pageSize;
    }



    public static TabProductListVo getIndexTabCache(String channelId, Long tabId, int pageNum, int pageSize){
        Object cache = RedisUtil.get(geIndexTabKey(channelId,tabId,pageNum,pageSize));
        if (cache != null) {
            return JSON.parseObject(cache.toString(),TabProductListVo.class);
        }else {
            return null;
        }
    }

    public static void setIndexTabCache(String channelId,Long tabId,int pageNum,int pageSize,TabProductListVo ob){
        RedisUtil.set(geIndexTabKey(channelId,tabId,pageNum,pageSize), JSON.toJSONString(ob), 300);
    }

    private static String geIndexTabKey(String channelId,Long tabId,int pageNum,int pageSize){
        return  ALIPAY_CHANNEL_TAB + tabId + channelId + pageNum + pageSize;
    }




    public static String getIndexApiNoticeCache(String channelId){
        Object cache = RedisUtil.get(geIndexApiNoticeKey(channelId));
        if (cache != null) {
            return String.valueOf(cache);
        }else {
            return null;
        }
    }

    public static void setIndexApiNoticeCache(String channelId,String content){
        if(StringUtil.isEmpty(content)){
            RedisUtil.del(geIndexApiNoticeKey(channelId));
        }else {
            RedisUtil.set(geIndexApiNoticeKey(channelId), content);
        }
    }

    private static String geIndexApiNoticeKey(String channelId){
        return  ALIPAY_CHANNEL_NOTICE + channelId;
    }

}
