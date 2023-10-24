package com.rent.common.enums.components;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xiaoyao
 * @version V1.0
 * @date 2020-7-2 下午 2:04:15
 * @since 1.0
 */
@Getter
@AllArgsConstructor
public enum EnumFaceAuthStatus {
    /** 人脸识别状态 */
    INIT("00", "初始化"),
    SUCCESS("01", "成功"),
    FAILED("02", "失败"),
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
     * @return {@link com.hzsx.rent.common.model.enums.components.EnumFaceAuthStatus } 实例
     **/
    public static EnumFaceAuthStatus find(String code) {
        for (EnumFaceAuthStatus instance : EnumFaceAuthStatus.values()) {
            if (instance.getCode()
                .equals(code)) {
                return instance;
            }
        }
        return null;
    }
}