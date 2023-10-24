package com.rent.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EnumBankCardType {
    DEBIT("DEBIT", "储蓄卡"),

    CREDIT("CREDIT", "信用卡"),
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
     * @return {@link EnumByteSignEntQualificationType } 实例
     **/
    public static EnumByteSignEntQualificationType find(String code) {
        for (EnumByteSignEntQualificationType instance : EnumByteSignEntQualificationType.values()) {
            if (instance.getCode()
                    .equals(code)) {
                return instance;
            }
        }
        return null;
    }

}
