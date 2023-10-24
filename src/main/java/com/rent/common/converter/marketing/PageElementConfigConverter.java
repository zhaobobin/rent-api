        
package com.rent.common.converter.marketing;

import cn.hutool.core.collection.CollectionUtil;
import com.rent.common.dto.marketing.PageElementConfigDto;
import com.rent.common.dto.marketing.PageElementConfigRequest;
import com.rent.common.dto.marketing.PageElementConfigResp;
import com.rent.model.marketing.PageElementConfig;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


/**
 * 活动素材Service
 *
 * @author xiaoyao
 * @Date 2020-12-21 15:27
 */
public class PageElementConfigConverter {

    public static PageElementConfig reqDtoToModel(PageElementConfigRequest request) {
        if (request == null) {
            return null;
        }
        PageElementConfig model = new PageElementConfig();
        model.setId(request.getId());
        model.setMaterialCenterItemId(request.getMaterialCenterItemId());
        model.setType(request.getType());
        model.setLink(request.getLink());
        model.setSortNum(request.getSortNum());
        model.setDescribeInfo(request.getDescribeInfo());
        model.setChannelId(request.getChannelId());
        model.setExtCode(request.getExtCode());
        return model;
    }

    public static List<PageElementConfigResp> modelListToRespList(List<PageElementConfig> models,Map<Long,String> materialCenterMap) {
        if (CollectionUtil.isEmpty(models)) {
            return Collections.emptyList();
        }
        List<PageElementConfigResp> resps = new ArrayList<>(models.size());
        for (PageElementConfig model : models) {
            PageElementConfigResp resp = new PageElementConfigResp();
            resp.setId(model.getId());
            resp.setMaterialCenterItemId(model.getMaterialCenterItemId());
            resp.setFileUrl(materialCenterMap.get(resp.getMaterialCenterItemId()));
            resp.setType(model.getType());
            resp.setLink(model.getLink());
            resp.setSortNum(model.getSortNum());
            resp.setDescribeInfo(model.getDescribeInfo());
            resp.setChannelId(model.getChannelId());
            resp.setExtCode(model.getExtCode());
            resps.add(resp);
        }
        return resps;
    }

    public static List<PageElementConfigDto> modelListToDtoList(List<PageElementConfig> models, Map<Long,String> materialCenterItemIdAndUrlMap) {
        if (CollectionUtil.isEmpty(models)) {
            return Collections.emptyList();
        }
        List<PageElementConfigDto> dtoList = new ArrayList<>(models.size());
        for (PageElementConfig model : models) {
            PageElementConfigDto dto = new PageElementConfigDto();
            dto.setFileUrl(materialCenterItemIdAndUrlMap.get(model.getMaterialCenterItemId()));
            dto.setLink(model.getLink());
            dto.setDescribeInfo(model.getDescribeInfo());
            dto.setExtCode(model.getExtCode());
            dtoList.add(dto);
        }
        return dtoList;
    }

}