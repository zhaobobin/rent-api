        
package com.rent.common.converter.marketing;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.rent.common.dto.marketing.OpeNoticeItemDto;
import com.rent.common.dto.marketing.OpeNoticeItemReqDto;
import com.rent.model.marketing.OpeNoticeItem;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 公告常见问题tab内容Service
 *
 * @author youruo
 * @Date 2021-08-16 15:55
 */
public class OpeNoticeItemConverter {

    /**
     * model转dto
     *
     * @param model
     * @return
     */
    public static OpeNoticeItemDto model2Dto(OpeNoticeItem model) {
        if (model == null) {
            return null;
        }
        OpeNoticeItemDto dto = new OpeNoticeItemDto();
        dto.setId(model.getId());
        dto.setCreateTime(model.getCreateTime());
        dto.setTabId(model.getTabId());
        dto.setDeleteTime(model.getDeleteTime());
        dto.setUpdateTime(model.getUpdateTime());
        dto.setIndexSort(model.getIndexSort());
        dto.setName(model.getName());
        dto.setRemark(model.getRemark());
        dto.setDetail(model.getDetail());
        return dto;
    }

    /**
     * dto转do
     *
     * @param dto
     * @return
     */
    public static OpeNoticeItem dto2Model(OpeNoticeItemDto dto) {
        if (dto == null) {
            return null;
        }
        OpeNoticeItem model = new OpeNoticeItem();
        model.setId(dto.getId());
        model.setCreateTime(dto.getCreateTime());
        model.setTabId(dto.getTabId());
        model.setDeleteTime(dto.getDeleteTime());
        model.setUpdateTime(dto.getUpdateTime());
        model.setIndexSort(dto.getIndexSort());
        model.setName(dto.getName());
        model.setRemark(dto.getRemark());
        model.setDetail(dto.getDetail());
        return model;
    }
    /**
     * dto转do
     *
     * @return
     */
    public static OpeNoticeItem dto2ModelV1(Long id) {
        if (id == null) {
            return null;
        }
        OpeNoticeItem model = new OpeNoticeItem();
        model.setId(id);
        model.setDeleteTime(new Date());
        return model;
    }

    /**
     * modelList转dtoList
     *
     * @param modelList
     * @return
     */
    public static List<OpeNoticeItemDto> modelList2DtoList(List<OpeNoticeItem> modelList) {
        if (CollectionUtil.isEmpty(modelList)) {
            return new ArrayList<>(0);
        }
        return Lists.newArrayList(Iterators.transform(modelList.iterator(), OpeNoticeItemConverter::model2Dto));
    }

    /**
     * dto转model
     *
     * @param dto
     * @return
     */
    public static OpeNoticeItem reqDto2Model(OpeNoticeItemReqDto dto) {
        if (dto == null) {
            return null;
        }
        OpeNoticeItem model = new OpeNoticeItem();
            model.setId(dto.getId());
            model.setCreateTime(dto.getCreateTime());
            model.setTabId(dto.getTabId());
            model.setDeleteTime(dto.getDeleteTime());
            model.setUpdateTime(dto.getUpdateTime());
            model.setIndexSort(dto.getIndexSort());
            model.setName(dto.getName());
            model.setRemark(dto.getRemark());
            model.setDetail(dto.getDetail());
        return model;
    }


}