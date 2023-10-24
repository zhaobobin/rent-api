        
package com.rent.common.converter.marketing;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.rent.common.dto.marketing.OpeNoticeDto;
import com.rent.common.dto.marketing.OpeNoticeReqDto;
import com.rent.model.marketing.OpeNotice;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 商家中心通知Service
 *
 * @author youruo
 * @Date 2021-08-16 16:10
 */
public class OpeNoticeConverter {

    /**
     * model转dto
     *
     * @param model
     * @return
     */
    public static OpeNoticeDto model2Dto(OpeNotice model) {
        if (model == null) {
            return null;
        }
        OpeNoticeDto dto = new OpeNoticeDto();
        dto.setId(model.getId());
        dto.setMaterialItemId(model.getMaterialItemId());
        dto.setJumpUrl(model.getJumpUrl());
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
    public static OpeNotice dto2Model(OpeNoticeDto dto) {
        if (dto == null) {
            return null;
        }
        OpeNotice model = new OpeNotice();
        model.setId(dto.getId());
        model.setMaterialItemId(dto.getMaterialItemId());
        model.setJumpUrl(dto.getJumpUrl());
        model.setCreateTime(dto.getCreateTime());
        model.setDeleteTime(dto.getDeleteTime());
        model.setUpdateTime(dto.getUpdateTime());
        model.setIndexSort(dto.getIndexSort());
        model.setName(dto.getName());
        model.setRemark(dto.getRemark());
        return model;
    }

    public static OpeNotice dto2ModelV1(Long id) {
        if (id == null) {
            return null;
        }
        OpeNotice model = new OpeNotice();
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
    public static List<OpeNoticeDto> modelList2DtoList(List<OpeNotice> modelList) {
        if (CollectionUtil.isEmpty(modelList)) {
            return new ArrayList<>(0);
        }
        return Lists.newArrayList(Iterators.transform(modelList.iterator(), OpeNoticeConverter::model2Dto));
    }

    /**
     * dto转model
     *
     * @param dto
     * @return
     */
    public static OpeNotice reqDto2Model(OpeNoticeReqDto dto) {
        if (dto == null) {
            return null;
        }
        OpeNotice model = new OpeNotice();
            model.setId(dto.getId());
            model.setMaterialItemId(dto.getMaterialItemId());
            model.setJumpUrl(dto.getJumpUrl());
            model.setCreateTime(dto.getCreateTime());
            model.setDeleteTime(dto.getDeleteTime());
            model.setUpdateTime(dto.getUpdateTime());
            model.setIndexSort(dto.getIndexSort());
            model.setName(dto.getName());
            model.setRemark(dto.getRemark());
        return model;
    }


}