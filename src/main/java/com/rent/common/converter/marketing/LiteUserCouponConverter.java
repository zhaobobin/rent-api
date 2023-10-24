        
package com.rent.common.converter.marketing;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.rent.common.dto.backstage.resp.BackstageQueryUserCouponResp;
import com.rent.common.dto.marketing.UserCouponDto;
import com.rent.common.dto.marketing.UserCouponReqDto;
import com.rent.model.marketing.LiteUserCoupon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 用户优惠券表Service
 *
 * @author zhao
 * @Date 2020-07-07 15:04
 */
public class LiteUserCouponConverter {

    /**
     * model转dto
     *
     * @param model
     * @return
     */
    public static UserCouponDto model2Dto(LiteUserCoupon model) {
        if (model == null) {
            return null;
        }
        UserCouponDto dto = new UserCouponDto();
        dto.setId(model.getId());
        dto.setTemplateId(model.getTemplateId());
        dto.setDiscountAmount(model.getDiscountAmount());
        dto.setCode(model.getCode());
        dto.setUid(model.getUid());
        dto.setPhone(model.getPhone());
        dto.setReceiveTime(model.getReceiveTime());
        dto.setReceiveType(model.getReceiveType());
        dto.setStartTime(model.getStartTime());
        dto.setEndTime(model.getEndTime());
        dto.setOrderId(model.getOrderId());
        dto.setUseTime(model.getUseTime());
        dto.setPackageId(model.getPackageId());
        dto.setPackageName(model.getPackageName());
        dto.setStatus(model.getStatus());
        return dto;
    }

    /**
     * modelList转dtoList
     *
     * @param modelList
     * @return
     */
    public static List<UserCouponDto> modelList2DtoList(List<LiteUserCoupon> modelList) {
        if (CollectionUtil.isEmpty(modelList)) {
            return new ArrayList<>(0);
        }
        return Lists.newArrayList(Iterators.transform(modelList.iterator(), LiteUserCouponConverter::model2Dto));
    }

    /**
     * dto转model
     *
     * @param dto
     * @return
     */
    public static LiteUserCoupon reqDto2Model(UserCouponReqDto dto) {
        if (dto == null) {
            return null;
        }
        LiteUserCoupon model = new LiteUserCoupon();
        model.setId(dto.getId());
        model.setTemplateId(dto.getTemplateId());
        model.setDiscountAmount(dto.getDiscountAmount());
        model.setCode(dto.getCode());
        model.setUid(dto.getUid());
        model.setPhone(dto.getPhone());
        model.setReceiveTime(dto.getReceiveTime());
        model.setReceiveType(dto.getReceiveType());
        model.setStartTime(dto.getStartTime());
        model.setEndTime(dto.getEndTime());
        model.setOrderId(dto.getOrderId());
        model.setUseTime(dto.getUseTime());
        model.setPackageId(dto.getPackageId());
        model.setPackageName(dto.getPackageName());
        model.setStatus(dto.getStatus());
        return model;
    }


    public static List<BackstageQueryUserCouponResp> modelList2BackstageQueryUserCouponResp(List<LiteUserCoupon> records) {
        if (CollectionUtil.isEmpty(records)) {
            return Collections.EMPTY_LIST;
        }
        List<BackstageQueryUserCouponResp> dtoList = new ArrayList<>(records.size());
        for (LiteUserCoupon model : records) {
            BackstageQueryUserCouponResp dto = new BackstageQueryUserCouponResp();
            dto.setCode(model.getCode());
            dto.setPhone(model.getPhone());
            dto.setReceiveTime(model.getReceiveTime());
            dto.setReceiveType(model.getReceiveType());
            dto.setOrderId(model.getOrderId());
            dto.setUseTime(model.getUseTime());
            dto.setStatus(model.getStatus());
            dtoList.add(dto);
        }
        return dtoList;
    }
}