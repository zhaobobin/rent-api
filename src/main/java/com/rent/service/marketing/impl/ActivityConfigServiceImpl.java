package com.rent.service.marketing.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rent.common.converter.marketing.ColumnConfigConverter;
import com.rent.common.dto.marketing.ActivityConfigListDto;
import com.rent.common.dto.marketing.ActivityConfigReqDto;
import com.rent.dao.marketing.ColumnConfigDao;
import com.rent.model.marketing.ColumnConfig;
import com.rent.service.marketing.ActivityConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author xiaotong
 */
@Service
@Slf4j
public class ActivityConfigServiceImpl implements ActivityConfigService {

    private ColumnConfigDao columnConfigDao;

    @Autowired
    public ActivityConfigServiceImpl(ColumnConfigDao columnConfigDao) {
        this.columnConfigDao = columnConfigDao;
    }


    @Override
    public List<ActivityConfigListDto> list(String channelId) {
        List<ColumnConfig> list = columnConfigDao.list(new QueryWrapper<ColumnConfig>()
                .eq("type","ACTIVITY")
                .orderByDesc("create_time"));
        List<ActivityConfigListDto> result = ColumnConfigConverter.modelList2DtoListForActivity(list);
        return result;
    }

    @Override
    public ActivityConfigListDto detail(Long id) {
        ColumnConfig columnConfig = columnConfigDao.getById(id);
        return ColumnConfigConverter.model2DtoForActivity(columnConfig);
    }

    @Override
    public Boolean update(ActivityConfigReqDto dto) {
        ColumnConfig columnConfig = columnConfigDao.getById(dto.getId());
        columnConfig.setUrl(dto.getUrl());
        columnConfig.setJumpUrl(dto.getJumpUrl());
        columnConfig.setUpdateTime(new Date());
        return columnConfigDao.updateById(columnConfig);
    }
}