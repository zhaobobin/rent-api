package com.rent.common.enums.order;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 催收结果
 */
@Getter
@AllArgsConstructor
public enum EnumOrderResult {
    /** 描述 */
    PROMISE_REPAY("01", "承诺还款"),
    EXTENSION("02", "申请延期还款"),
    REFUSE_REPAY("03","拒绝还款"),
    NO_ANSWER("04","电话无人接听"),
    REJECTION("05","电话拒接"),
    TURN_OFF("06","电话关机"),
    SUSPENDED("07","电话停机"),
    LOST_CONTACT("08","客户失联"),
    ;

    /** 状态码 */
    @JsonValue
    @EnumValue
    private String code;

    /** 状态描述 */
    private String description;

    /**
     * 根据编码查找枚举
     *
     * @param code 编码
     * @return {@link  EnumOrderResult } 实例
     **/
    public static  EnumOrderResult find(String code) {
        for ( EnumOrderResult instance :  EnumOrderResult.values()) {
            if (instance.getCode()
                .equals(code)) {
                return instance;
            }
        }
        return null;
    }
}