        
package com.rent.common.converter.marketing;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.rent.common.dto.marketing.AddOrUpdateCouponTemplateDto;
import com.rent.common.dto.marketing.CouponTemplateDto;
import com.rent.common.dto.marketing.CouponTemplateIndexDto;
import com.rent.common.dto.marketing.CouponTemplatePageListDto;
import com.rent.common.dto.product.ProductCouponDto;
import com.rent.model.marketing.LiteCouponTemplate;

import java.util.ArrayList;
import java.util.List;


/**
 * 优惠券模版Service
 *
 * @author zhao
 * @Date 2020-07-07 15:04
 */
public class LiteCouponTemplateConverter {

    /**
     * model转dto
     *
     * @param model
     * @return
     */
    public static CouponTemplateDto model2Dto(LiteCouponTemplate model) {
        if (model == null) {
            return null;
        }
        CouponTemplateDto dto = new CouponTemplateDto();
        dto.setId(model.getId());
        dto.setBindId(model.getBindId());
        dto.setTitle(model.getTitle());
        dto.setScene(model.getScene());
        dto.setDisplayNote(model.getDisplayNote());
        dto.setNum(model.getNum());
        dto.setLeftNum(model.getLeftNum());
        dto.setMinAmount(model.getMinAmount());
        dto.setDiscountAmount(model.getDiscountAmount());
        dto.setUserLimitNum(model.getUserLimitNum());
        dto.setStartTime(model.getStartTime());
        dto.setEndTime(model.getEndTime());
        dto.setDelayDayNum(model.getDelayDayNum());
        dto.setForNew(model.getForNew());
        dto.setPackageId(model.getPackageId());
        dto.setType(model.getType());
        dto.setStatus(model.getStatus());
        dto.setCreateTime(model.getCreateTime());
        dto.setDeleteTime(model.getDeleteTime());
        dto.setUpdateTime(model.getUpdateTime());
        dto.setBindUrl(model.getBindUrl());
        return dto;
    }

    public static CouponTemplatePageListDto model2PageListDto(LiteCouponTemplate model) {
        if (model == null) {
            return null;
        }
        CouponTemplatePageListDto dto = new CouponTemplatePageListDto();
        dto.setId(model.getId());
        dto.setBindId(model.getBindId()==null ? model.getId() : model.getBindId());
        dto.setTitle(model.getTitle());
        dto.setScene(model.getScene());
        dto.setDisplayNote(model.getDisplayNote());
        dto.setNum(model.getNum());
        dto.setLeftNum(model.getLeftNum());
        dto.setMinAmount(model.getMinAmount());
        dto.setDiscountAmount(model.getDiscountAmount());
        dto.setUserLimitNum(model.getUserLimitNum());
        dto.setStartTime(model.getStartTime());
        dto.setEndTime(model.getEndTime());
        dto.setDelayDayNum(model.getDelayDayNum());
        dto.setForNew(model.getForNew());
        dto.setPackageId(model.getPackageId());
        dto.setType(model.getType());
        dto.setStatus(model.getStatus());
        dto.setCreateTime(model.getCreateTime());
        dto.setDeleteTime(model.getDeleteTime());
        dto.setUpdateTime(model.getUpdateTime());
        dto.setBindUrl(model.getBindUrl());
        return dto;
    }

    /**
     * model转dto
     *
     * @param model
     * @return
     */
    public static CouponTemplateIndexDto model2IndexDto(LiteCouponTemplate model) {
        if (model == null) {
            return null;
        }
        CouponTemplateIndexDto dto = new CouponTemplateIndexDto();
        dto.setName(model.getTitle());
        dto.setId(model.getId());
        dto.setMinAmount(model.getMinAmount());
        dto.setDiscountAmount(model.getDiscountAmount());
        dto.setStartTime(model.getStartTime());
        dto.setEndTime(model.getEndTime());
        dto.setDelayDayNum(model.getDelayDayNum());
        dto.setBindUrl(model.getBindUrl());
        return dto;
    }

    /**
     * dto转do
     * @param dto
     * @return
     */
    public static LiteCouponTemplate dto2Model(CouponTemplateDto dto) {
        if (dto == null) {
            return null;
        }
        LiteCouponTemplate model = new LiteCouponTemplate();
        model.setId(dto.getId());
        model.setTitle(dto.getTitle());
        model.setScene(dto.getScene());
        model.setDisplayNote(dto.getDisplayNote());
        model.setNum(dto.getNum());
        model.setLeftNum(dto.getLeftNum());
        model.setMinAmount(dto.getMinAmount());
        model.setDiscountAmount(dto.getDiscountAmount());
        model.setUserLimitNum(dto.getUserLimitNum());
        model.setStartTime(dto.getStartTime());
        model.setEndTime(dto.getEndTime());
        model.setDelayDayNum(dto.getDelayDayNum());
        model.setForNew(dto.getForNew());
        model.setType(dto.getType());
        model.setPackageId(dto.getPackageId());
        model.setStatus(dto.getStatus());
        model.setCreateTime(dto.getCreateTime());
        model.setDeleteTime(dto.getDeleteTime());
        model.setUpdateTime(dto.getUpdateTime());
        return model;
    }

