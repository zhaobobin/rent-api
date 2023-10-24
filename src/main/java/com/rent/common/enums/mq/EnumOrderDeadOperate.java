package com.rent.common.enums.mq;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author zhaowenchao
 */
@Getter
@AllArgsConstructor
public enum EnumOrderDeadOperate {

    EXPIRATION("EXPIRATION", "订单失效"),
    ORDER_SMS_SUCCESS("ORDER_SMS_SUCCESS", "支付成功短信发送"),

    ;


    @JsonValue
    @EnumValue
    private String code;
    private String msg;

    public static EnumOrderDeadOperate find(Integer code) {
        return Arrays.stream(EnumOrderDeadOperate.values())
                .filter(input -> input.getCode()
                        .equals(code))
                .findFirst()
                .orElse(null);
    }
}
