package com.rent.service.order;

import com.rent.common.dto.order.response.BuyOutOrderPageDto;
import com.rent.common.dto.order.response.BuyOutOrderPayResponse;
import com.rent.common.dto.order.resquest.UserOrderBuyOutReqDto;
import com.rent.common.enums.components.EnumAliPayStatus;

/**
 * 用户买断Service
 *
 * @author xiaoyao
 * @Date 2020-06-23 14:50
 */
public interface UserOrderBuyOutService {

    /**
     * 买断之前展示页面数据
     *
     * @param orderId 订单编号
     * @return 买断订单页展示数据
     */
    BuyOutOrderPageDto buyOutPage(String orderId);
    /**
     * 买断之前展示页面数据
     *
     * @param request 订单编号
     * @return 买断订单页展示数据
     */
    BuyOutOrderPageDto buyOutTrial(UserOrderBuyOutReqDto request);

    /**
     * 买断订单支付
     *
     * @param orderId 订单id
     * @param couponId 优惠券id
     * @return
     */
    BuyOutOrderPayResponse liteBuyOutOrderPay(String orderId, String couponId);

    /**
     * 买断回调处理
     *
     * @return void
     * @Author xiaoyao
     * @Date 18:01 2020-4-30
     * @Param outTransNo 商户订单号
     **/
    void buyOutPayedCallBack(String outTradeNo, String orderId, String tradeNo, EnumAliPayStatus payStatus);


}