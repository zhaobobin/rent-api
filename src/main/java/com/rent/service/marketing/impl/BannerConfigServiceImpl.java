package com.rent.service.marketing.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rent.common.converter.marketing.BannerConfigConverter;
import com.rent.common.dto.marketing.BannerConfigAddReqDto;
import com.rent.common.dto.marketing.BannerConfigListDto;
import com.rent.dao.marketing.BannerConfigDao;
import com.rent.model.marketing.BannerConfig;
import com.rent.service.marketing.BannerConfigService;
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
public class BannerConfigServiceImpl implements BannerConfigService {

    private BannerConfigDao bannerConfigDao;

    @Autowired
    public BannerConfigServiceImpl(BannerConfigDao bannerConfigDao) {
        this.bannerConfigDao = bannerConfigDao;
    }


    @Override
    public List<BannerConfigListDto> list(String channelId, String type) {
        List<BannerConfig> list = bannerConfigDao.list(new QueryWrapper<BannerConfig>()
                .eq("API".equals(type),"open_status",1)
                .ge("API".equals(type),"end_time",new Date())
                .le("API".equals(type),"begin_time",new Date())
                .eq("channel_id",channelId)
                .isNull("delete_time")
                .orderByDesc("API".equals(type),"sort"));
        return BannerConfigConverter.modelList2DtoList(list);
    }

    @Override
    public BannerConfigListDto detail(Long id) {
        BannerConfig bannerConfig = bannerConfigDao.getById(id);
        return BannerConfigConverter.model2Dto(bannerConfig);
    }

    @Override
    public Boolean add(BannerConfigAddReqDto dto) {
//        List<BannerConfig> list = bannerConfigDao.list(new QueryWrapper<BannerConfig>()
//                .eq("channel_id",dto.getChannelId()));
//        if(list.size() == 5){
//            throw new HzsxBizException("-1","最多添加5张Banner图片");
//        }
        BannerConfig bannerConfig = new BannerConfig();
        bannerConfig.setUrl(dto.getUrl());
        bannerConfig.setJumpUrl(dto.getJumpUrl());
        bannerConfig.setSort(dto.getSort());
        bannerConfig.setChannelId(dto.getChannelId());
        bannerConfig.setOpenStatus(dto.getOpenStatus());
        bannerConfig.setBeginTime(dto.getBeginTime());
        bannerConfig.setEndTime(dto.getEndTime());
        bannerConfig.setCreateTime(new Date());
        return bannerConfigDao.save(bannerConfig);
    }

    @Override
    public Boolean update(BannerConfigAddReqDto dto) {
        BannerConfig bannerConfig = bannerConfigDao.getById(dto.getId());
        bannerConfig.setUpdateTime(new Date());
        bannerConfig.setUrl(dto.getUrl());
        bannerConfig.setJumpUrl(dto.getJumpUrl());
        bannerConfig.setSort(dto.getSort());
        bannerConfig.setBeginTime(dto.getBeginTime());
        bannerConfig.setEndTime(dto.getEndTime());
        return bannerConfigDao.updateById(bannerConfig);
    }

    @Override
    public Boolean open(BannerConfigAddReqDto dto) {
        BannerConfig bannerConfig = bannerConfigDao.getById(dto.getId());
        bannerConfig.setUpdateTime(new Date());
        bannerConfig.setOpenStatus(dto.getOpenStatus());
        return bannerConfigDao.updateById(bannerConfig);
    }

    @Override
    public Boolean delete(BannerConfigAddReqDto dto) {
        BannerConfig bannerConfig = bannerConfigDao.getById(dto.getId());
        bannerConfig.setDeleteTime(new Date());
        return bannerConfigDao.updateById(bannerConfig);
    }


}