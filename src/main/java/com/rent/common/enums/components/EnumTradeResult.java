package com.rent.common.enums.components;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xiaoyao
 * @version V1.0
 * @date 2020-6-29 下午 2:31:53
 * @since 1.0
 */
@Getter
@AllArgsConstructor
public enum EnumTradeResult {
    /** 描述 */
    SUCCESS("1", "成功"),
    FAILED("2", "失败"),
    LIMIT_AMOUNT("3", "限额"),
    ;

    /** 状态码 */
    @EnumValue
    @JsonValue
    private String code;

    /** 状态描述 */
    private String description;

    /**
     * 根据编码查找枚举
     *
     * @param code 编码
     * @return {@link com.hzsx.rent.common.model.enums.components.EnumTradeResult } 实例
     **/
    public static EnumTradeResult find(String code) {
        for (EnumTradeResult instance : EnumTradeResult.values()) {
            if (instance.getCode()
                .equals(code)) {
                return instance;
            }
        }
        return null;
    }
}