    /**
     * modelList转dtoList
     *
     * @param modelList
     * @return
     */
    public static List<CouponTemplateDto> modelList2DtoList(List<LiteCouponTemplate> modelList) {
        if (CollectionUtil.isEmpty(modelList)) {
            return new ArrayList<>(0);
        }
        return Lists.newArrayList(Iterators.transform(modelList.iterator(), LiteCouponTemplateConverter::model2Dto));
    }

    /**
     * modelList转dtoList
     *
     * @param modelList
     * @return
     */
    public static List<CouponTemplatePageListDto> modelList2PageDtoList(List<LiteCouponTemplate> modelList) {
        if (CollectionUtil.isEmpty(modelList)) {
            return new ArrayList<>(0);
        }
        return Lists.newArrayList(Iterators.transform(modelList.iterator(), LiteCouponTemplateConverter::model2PageListDto));
    }

    /**
     * dtoList转doList
     *
     * @param dtoList
     * @return
     */
    public static List<LiteCouponTemplate> dtoList2ModelList(List<CouponTemplateDto> dtoList) {
        if (CollectionUtil.isEmpty(dtoList)) {
            return new ArrayList<>(0);
        }
        return Lists.newArrayList(Iterators.transform(dtoList.iterator(), LiteCouponTemplateConverter::dto2Model));
    }



    /**
     * model转dto
     *
     * @param model
     * @return
     */
    public static ProductCouponDto model2ProductCouponDto(LiteCouponTemplate model) {
        if (model == null) {
            return null;
        }
        ProductCouponDto dto = new ProductCouponDto();
        dto.setTitle(model.getTitle());
        dto.setTemplateId(model.getId());
        dto.setDiscountAmount(model.getDiscountAmount());
        dto.setMinAmount(model.getMinAmount());
        dto.setStartTime(model.getStartTime());
        dto.setEndTime(model.getEndTime());
        dto.setDelayDayNum(model.getDelayDayNum());
        dto.setBindUrl(model.getBindUrl());
        dto.setScene(model.getScene());
        return dto;
    }


    public static LiteCouponTemplate addOrUpdateCouponTemplateDtoToModel(AddOrUpdateCouponTemplateDto dto){
        LiteCouponTemplate model = new LiteCouponTemplate();
        model.setTitle(dto.getTitle());
        model.setScene(dto.getScene());
        model.setDisplayNote(dto.getDisplayNote());
        model.setNum(dto.getNum());
        model.setLeftNum(dto.getLeftNum());
        model.setMinAmount(dto.getMinAmount());
        model.setDiscountAmount(dto.getDiscountAmount());
        model.setUserLimitNum(dto.getUserLimitNum());
        model.setStartTime(dto.getStartTime());
        model.setEndTime(dto.getEndTime());
        model.setDelayDayNum(dto.getDelayDayNum());
        model.setForNew(dto.getForNew());
        model.setType(dto.getType());
        model.setStatus(dto.getStatus());
        model.setChannelId(dto.getChannelId());
        model.setSourceShopId(dto.getSourceShopId());
        model.setSourceShopName(dto.getSourceShopName());
        return model;
    }

    public static AddOrUpdateCouponTemplateDto modelToAddOrUpdateCouponTemplateDto(LiteCouponTemplate model){
        AddOrUpdateCouponTemplateDto dto = new AddOrUpdateCouponTemplateDto();
        dto.setId(model.getId());
        dto.setTitle(model.getTitle());
        dto.setScene(model.getScene());
        dto.setDisplayNote(model.getDisplayNote());
        dto.setNum(model.getNum());
        dto.setLeftNum(model.getLeftNum());
        dto.setMinAmount(model.getMinAmount());
        dto.setDiscountAmount(model.getDiscountAmount());
        dto.setUserLimitNum(model.getUserLimitNum());
        dto.setStartTime(model.getStartTime());
        dto.setEndTime(model.getEndTime());
        dto.setDelayDayNum(model.getDelayDayNum());
        dto.setForNew(model.getForNew());
        dto.setType(model.getType());
        dto.setStatus(model.getStatus());
        dto.setPackageId(model.getPackageId());
        dto.setChannelId(model.getChannelId());
        return dto;
    }

}