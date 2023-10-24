package com.rent.config.outside;

import com.rent.common.enums.common.EnumComponentsError;
import com.rent.common.enums.components.EnumPayType;
import com.rent.exception.HzsxBizException;

/**
 * @author xiaoyao
 * @version V1.0
 * @date 2020-7-1 下午 2:50:47
 * @since 1.0
 */
public class AliPayNotifyFactory {
    //预授权冻结回调
    public static final String aliFreezeCallbackUrl = OutsideConfig.DOMAIN + "/zyj-component/callBack/aliPayCapitalNotify/aliPayFreezeCallback";
    //分期租金-冻结转支付回调
    public static final String stageOrderAlipayCallback = OutsideConfig.DOMAIN + "/zyj-component/callBack/aliPayCapitalNotify/stageOrderAlipayCallback";
    //网页支付重定向地址
    public static final String pagePayReturnUrl = OutsideConfig.DOMAIN + "business/#/finance/capitalAccount";
    //预授权解冻回调
    public static final String aliUnFreezeCallbackUrl = OutsideConfig.DOMAIN + "/zyj-component/callBack/aliPayCapitalNotify/alipayUnFreezeCallBack";
    //订单支付回调
    public static final String tradeCreateCallBackUrl = OutsideConfig.DOMAIN + "/zyj-component/callBack/aliPayCapitalNotify/alipayTradeCreateCallback";
    //网页支付回调
    public static final String pagePayCallBackUrl = OutsideConfig.DOMAIN + "/zyj-component/callBack/aliPayCapitalNotify/alipayTradePagePayCallback";
    //续租首期冻结转支付回调
    public static final String aliTradePayCallbackUrl = OutsideConfig.DOMAIN + "/zyj-component/callBack/aliPayCapitalNotify/alipayTradePayCallback";


    public static String getAliPayNotifyUrl(EnumPayType payType) {
        switch (payType) {
            case FREEZE:
                return aliFreezeCallbackUrl;
            case UNFREEZE:
                return aliUnFreezeCallbackUrl;
            case TRADE_CREATE:
                return tradeCreateCallBackUrl;
            case PAGE_PAY:
                return pagePayCallBackUrl;
            default:
                throw new HzsxBizException(EnumComponentsError.PARAMS_CHECK_ERROR.getCode(),
                        EnumComponentsError.PARAMS_CHECK_ERROR.getMsg());
        }
    }


}
