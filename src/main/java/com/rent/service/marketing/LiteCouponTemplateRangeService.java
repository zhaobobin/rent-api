        
package com.rent.service.marketing;


import com.rent.common.dto.marketing.AddOrUpdateCouponTemplateDto;
import com.rent.model.marketing.LiteCouponTemplateRange;

import java.util.List;

/**
 * 优惠券使用范围Service
 *
 * @author zhao
 * @Date 2020-07-07 15:04
 */
public interface LiteCouponTemplateRangeService {

        /**
         * 新增优惠券使用范围
         * @param request
         * @param couponTemplateId
         */
        void addCouponTemplateRange(AddOrUpdateCouponTemplateDto request, Long couponTemplateId);


        /**
         * 根据优惠券模板ID获取到优惠券使用范围
         * @param templateId
         * @return
         */
        List<LiteCouponTemplateRange> getCouponTemplateRangeByTemplateId(Long templateId);

}