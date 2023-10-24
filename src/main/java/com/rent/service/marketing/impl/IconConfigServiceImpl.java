package com.rent.service.marketing.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.rent.common.converter.marketing.IconConfigConverter;
import com.rent.common.dto.marketing.IconConfigAddReqDto;
import com.rent.common.dto.marketing.IconConfigListDto;
import com.rent.dao.marketing.IconConfigDao;
import com.rent.model.marketing.IconConfig;
import com.rent.service.marketing.IconConfigService;
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
public class IconConfigServiceImpl implements IconConfigService {

    private IconConfigDao iconConfigDao;

    @Autowired
    public IconConfigServiceImpl(IconConfigDao iconConfigDao) {
        this.iconConfigDao = iconConfigDao;
    }

    @Override
    public List<IconConfigListDto> list(String channelId) {
        List<IconConfig> iconConfigList = iconConfigDao.list(new QueryWrapper<IconConfig>()
                .eq("channel_id",channelId)
                .isNull("delete_time")
                .orderByAsc("sort")
        );
        return IconConfigConverter.modelList2DtoList(iconConfigList);
    }

    @Override
    public IconConfigListDto detail(Long id) {
        IconConfig iconConfig = iconConfigDao.getById(id);
        return IconConfigConverter.model2Dto(iconConfig);
    }

    @Override
    public Boolean add(IconConfigAddReqDto dto) {
        IconConfig iconConfig = new IconConfig();
        iconConfig.setCreateTime(new Date());
        iconConfig.setTitle(dto.getTitle());
        iconConfig.setChannelId(dto.getChannelId());
        iconConfig.setJumpUrl(dto.getJumpUrl());
        iconConfig.setUrl(dto.getUrl());
        iconConfig.setSort(dto.getSort());
        iconConfig.setTag(dto.getTag());
        return iconConfigDao.save(iconConfig);
    }

    @Override
    public Boolean update(IconConfigAddReqDto dto) {
        IconConfig iconConfig = iconConfigDao.getById(dto.getId());
        iconConfig.setUpdateTime(new Date());
        iconConfig.setUrl(dto.getUrl());
        iconConfig.setTitle(dto.getTitle());
        iconConfig.setJumpUrl(dto.getJumpUrl());
        iconConfig.setSort(dto.getSort());
        iconConfig.setTag(dto.getTag());
        return iconConfigDao.updateById(iconConfig);
    }

    @Override
    public Boolean delete(Long id) {
        IconConfig iconConfig = iconConfigDao.getById(id);
        iconConfig.setDeleteTime(new Date());
        return iconConfigDao.updateById(iconConfig);
    }


}