package com.rent.common.constant;

/**
 * @author xiaoyao
 * @version V1.0
 * @date 2020-7-20 下午 5:48:21
 * @since 1.0
 */
public class SmsConfigConstants {
    /**
     * 支付失败关单
     */
    public final static String ORDER_PAY_FAIL_CLOSE = "ORDER_PAY_FAIL_CLOSE";
    /**
     * 买断支付成功
     */
    public final static String ORDER_BUY_OUT_SUCCESS = "ORDER_BUY_OUT_SUCCESS";
    /**
     * 订单支付成功
     */
    public final static String ORDER_PAY_SUCCESS = "ORDER_PAY_SUCCESS";
    /**
     * 订单关闭
     */
//    public final static String ORDER_PAY_PLANT_CLOSE = "ORDER_PAY_PLANT_CLOSE";
    public final static String ORDER_PAY_PLANT_CLOSE = "ORDER_PALT_CLOSE";
    /**
     * 实名认证
     */
    public final static String REAL_NAME = "REAL_NAME";
    /**
     * 商家发货
     */
    public final static String BUSINESS_DELIVERY = "BUSINESS_DELIVERY";
    /**
     * 平台关单
     */
    public final static String ORDER_PALT_CLOSE = "ORDER_PALT_CLOSE";

    /**
     * 续租支付成功-通知用户 支付宝租物租、抖音租物租的短信
     */
    public final static String RELET_ORDER_PAY_SUCCESS = "RELET_ORDER_PAY_SUCCESS";
    /**
     * 续租支付成功-通知商家
     */
    public final static String RELET_TO_BUSINESS = "RELET_TO_BUSINESS";

    public final static String BUSINESS_UPDATE_PASSWORD = "BUSINESS_UPDATE_PASSWORD";


    /**
     * 风控关单
     */
    public final static String RISK_CLOSE = "RISK_CLOSE";

    public final static String ORDER_BUSINESS_BUY_OUT_SUCCESS = "ORDER_BUSINESS_BUY_OUT_SUCCESS";
}
