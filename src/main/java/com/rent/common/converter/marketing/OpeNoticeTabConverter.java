        
package com.rent.common.converter.marketing;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.rent.common.dto.marketing.OpeNoticeTabDto;
import com.rent.common.dto.marketing.OpeNoticeTabReqDto;
import com.rent.model.marketing.OpeNoticeTab;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 公告常见问题tabService
 *
 * @author youruo
 * @Date 2021-08-16 15:55
 */
public class OpeNoticeTabConverter {

    /**
     * model转dto
     *
     * @param model
     * @return
     */
    public static OpeNoticeTabDto model2Dto(OpeNoticeTab model) {
        if (model == null) {
            return null;
        }
        OpeNoticeTabDto dto = new OpeNoticeTabDto();
        dto.setId(model.getId());
        dto.setCreateTime(model.getCreateTime());
        dto.setDeleteTime(model.getDeleteTime());
        dto.setUpdateTime(model.getUpdateTime());
        dto.setIndexSort(model.getIndexSort());
        dto.setName(model.getName());
        dto.setRemark(model.getRemark());
        return dto;
    }

    /**
     * dto转do
     *
     * @param dto
     * @return
     */
    public static OpeNoticeTab dto2Model(OpeNoticeTabDto dto) {
        if (dto == null) {
            return null;
        }
        OpeNoticeTab model = new OpeNoticeTab();
        model.setId(dto.getId());
        model.setCreateTime(dto.getCreateTime());
        model.setDeleteTime(dto.getDeleteTime());
        model.setUpdateTime(dto.getUpdateTime());
        model.setIndexSort(dto.getIndexSort());
        model.setName(dto.getName());
        model.setRemark(dto.getRemark());
        return model;
    }

    public static OpeNoticeTab dto2ModelV1(Long id) {
        if (id == null) {
            return null;
        }
        OpeNoticeTab model = new OpeNoticeTab();
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
    public static List<OpeNoticeTabDto> modelList2DtoList(List<OpeNoticeTab> modelList) {
        if (CollectionUtil.isEmpty(modelList)) {
            return new ArrayList<>(0);
        }
        return Lists.newArrayList(Iterators.transform(modelList.iterator(), OpeNoticeTabConverter::model2Dto));
    }


    /**
     * dto转model
     *
     * @param dto
     * @return
     */
    public static OpeNoticeTab reqDto2Model(OpeNoticeTabReqDto dto) {
        if (dto == null) {
            return null;
        }
        OpeNoticeTab model = new OpeNoticeTab();
            model.setId(dto.getId());
            model.setCreateTime(dto.getCreateTime());
            model.setDeleteTime(dto.getDeleteTime());
            model.setUpdateTime(dto.getUpdateTime());
            model.setIndexSort(dto.getIndexSort());
            model.setName(dto.getName());
            model.setRemark(dto.getRemark());
        return model;
    }


}