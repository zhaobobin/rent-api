package com.rent.common.enums.components;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xiaoyao
 * @version V1.0
 * @date 2020-6-28 下午 1:26:32
 * @since 1.0
 */
@Getter
@AllArgsConstructor
public enum EnumPayType {
    /**
     * 01：预授权冻结 02：预授权转支付 03：订单创建 04：APP支付 05：退款 06：解冻 07：转账
     */
    FREEZE("01", "预授权冻结"),
    FREEZE_TO_PAY("02", "预授权转支付"),
    TRADE_CREATE("03", "订单创建"),
    APP_PAY("04", "APP支付"),
    PAGE_PAY("08", "PAGE支付"),
    REFUND("05", "退款"),
    UNFREEZE("06", "解冻"),
    TRANSFER("07", "转账"),
    TTF_FIRST_PAY("10", "统统付支付"),
    TTF_REFUND("11", "统统付退款"),
    WX_FIRST_PAY("12", "微信支付"),
    WX_REFUND("13", "微信退款"),
    YFB_PAY("14", "易付宝代扣"),
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
     * @return {@link EnumPayType } 实例
     **/
    public static EnumPayType find(String code) {
        for (EnumPayType instance : EnumPayType.values()) {
            if (instance.getCode()
                .equals(code)) {
                return instance;
            }
        }
        return null;
    }
}