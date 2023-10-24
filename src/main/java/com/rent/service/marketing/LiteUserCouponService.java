        
package com.rent.service.marketing;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.dto.backstage.request.BackstageQueryUserCouponReq;
import com.rent.common.dto.backstage.resp.BackstageQueryUserCouponResp;
import com.rent.common.dto.marketing.UserCouponDto;
import com.rent.common.dto.marketing.UserCouponReqDto;
import com.rent.common.dto.order.UserOrderCouponDto;

import java.util.List;

/**
 * 用户优惠券表Service
 *
 * @author zhao
 * @Date 2020-07-07 15:04
 */
public interface LiteUserCouponService {


        /**
         * 替换用户优惠券表的中uid
         * @param origin
         * @param newUid
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
         * 分页获取
         * @param request
         * @return
         */
        Page<UserCouponDto> queryPage(UserCouponReqDto request);

        /**
         * 获取某个优惠券分配给的用户的手机号码
         * @param templateId
         * @return
         */
        List<String> getAssignRecord(Long templateId);

        /**
         *
         * @param orderId
         * @return
         */
        UserCouponDto getUserCouponByOrderId(String orderId);

        /**
         *
         * @param orderId
         * @param isRecall
         * @return
         */
        List<UserOrderCouponDto> getUserOrderCouponByOrderId(String orderId , Boolean isRecall);

        /**
         * 后台 分页查询某个优惠券的用户领取情况
         * @param request
         * @return
         */
        Page<BackstageQueryUserCouponResp> backstageQueryPage(BackstageQueryUserCouponReq request);
}