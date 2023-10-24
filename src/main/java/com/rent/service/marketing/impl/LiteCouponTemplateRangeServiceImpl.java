        
package com.rent.service.marketing.impl;


import com.rent.common.dto.marketing.AddOrUpdateCouponTemplateDto;
import com.rent.common.dto.marketing.CouponRangeReqDto;
import com.rent.common.enums.marketing.CouponRangeConstant;
import com.rent.common.enums.user.EnumBackstageUserPlatform;
import com.rent.dao.marketing.LiteCouponTemplateRangeDao;
import com.rent.model.marketing.LiteCouponTemplateRange;
import com.rent.service.marketing.LiteCouponTemplateRangeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 优惠券使用范围Service
 *
 * @author zhao
 * @Date 2020-07-07 15:04
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class LiteCouponTemplateRangeServiceImpl implements LiteCouponTemplateRangeService {

    private final LiteCouponTemplateRangeDao liteCouponTemplateRangeDao;

    @Override
    public void addCouponTemplateRange(AddOrUpdateCouponTemplateDto request, Long couponTemplateId) {
        Date now = new Date();
        if(CouponRangeConstant.RANGE_TYPE_ALL.equals(request.getRangeType())){
            if(EnumBackstageUserPlatform.OPE.getCode().equals(request.getSourceShopId())){
                LiteCouponTemplateRange couponTemplateRange =  new LiteCouponTemplateRange();
                couponTemplateRange.setTemplateId(couponTemplateId);
                couponTemplateRange.setType(CouponRangeConstant.RANGE_TYPE_ALL);
                couponTemplateRange.setValue(CouponRangeConstant.RANGE_TYPE_ALL);
                couponTemplateRange.setDescription(request.getSourceShopName());
                couponTemplateRange.setCreateTime(now);
                couponTemplateRange.setChannelId(request.getChannelId());
                liteCouponTemplateRangeDao.save(couponTemplateRange);
                return;
            }else {
                LiteCouponTemplateRange couponTemplateRange =  new LiteCouponTemplateRange();
                couponTemplateRange.setTemplateId(couponTemplateId);
                couponTemplateRange.setType(CouponRangeConstant.RANGE_TYPE_SHOP);
                couponTemplateRange.setValue(request.getSourceShopId());
                couponTemplateRange.setDescription(request.getSourceShopName());
                couponTemplateRange.setCreateTime(now);
                couponTemplateRange.setChannelId(request.getChannelId());
                liteCouponTemplateRangeDao.save(couponTemplateRange);
                return;
            }
        }else {
            List<CouponRangeReqDto> rangeList = request.getRangeList();
            for (CouponRangeReqDto couponRangeReqDto : rangeList) {
                LiteCouponTemplateRange couponTemplateRange =  new LiteCouponTemplateRange();
                couponTemplateRange.setType(request.getRangeType());
                couponTemplateRange.setTemplateId(couponTemplateId);
                couponTemplateRange.setType(request.getRangeType());
                couponTemplateRange.setValue(couponRangeReqDto.getValue());
                couponTemplateRange.setDescription(couponRangeReqDto.getDescription());
                couponTemplateRange.setCreateTime(now);
                couponTemplateRange.setChannelId(request.getChannelId());
                liteCouponTemplateRangeDao.save(couponTemplateRange);
            }
        }
    }

    @Override
    public List<LiteCouponTemplateRange> getCouponTemplateRangeByTemplateId(Long templateId) {
        return liteCouponTemplateRangeDao.getCouponTemplateRangeByTemplateId(templateId);
    }
}