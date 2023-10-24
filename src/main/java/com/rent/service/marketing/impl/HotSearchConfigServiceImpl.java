package com.rent.service.marketing.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rent.common.cache.marketing.HotSearchCache;
import com.rent.common.dto.marketing.HotSearchSaveReqDto;
import com.rent.dao.marketing.HotSearchConfigDao;
import com.rent.model.marketing.HotSearchConfig;
import com.rent.service.marketing.HotSearchConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhaowenchao
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class HotSearchConfigServiceImpl implements HotSearchConfigService {

    private final HotSearchConfigDao hotSearchConfigDao;

    @Override
    public List<String> list(String channelId) {
        List<String> hotSearchList = HotSearchCache.getHotSearchCache(channelId);
        if(CollectionUtils.isEmpty(hotSearchList)){
            hotSearchList = listFromDb(channelId);
            if(CollectionUtils.isNotEmpty(hotSearchList)){
                HotSearchCache.setHotSearchCache(channelId,hotSearchList);
            }
        }
        return hotSearchList;
    }

    @Override
    public Boolean save(HotSearchSaveReqDto request) {
        Date now = new Date();
        //删除以前的
        HotSearchConfig temp = new HotSearchConfig();
        temp.setDeleteTime(now);
        hotSearchConfigDao.update(temp,new QueryWrapper<HotSearchConfig>().eq("channel_id",request.getChannelId()).isNull("delete_time"));
        //保存新的
        int indexSort = 0;
        List<HotSearchConfig> saveList = new ArrayList<>(request.getWords().size());
        for (String word : request.getWords()) {
            if(StringUtils.isEmpty(word)){
                continue;
            }
            HotSearchConfig model = new HotSearchConfig();
            model.setChannelId(request.getChannelId());
            model.setWord(word);
            model.setIndexSort(indexSort++);
            model.setCreateTime(now);
            saveList.add(model);
        }
        hotSearchConfigDao.saveBatch(saveList);
        //更新缓存
        List<String> list = listFromDb(request.getChannelId());
        HotSearchCache.setHotSearchCache(request.getChannelId(),list);
        return Boolean.TRUE;
    }

    public List<String> listFromDb(String channelId){
        List<HotSearchConfig> list = hotSearchConfigDao.list(
                new QueryWrapper<HotSearchConfig>().select("word")
                        .eq("channel_id",channelId)
                        .isNull("delete_time")
                        .orderByAsc("index_sort")
        );
        return list.stream().map(HotSearchConfig::getWord).collect(Collectors.toList());
    }
}