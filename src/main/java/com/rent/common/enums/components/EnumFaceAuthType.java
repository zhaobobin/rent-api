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
public enum EnumFaceAuthType {
    /** 人脸识别状态 */
    VIEW("VIEW", "视频"),
    PNG("PNG", "照片"),
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
     * @return {@link com.hzsx.rent.common.model.enums.components.EnumFaceAuthType } 实例
     **/
    public static EnumFaceAuthType find(String code) {
        for (EnumFaceAuthType instance : EnumFaceAuthType.values()) {
            if (instance.getCode()
                .equals(code)) {
                return instance;
            }
        }
        return null;
    }
}