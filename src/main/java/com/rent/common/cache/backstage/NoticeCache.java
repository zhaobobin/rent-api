package com.rent.common.cache.backstage;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rent.common.dto.backstage.NoticeDto;
import com.rent.util.RedisUtil;
import org.apache.commons.lang.StringUtils;

/**
 * @author zhaowenchao
 */
public class NoticeCache {

    private static final String BACKSTAGE_NOTICE_KEY = "BACKSTAGE_NOTICE";


    public static NoticeDto getNoticeCache(){
        Object cache = RedisUtil.get(BACKSTAGE_NOTICE_KEY);
        if (cache != null) {
            return JSONObject.parseObject(cache.toString(),NoticeDto.class);
        }else {
            return new NoticeDto();
        }
    }

    public static void setNoticeCache(NoticeDto notice){
        if(null != notice && StringUtils.isNotEmpty(notice.getTitle()) && StringUtils.isNotEmpty(notice.getDetail())){
            RedisUtil.set(BACKSTAGE_NOTICE_KEY,JSON.toJSONString(notice));
        }else{
            RedisUtil.del(BACKSTAGE_NOTICE_KEY);
        }
    }

}
