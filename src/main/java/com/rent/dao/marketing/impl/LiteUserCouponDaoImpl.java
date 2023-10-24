package com.rent.dao.marketing.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rent.common.constant.UserCouponConstant;
import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.marketing.LiteUserCouponDao;
import com.rent.mapper.marketing.LiteUserCouponMapper;
import com.rent.model.marketing.LiteUserCoupon;
import com.rent.util.AppParamUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * LiteUserCouponDao
 * @author zhao
 * @Date 2020-07-07 15:04
 */
@Repository
public class LiteUserCouponDaoImpl extends AbstractBaseDaoImpl<LiteUserCoupon, LiteUserCouponMapper> implements
    LiteUserCouponDao {


    @Override
    public Integer getUserPackageCount(String uid,Long couponPackageId) {
        return count(new QueryWrapper<LiteUserCoupon>().eq("package_id",couponPackageId).eq("uid",uid));
    }

    @Override
    public Integer getUserCouponCount(String uid, Long couponTemplateId) {
        return count(new QueryWrapper<LiteUserCoupon>().eq("template_id",couponTemplateId).eq("uid",uid));
    }

    @Override
    public Integer getUserCouponUnusedCount(String uid, Long couponTemplateId) {
        return count(new QueryWrapper<LiteUserCoupon>().eq("template_id",couponTemplateId).eq("uid",uid).eq("status", UserCouponConstant.STATUS_UNUSED));
    }

    @Override
    public List<LiteUserCoupon> getUserCoupon(String uid) {
        return list(new QueryWrapper<LiteUserCoupon>().eq("uid",uid).orderByDesc("discount_amount"));
    }

    @Override
    public List<LiteUserCoupon> getUserUsableCoupon(String uid) {
        Date now = new Date();
        List<LiteUserCoupon> coupons =  list(new QueryWrapper<LiteUserCoupon>()
                .eq("uid",uid)
                .eq("status", UserCouponConstant.STATUS_UNUSED)
                .orderByDesc("discount_amount")
        );
        coupons = coupons.stream().filter(coupon -> coupon.getStartTime().before(now)&&coupon.getEndTime().after(now)).collect(Collectors.toList());
        return coupons;
    }

    @Override
    public List<LiteUserCoupon> getUserUnusedCoupon(String uid) {
        List<LiteUserCoupon> coupons =  list(new QueryWrapper<LiteUserCoupon>()
                .eq("uid",uid)
                .eq("status", UserCouponConstant.STATUS_UNUSED)
                .eq("channel_id", AppParamUtil.getChannelId())
                .orderByDesc("discount_amount")
        );
        return coupons;
    }

    @Override
    public List<LiteUserCoupon> getUserUsedCoupon(String uid) {
        List<LiteUserCoupon> coupons =  list(new QueryWrapper<LiteUserCoupon>()
                .eq("uid",uid)
                .eq("status", UserCouponConstant.STATUS_USED)
                .eq("channel_id", AppParamUtil.getChannelId())
                .orderByDesc("discount_amount")
        );
        return coupons;
    }

    @Override
    public List<LiteUserCoupon> getUserExpireCoupon(String uid) {
        List<LiteUserCoupon> coupons =  list(new QueryWrapper<LiteUserCoupon>()
                .eq("uid",uid)
                .eq("status", UserCouponConstant.STATUS_INVALID)
                .eq("channel_id", AppParamUtil.getChannelId())
                .orderByDesc("discount_amount")
        );
        return coupons;
    }

    @Override
    public List<LiteUserCoupon> getUserCouponByOrderId(String orderId) {
        return list(new QueryWrapper<LiteUserCoupon>().eq("order_id",orderId).eq("status",UserCouponConstant.STATUS_USED));
    }

    @Override
    public List<LiteUserCoupon> getAllCouponByOrderId(String orderId, Boolean isRecall) {
        if(null != isRecall && isRecall){
            return list(new QueryWrapper<LiteUserCoupon>().eq("order_id",orderId));
        }else{
            return list(new QueryWrapper<LiteUserCoupon>().eq("order_id",orderId).notIn("receive_type",UserCouponConstant.BIND_TYPE_TIME_ORDER));
        }
    }


    @Override
    public Boolean updateCouponUnused(String code) {
        baseMapper.updateCouponUnused(code);
        return Boolean.TRUE;
    }

    @Override
    public Boolean updateCouponInvalid(Long id) {
        LiteUserCoupon userCoupon = new LiteUserCoupon();
        userCoupon.setId(id);
        userCoupon.setStatus(UserCouponConstant.STATUS_INVALID);
        return updateById(userCoupon);
    }

    @Override
    public LiteUserCoupon getUserCouponByCode(String code) {

        return getOne(new QueryWrapper<LiteUserCoupon>().eq("code",code));
    }

    @Override
    public Boolean replaceUid(String origin, String newUid) {
        return baseMapper.replaceUid(origin,newUid);
    }

    @Override
    public Integer getBindCount(Long templateId) {
        return count(new QueryWrapper<LiteUserCoupon>().eq("template_id",templateId));
    }

    @Override
    public Integer getUsedCount(Long templateId) {
        return count(new QueryWrapper<LiteUserCoupon>().eq("template_id",templateId).eq("status",UserCouponConstant.STATUS_USED));
    }

    @Override
    public Boolean checkUserHasCoupon(List<Long> templateIds, String uid) {
        List<LiteUserCoupon> coupons =  list(new QueryWrapper<LiteUserCoupon>()
                .select("id")
                .eq("uid",uid)
                .in("template_id",templateIds));
        return CollectionUtils.isNotEmpty(coupons);
    }

    @Override
    public List<String> getAssignRecord(Long templateId) {
        List<LiteUserCoupon> coupons =  list(new QueryWrapper<LiteUserCoupon>()
                .select("phone")
                .eq("template_id",templateId)
                .eq("receive_type",UserCouponConstant.BIND_TYPE_ASSIGN));
        return coupons.stream().map(LiteUserCoupon::getPhone).collect(Collectors.toList());
    }

    @Override
    public LiteUserCoupon getUserCouponByOrderIdAndReceiveType(String orderId, String receiveType) {
        LiteUserCoupon coupons =  getOne(new QueryWrapper<LiteUserCoupon>()
                .eq("order_id",orderId)
                .eq("status",UserCouponConstant.STATUS_UNUSED)
                .in("receive_type",receiveType)
                .orderByDesc("receive_time")
                .last("limit 1")
        );
        return coupons;
    }
}
