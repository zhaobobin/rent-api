package com.rent.common.cache.product;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rent.exception.HzsxBizException;
import com.rent.util.DateUtil;
import com.rent.util.RedisUtil;

import java.util.Date;


/**
 * @author zhaowenchao
 */
public class ProductSortScoreCache {

    private static final String ADD_SORT_SCORE = "ADD_SORT_SCORE";
    private static final String PRODUCT_SCORE_SET = "PRODUCT_SCORE_SET";

    public static final String expireTimeKey = "expireTime";
    public static final String addScoreKey = "addScore";

    public static void setAddSortScore(String productId, Integer addScore, Date expireTime){
        long expireSeconds = DateUtil.differDateInDays(new Date(),expireTime, DateUtil.TimeType.SECOND);
        if(expireSeconds<0){
            throw new HzsxBizException("-1","过期时间选择错误");
        }
        JSONObject data = new JSONObject();
        data.put(expireTimeKey,DateUtil.date2String(expireTime,DateUtil.DATETIME_FORMAT_1));
        data.put(addScoreKey,addScore);
        RedisUtil.set(ADD_SORT_SCORE+productId,data.toJSONString(),expireSeconds);
    }

    public static Integer getAddScore(String productId){
        JSONObject data = getAddScoreInfo(productId);
        return data == null ? null : data.getInteger(addScoreKey);
    }

    public static JSONObject getAddScoreInfo(String productId){
        Object obj =  RedisUtil.get(ADD_SORT_SCORE+productId);
        return obj == null ? null : JSON.parseObject((String)obj);
    }


    public static void setSysScore(String productId,double sysScore){
        RedisUtil.zAdd(PRODUCT_SCORE_SET,productId,sysScore);
    }

    public static void cleanSysScore(){
        RedisUtil.del(PRODUCT_SCORE_SET);
    }

    public static int getRank(String productId){
        Long rank = RedisUtil.zReverseRank(PRODUCT_SCORE_SET,productId);
        if(rank==null){
            return Integer.MAX_VALUE;
        }
        return rank.intValue()+1;
    }

}
