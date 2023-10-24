package com.rent.common.enums.order;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description: 支付方式
 */
@Getter
@AllArgsConstructor
public enum PaymentMethod {

    ZFB("ZFB", "支付宝"),
    YFB("YFB", "易付宝"),
    VALET_PAYMENT("VALET_PAYMENT", "代客支付"),
    MORTGAGE("MORTGAGE", "押金抵扣"),
    ;

    /**
     * 状态码
     */
    @JsonValue
    @EnumValue
    private String code;


    /**
     * 状态描述
     */
    private String description;


    public static PaymentMethod find(String code) {
        for (PaymentMethod instance : PaymentMethod.values()) {
            if (instance.getCode()
                    .equals(code)) {
                return instance;
            }
        }
        return null;
    }
}
