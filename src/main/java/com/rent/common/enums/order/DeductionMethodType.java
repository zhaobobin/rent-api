package com.rent.common.enums.order;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 扣款方式
 */
@Getter
@AllArgsConstructor
public enum DeductionMethodType {

    AUTH("AUTH", "预授权扣款"),
    CYCLE("CYCLE", "周期扣款"),
    BANK("BANK", "银行卡扣款"),
    SUNING_BANK("SUNING_BANK", "银行卡扣款"),
    UNKNOWN("UNKNOWN", "未知扣款"),
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

    public static DeductionMethodType find(String code) {
        for (DeductionMethodType instance : DeductionMethodType.values()) {
            if (instance.getCode()
                .equals(code)) {
                return instance;
            }
        }
        return null;
    }
}
