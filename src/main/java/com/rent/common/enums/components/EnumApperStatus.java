package com.rent.common.enums.components;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 上报状态
 */
@Getter
@AllArgsConstructor
public enum EnumApperStatus {
    /**
     * 描述
     */
    INIT("00", "初始化"),
    PAYING("01", "处理中"),
    SUCCESS("02", "成功"),
    FAILED("03", "失败"),
    CANCEL("04", "取消"),
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
     * @return {@link EnumApperStatus } 实例
     **/
    public static EnumApperStatus find(String code) {
        for (EnumApperStatus instance : EnumApperStatus.values()) {
            if (instance.getCode()
                    .equals(code)) {
                return instance;
            }
        }
        return null;
    }
}