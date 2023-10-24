package com.rent.common.cache.marketing;

import com.alibaba.fastjson.JSON;
import com.rent.common.dto.marketing.PageElementConfigDto;
import com.rent.common.enums.marketing.PageElementConfigTypeEnum;
import com.rent.util.RedisUtil;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author zhaowenchao
 */
public class PageElementConfigCache {

    private static final String PAGE_ELEMENT_CONFIG = "PAGE_ELEMENT_CONFIG:";

    public static List<PageElementConfigDto> getPageElementConfig(String channelId, PageElementConfigTypeEnum type){
        String key = getKey(channelId,type);
        List cache = RedisUtil.lGet(key,0,-1);
        List<PageElementConfigDto> dtoList = new ArrayList<>(cache.size());
        for (Object o : cache) {
            dtoList.add(JSON.parseObject((String)o,PageElementConfigDto.class));
        }
        return dtoList;
    }

    public static void setPageElementConfig(String channelId, PageElementConfigTypeEnum type,List<PageElementConfigDto> dtoList){
        if(CollectionUtils.isEmpty(dtoList)){
            return;
        }
        String key = getKey(channelId,type);
        List list = new ArrayList<>(dtoList.size());
        for (PageElementConfigDto dto : dtoList) {
            list.add(JSON.toJSONString(dto));
        }
        RedisUtil.del(key);
        RedisUtil.lSet(key,list);
    }


    public static void clear(){
        Set<String> keySet = RedisUtil.keys(PAGE_ELEMENT_CONFIG+"*");
        RedisUtil.del(keySet);
    }

    private static String getKey(String channelId, PageElementConfigTypeEnum type){
        return PAGE_ELEMENT_CONFIG+channelId+type.getCode();
    }

}
