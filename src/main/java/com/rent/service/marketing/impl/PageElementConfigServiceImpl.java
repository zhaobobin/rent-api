package com.rent.service.marketing.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rent.common.cache.marketing.PageElementConfigCache;
import com.rent.common.converter.marketing.PageElementConfigConverter;
import com.rent.common.dto.marketing.PageElementConfigDto;
import com.rent.common.dto.marketing.PageElementConfigRequest;
import com.rent.common.dto.marketing.PageElementConfigResp;
import com.rent.common.enums.marketing.PageElementConfigTypeEnum;
import com.rent.dao.marketing.MaterialCenterItemDao;
import com.rent.dao.marketing.PageElementConfigDao;
import com.rent.model.marketing.MaterialCenterItem;
import com.rent.model.marketing.PageElementConfig;
import com.rent.service.marketing.PageElementConfigService;
import com.rent.util.AppParamUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zhaowenchao
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class PageElementConfigServiceImpl implements PageElementConfigService {

    private final PageElementConfigDao pageElementConfigDao;
    private final MaterialCenterItemDao materialCenterItemDao;

    @Override
    public Long add(PageElementConfigRequest request) {
        PageElementConfig model = PageElementConfigConverter.reqDtoToModel(request);
        model.setCreateTime(new Date());
        pageElementConfigDao.save(model);
        updateCache(request.getChannelId(),request.getType());
        return model.getId();
    }

    @Override
    public Long delete(Long id) {
        PageElementConfig model = pageElementConfigDao.getById(id);
        model.setDeleteTime(new Date());
        model.setId(id);
        pageElementConfigDao.updateById(model);
        updateCache(model.getChannelId(),model.getType());
        return model.getId();
    }

    @Override
    public Long update(PageElementConfigRequest request) {
        PageElementConfig model = PageElementConfigConverter.reqDtoToModel(request);
        pageElementConfigDao.updateById(model);
        updateCache(request.getChannelId(),request.getType());
        return model.getId();
    }

    @Override
    public List<PageElementConfigResp> list(PageElementConfigRequest request) {
        List<PageElementConfig> list = pageElementConfigDao.getByChannelIDAndType(request.getChannelId(),request.getType());
        Map<Long,String> materialCenterMap = new HashMap<>();
        if(CollectionUtils.isNotEmpty(list)){
            List<Long> materialCenterItemId = list.stream().map(PageElementConfig::getMaterialCenterItemId).collect(Collectors.toList());
            materialCenterMap = materialCenterItemDao.getMaterialCenterFileUrl(materialCenterItemId);
        }
        return PageElementConfigConverter.modelListToRespList(list,materialCenterMap);
    }

    @Override
    public List<PageElementConfigDto> listBanner() {
        return listForApi(AppParamUtil.getChannelId(),PageElementConfigTypeEnum.INDEX_BANNER);
    }

    @Override
    public List<PageElementConfigDto> listIconArea() {
        return listForApi(AppParamUtil.getChannelId(),PageElementConfigTypeEnum.ICON_AREA);
    }

    @Override
    public List<PageElementConfigDto> listSpecialAreaMain() {
        return listForApi(AppParamUtil.getChannelId(),PageElementConfigTypeEnum.SPECIAL_AREA_MAIN);
    }

    @Override
    public List<PageElementConfigDto> listSpecialAreaSub() {
        return listForApi(AppParamUtil.getChannelId(),PageElementConfigTypeEnum.SPECIAL_AREA_SUB);
    }

    @Override
    public String getSpecialTitleMain() {
        List<PageElementConfigDto> list = listForApi(AppParamUtil.getChannelId(),PageElementConfigTypeEnum.SPECIAL_TITLE_MAIN);
        if(CollectionUtils.isNotEmpty(list)){
            return list.get(0).getDescribeInfo();
        }else {
            return null;
        }
    }

    @Override
    public String getSpecialTitleSub() {
        List<PageElementConfigDto> list = listForApi(AppParamUtil.getChannelId(),PageElementConfigTypeEnum.SPECIAL_TITLE_SUB);
        if(CollectionUtils.isNotEmpty(list)){
            return list.get(0).getDescribeInfo();
        }else {
            return null;
        }
    }

    @Override
    public List<PageElementConfigDto> listMyService() {
        return listForApi(AppParamUtil.getChannelId(),PageElementConfigTypeEnum.MY_SERVICE);
    }

    @Override
    public List<PageElementConfigDto> listMyOrder() {
        return listForApi(AppParamUtil.getChannelId(),PageElementConfigTypeEnum.MY_ORDER);
    }

    /**
     * 从缓存中查询页面配置，缓存中没有就从数据库查询，并更新到缓存中
     * @param channelId
     * @param type
     * @return
     */
    private List<PageElementConfigDto> listForApi(String channelId, PageElementConfigTypeEnum type) {
        List<PageElementConfigDto> dto = PageElementConfigCache.getPageElementConfig(channelId,type);
        if(CollectionUtils.isNotEmpty(dto)){
            return dto;
        }
        return updateCache(channelId,type);
    }

    /**
     * 查询页面配置，并更新缓存
     * @param channelId
     * @param type
     * @return
     */
    private List<PageElementConfigDto> updateCache(String channelId, PageElementConfigTypeEnum type){
        List<PageElementConfig> list = pageElementConfigDao.getByChannelIDAndType(channelId,type);
        if(CollectionUtils.isEmpty(list)){
            return Collections.emptyList();
        }

        //查询材料路径信息
        List<Long> materialCenterItemIdList = list.stream().map(PageElementConfig::getMaterialCenterItemId).collect(Collectors.toList());
        List<MaterialCenterItem> materialCenterItemList = materialCenterItemDao.list(new QueryWrapper<MaterialCenterItem>().select("id,file_url").in("id",materialCenterItemIdList));
        Map<Long,String>  materialCenterItemIdAndUrlMap = materialCenterItemList.stream().collect(Collectors.toMap(MaterialCenterItem::getId,MaterialCenterItem::getFileUrl));

        //刷新缓存
        List<PageElementConfigDto> dtoList = PageElementConfigConverter.modelListToDtoList(list,materialCenterItemIdAndUrlMap);
        PageElementConfigCache.setPageElementConfig(channelId,type,dtoList);
        return dtoList;

    }
}