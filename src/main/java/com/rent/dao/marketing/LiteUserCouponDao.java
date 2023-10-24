package com.rent.dao.marketing;

import com.rent.config.mybatis.IBaseDao;
import com.rent.model.marketing.LiteUserCoupon;

import java.util.List;

/**
 * UserCouponDao
 *
 * @author zhao
 * @Date 2020-07-07 15:04
 */
public interface LiteUserCouponDao extends IBaseDao<LiteUserCoupon> {

    /**
     * 查询用户拥有多少个来自该大礼包的优惠券
     * @param uid
     * @param couponPackageId
     * @return
     */
    Integer getUserPackageCount(String uid,Long couponPackageId);

    /**
     * 查询用户拥有多少张该优惠券
     * @param uid
     * @param couponTemplateId
     * @return
     */
    Integer getUserCouponCount(String uid, Long couponTemplateId);

    /**
     * 查询用户拥有多少张未使用的该优惠券
     * @param uid
     * @param couponTemplateId
     * @return
     */
    Integer getUserCouponUnusedCount(String uid, Long couponTemplateId);

    /**
     * 小程序用户中心-我的优惠券
     * @param uid
     * @return
     */
    List<LiteUserCoupon> getUserCoupon(String uid);

    /**
     * 获取用户可以使用的优惠券
     * @param uid
     * @return
     */
    List<LiteUserCoupon> getUserUsableCoupon(String uid);

    /**
     * 获取用户可以使用的优惠券
     * @param uid
     * @return
     */
    List<LiteUserCoupon> getUserUnusedCoupon(String uid);

    /**
     * 获取用户可以使用的优惠券
     * @param uid
     * @return
     */
    List<LiteUserCoupon> getUserUsedCoupon(String uid);

    /**
     * 获取用户可以使用的优惠券
     * @param uid
     * @return
     */
    List<LiteUserCoupon> getUserExpireCoupon(String uid);

    /**
     * 更新优惠券状态为已经失效
     * @param id
     * @return
     */
    Boolean updateCouponInvalid(Long id);

    /**
     * 根据券码获取优惠券信息
     * @param code
     * @return
     */
    LiteUserCoupon getUserCouponByCode(String code);

    /**
     * 取消订单更新优惠券为未使用
     * @param code
     * @return
     */
    Boolean updateCouponUnused(String code);

    /**
     * 获取订单使用的优惠券
     * @param orderId
     * @return
     */
    List<LiteUserCoupon> getUserCouponByOrderId(String orderId);

    /**
     *
     * @param orderId
     * @param isRecall
     * @return
     */
    List<LiteUserCoupon> getAllCouponByOrderId(String orderId, Boolean isRecall);

    /**
     * 替换用户优惠券表的中uid
     * @param origin 原来的uid
     * @param newUid 替换成新的uid
     * @return
     */
    Boolean replaceUid(String origin, String newUid);


    /**
     * 获取绑定的优惠券数量
     * @param templateId
     * @return
     */
    Integer getBindCount(Long templateId);

    /**
     * 获取已经使用的优惠券数量
     * @param templateId
     * @return
     */
    Integer getUsedCount(Long templateId);


    /**
     * 判断用户是否已经拥有该优惠券
     * @param templateIds
     * @param uid
     * @return
     */
    Boolean checkUserHasCoupon(List<Long> templateIds, String uid);

    /**
     * 取某个优惠券分配给的用户的手机号码
     * @param templateId
     * @return
     */
    List<String> getAssignRecord(Long templateId);

    /**
     *
     * @param orderId
     * @param receiveType
     * @return
     */
    LiteUserCoupon getUserCouponByOrderIdAndReceiveType(String orderId,String receiveType);


}
