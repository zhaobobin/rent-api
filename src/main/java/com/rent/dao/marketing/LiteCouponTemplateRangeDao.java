        
package com.rent.dao.marketing;

import com.rent.common.dto.marketing.CouponRangeDto;
import com.rent.config.mybatis.IBaseDao;
import com.rent.model.marketing.LiteCouponTemplateRange;

import java.util.List;

/**
 * CouponTemplateRangeDao
 *
 * @author zhao
 * @Date 2020-07-07 15:04
 */
public interface LiteCouponTemplateRangeDao extends IBaseDao<LiteCouponTemplateRange> {


    /**
     * 获取范围内的优惠券模板ID
     * @param type
     * @param value
     * @return
     */
    List<LiteCouponTemplateRange> getInRangeTemplateId(String type, String value);

    /**
     * 根据模板信息获取
     * @param templateId
     * @return
     */
    CouponRangeDto getCouponRange(Long templateId);

    /**
     * 获取优惠券使用范围类型
     * @param templateId
     * @return
     */
    String getCouponRangeType(Long templateId);

    /**
     * 根据优惠券模板ID获取到优惠券使用范围
     * @param templateId
     * @return
     */
    List<LiteCouponTemplateRange> getCouponTemplateRangeByTemplateId(Long templateId);
}
