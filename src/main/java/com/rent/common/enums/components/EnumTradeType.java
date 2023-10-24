package com.rent.common.enums.components;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xiaoyao
 * @version V1.0
 * @date 2020-6-28 下午 1:37:30
 * @since 1.0
 */
@Getter
@AllArgsConstructor
public enum EnumTradeType {
    /** 01:下单 02：关单 03：账单代扣 04：账单主动支付 05：结算单支付 06：充值 07：买断 08：商家分账 */
    GENERAL_PLACE_ORDER("01", "下单"),
    CLOSE_ORDER("02", "关闭订单"),
    BILL_WITHHOLD("03", "账单代扣"),
    USER_PAY_BILL("04", "账单主动支付"),
    SETTLEMENT_PAY("05", "结算单支付"),
    RECHARGE("06", "充值"),
    BUY_OUT("07", "买断"),
    SPLIT_BILL("08", "分账"),
    FINISH_ORDER("09", "订单完成"),
    MANUAL_UNFREEZE("10", "手工解冻"),
    RELET_PLACE_ORDER("11", "续租订单下单"),
    PURCHASE("12", "购买支付"),
    APPOINTMENT("13", "预约支付"),
    DEPOSIT_PAY("14", "押金支付"),
    FIRST_PERIOD("15", "首期租金支付"),
    DEPOSIT_REFUND("16", "押金提现"),
    FIRST_PERIOD_RELET("17", "首期租金支付-续租"),
    SHOP_RECHARGE("18", "商户资金账户充值"),
    TTF_FIRST_PERIOD("20", "统统付首期支付"),
    TTF_REFUND("21", "统统付退款"),
    WX_FIRST_PERIOD("22", "微信首次支付"),
    WX_REFUND("23", "微信退款"),
    TTF_USER_PAY_BILL("24", "统统付账单主动支付"),
    WX_USER_PAY_BILL("25", "微信账单主动支付"),
    TTF_BUY_OUT("26", "统统付买断"),
    WX_BUY_OUT("27", "微信买断"),
    TTF_FIRST_PERIOD_RELET("28", "统统付首期租金支付-续租"),
    WX_FIRST_PERIOD_RELET("29", "微信首期租金支付-续租"),
    TTF_SETTLEMENT_PAY("30", "统统付结算单支付"),
    WX_SETTLEMENT_PAY("31", "微信结算单支付"),
    TTF_DEPOSIT_PAY("32", "统统付押金支付"),
    WX_DEPOSIT_PAY("33", "微信押金支付"),
    WX_DEPOSIT_REFUND("34", "微信押金提现"),
    TTF_DEPOSIT_REFUND("35", "统统付押金提现"),
    TTF_CYCLE_PAY("36", "统统付分期账单代扣"),
    RISK_GENERAL_PLACE_ORDER("37", "常规账单先风控后支付"),


    ;

    /**
     * 状态码
     */
    @EnumValue
    @JsonValue
    private String code;

    /**
     * 状态描述
     */
    private String description;

    /**
     * 根据编码查找枚举
     *
     * @param code 编码
     * @return {@link com.rent.common.enums.components.EnumTradeType } 实例
     **/
    public static EnumTradeType find(String code) {
        for (EnumTradeType instance : EnumTradeType.values()) {
            if (instance.getCode()
                .equals(code)) {
                return instance;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return code;
    }
}