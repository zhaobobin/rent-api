        
package com.rent.service.marketing;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.dto.marketing.*;


/**
 * 优惠券模版Service
 *
 * @author zhao
 * @Date 2020-07-07 15:04
 */
public interface LiteCouponTemplateService {

        /**
         * 新增优惠券模版
         *
         * @param request 条件
         * @return boolean
         */
        Long addCouponTemplate(AddOrUpdateCouponTemplateDto request);

        /**
         * 编辑优惠券信息
         * @param request
         * @return
         */
        Boolean modifyCouponTemplate(AddOrUpdateCouponTemplateDto request);

        /**
         * 获取编辑页面的数据
         * @param id
         * @return
         */
        AddOrUpdateCouponTemplateDto getCouponTemplateUpdatePageData(Long id);

        /**
         * 获取优惠券详情页面数据
         * @param id
         * @return
         */
        CouponTemplatePageDto getCouponTemplateDetail(Long id);

        /**
         * 删除优惠券
         * @param id
         * @return
         */
        Boolean deleteCouponTemplate(Long id);

        /**
         * <p>
         * 根据条件列表
         * </p>
         *
         * @param request 实体对象
         * @return CouponTemplate
         */
        Page<CouponTemplatePageListDto> queryCouponTemplatePage(CouponTemplateReqDto request);

        /**
         * 获取可以分配给大礼包的优惠券
         * @param request
         * @return
         */
        Page<CouponTemplatePageListDto> getAssignAbleTemplate(CouponTemplateReqDto request);

        /**
         * 给优惠券添加领取链接
         * @param request
         * @return
         */
        Boolean addBindUrl(CouponAddBindUrlReqDto request);

        /**
         * 券码券 券码下载
         * @param templateId
         * @return
         */
        String exportEntityNum(Long templateId);
}
