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
public enum ByteSignOrderContractNotifyType {

    USER("USER", "用户"),
    SHOP("SHOP", "店铺"),
    PLATFORM("PLATFORM", "平台"),

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
     * @return {@link ByteSignOrderContractNotifyType } 实例
     **/
    public static ByteSignOrderContractNotifyType find(String code) {
        for (ByteSignOrderContractNotifyType instance : ByteSignOrderContractNotifyType.values()) {
            if (instance.getCode()
                .equals(code)) {
                return instance;
            }
        }
        return null;
    }
}