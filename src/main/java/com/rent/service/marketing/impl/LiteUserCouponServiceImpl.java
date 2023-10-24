        
package com.rent.service.marketing.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.constant.CouponTemplateConstant;
import com.rent.common.converter.marketing.LiteUserCouponConverter;
import com.rent.common.dto.backstage.request.BackstageQueryUserCouponReq;
import com.rent.common.dto.backstage.resp.BackstageQueryUserCouponResp;
import com.rent.common.dto.marketing.UserCouponDto;
import com.rent.common.dto.marketing.UserCouponReqDto;
import com.rent.common.dto.order.UserOrderCouponDto;
import com.rent.common.enums.marketing.UserCouponConstant;
import com.rent.common.enums.order.EnumPlatformType;
import com.rent.common.enums.user.EnumBackstageUserPlatform;
import com.rent.dao.marketing.LiteCouponTemplateDao;
import com.rent.dao.marketing.LiteUserCouponDao;
import com.rent.model.marketing.LiteCouponTemplate;
import com.rent.model.marketing.LiteUserCoupon;
import com.rent.service.marketing.LiteUserCouponService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户优惠券表Service
 *
 * @author zhao
 * @Date 2020-07-07 15:04
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class LiteUserCouponServiceImpl implements LiteUserCouponService {

    private final LiteUserCouponDao liteUserCouponDao;
    private final LiteCouponTemplateDao liteCouponTemplateDao;

    @Override
    public Boolean replaceUid(String origin, String newUid) {
        return liteUserCouponDao.replaceUid(origin,newUid);
    }

    @Override
    public Integer getBindCount(Long templateId) {
        return liteUserCouponDao.getBindCount(templateId);
    }

    @Override
    public Integer getUsedCount(Long templateId) {
        return liteUserCouponDao.getUsedCount(templateId);
    }

    @Override
    public Page<UserCouponDto> queryPage(UserCouponReqDto request) {
        Page<LiteUserCoupon> page = liteUserCouponDao.page(new Page<>(request.getPageNumber(), request.getPageSize()),
                new QueryWrapper<>(LiteUserCouponConverter.reqDto2Model(request)));

        return new Page<UserCouponDto>(page.getCurrent(), page.getSize(), page.getTotal()).setRecords(
                LiteUserCouponConverter.modelList2DtoList(page.getRecords()));
    }

    @Override
    public List<String> getAssignRecord(Long templateId) {
        return liteUserCouponDao.getAssignRecord(templateId);
    }

    @Override
    public UserCouponDto getUserCouponByOrderId(String orderId) {
        LiteUserCoupon userCoupon = liteUserCouponDao.getUserCouponByOrderIdAndReceiveType(orderId, UserCouponConstant.BIND_TYPE_TIME_ORDER);
        if(null != userCoupon){
            Long templateId =  userCoupon.getTemplateId();
            LiteCouponTemplate couponTemplate =  liteCouponTemplateDao.selectByBindIdForUpdate(templateId);
            if(null != couponTemplate && CouponTemplateConstant.STATUS_VALID.equals(couponTemplate.getStatus())){
                return LiteUserCouponConverter.model2Dto(userCoupon);
            }
        }
        return null;
    }

    @Override
    public List<UserOrderCouponDto> getUserOrderCouponByOrderId(String orderId, Boolean isRecall) {
        List<UserOrderCouponDto> result = Lists.newArrayList();
        List<LiteUserCoupon> userCoupon = liteUserCouponDao.getAllCouponByOrderId(orderId,isRecall);
        if(CollectionUtils.isNotEmpty(userCoupon)){
            userCoupon.forEach(item ->{
                UserOrderCouponDto couponDto = new UserOrderCouponDto();
                couponDto.setUid(item.getUid());
                couponDto.setDiscountAmount(item.getDiscountAmount());
                LiteCouponTemplate couponTemplate =  liteCouponTemplateDao.getById(item.getTemplateId());
                if(null != couponTemplate){
                    couponDto.setPlatform(couponTemplate.getSourceShopId().contains(EnumBackstageUserPlatform.OPE.getCode()) ? EnumPlatformType.REGULAR_OPE : EnumPlatformType.REGULAR_SHOP);
                    couponDto.setCouponName(couponTemplate.getTitle());
                }
                result.add(couponDto);
            });
        }
        return result;
    }

    @Override
    public Page<BackstageQueryUserCouponResp> backstageQueryPage(BackstageQueryUserCouponReq request) {
        Page<LiteUserCoupon> page = liteUserCouponDao.page(
                new Page<>(request.getPageNumber(), request.getPageSize()),
                new QueryWrapper<LiteUserCoupon>()
                        .eq("template_id",request.getTemplateId())
                        .eq(StringUtils.isNotEmpty(request.getOrderId()),"order_id",request.getOrderId())
                        .eq(StringUtils.isNotEmpty(request.getStatus()),"status",request.getStatus())
        );

        return new Page<BackstageQueryUserCouponResp>(page.getCurrent(), page.getSize(), page.getTotal()).setRecords(
                LiteUserCouponConverter.modelList2BackstageQueryUserCouponResp(page.getRecords()));
    }

}