        
package com.rent.service.marketing;


import com.rent.common.dto.marketing.*;
import com.rent.common.dto.product.ProductCouponDto;
import com.rent.common.enums.order.EnumOrderType;
import com.rent.model.marketing.LiteCouponTemplate;

import java.math.BigDecimal;
import java.util.List;

/**
 * 优惠券中心
 * @author zhaowenchao
 */
public interface LiteCouponCenterService {

    /**
     * 小程序领券中心页面接口
     * @param uid
     * @param isNewUser
     * @return
     */
    LiteCouponCenterCouponDto index(String uid, Boolean isNewUser, String scene);

    /**
     * 小程序领券中心页面接口
     * @param uid
     * @param isNewUser
     * @return
     */
    List<LiteCouponCenterPackageDto> indexPackage(String uid, Boolean isNewUser);

    /**
     * 我的优惠券
     * @param uid
     * @param scene
     * @return
     */
    List<MyCouponDto> my(String uid,String scene);
    /**
     * 我的优惠券 已使用
     * @param uid
     * @return
     */
    List<MyCouponDto> myUsed(String uid);
    /**
     * 我的优惠券 已过期
     * @param uid
     * @return
     */
    List<MyCouponDto> myExpire(String uid);

    /**
     * 用户领取优惠券
     * @param uid
     * @param phone
     * @param templateId
     * @param isNewUser
     * @return
     */
    String bindCoupon(String uid, String phone, Long templateId, Boolean isNewUser);

    /**
     * 用户领取大礼包
     * @param uid
     * @param phone
     * @param packageId
     * @param isNewUser
     * @return
     */
    String bindCouponPackage(String uid, String phone, Long packageId,Boolean isNewUser);

    /**
     * 用户在产品页面获取的优惠券
     * @param categoryId
     * @param productId
     * @param uid
     * @param isNewUser
     * @return
     */
    List<ProductCouponDto> getProductCoupon(String categoryId, String productId,String shopId, String uid, Boolean isNewUser);


    /**
     * 下单页面优惠券列表
     * @param categoryId
     * @param productId
     * @param shopId
     * @param uid
     * @param totalAmount
     * @param firstAmount
     * @param orderType
     * @return
     */
    List<OrderCouponDto> getOrderCoupon(String categoryId, String productId, String shopId, String uid, BigDecimal totalAmount, BigDecimal firstAmount, EnumOrderType orderType);


    /**
     * 获取延期优惠券
     * @param categoryId
     * @param productId
     * @param shopId
     * @param uid
     * @return
     */
    List<OrderCouponDto> getOrderExtensionCoupon(String categoryId, String productId, String shopId, String uid);

    /**
     * 更新用户优惠券状态为未使用
     * @param orderId
     * @return
     */
    Boolean updateCouponUnused(String orderId);

    /**
     * 用户提交订单时,根据code获取优惠券信息
     * @param categoryId
     * @param productId
     * @param shopId
     * @param uid
     * @param totalAmount
     * @param firstAmount
     * @param orderType
     * @param code
     * @return
     */
    OrderCouponDto getCouponInfoByCode(String categoryId, String productId, String shopId, String uid, BigDecimal totalAmount, BigDecimal firstAmount, EnumOrderType orderType, String code);


    /**
     * 获取优惠券可用的商品范围
     * @param templateId
     * @return
     */
    CouponRangeDto getCouponRange(Long templateId);


    /**
     * 给用户绑定一张优惠券
     * @param uid
     * @param phone
     * @param receiveType
     * @param couponTemplate
     * @param packageName
     * @param packageId
     * @return
     */
    Boolean bindUserCoupon(String uid, String phone, String receiveType, LiteCouponTemplate couponTemplate, String packageName, Long packageId);

    /**
     * 更新用户优惠券状态未已经使用
     * @param code
     * @param orderId
     * @return
     */
    Boolean updateCouponUsed(String code, String orderId);


}