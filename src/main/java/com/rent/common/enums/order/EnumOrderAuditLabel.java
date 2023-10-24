package com.rent.common.enums.order;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xiaoyao
 * @version V1.0
 * @date 2020-8-6 下午 4:54:29
 * @since 1.0
 */
@Getter
@AllArgsConstructor
public enum EnumOrderAuditLabel {
    /** 描述 */
    PLATFORM_AUDIT("00", "平台审核"),
    BUSINESS_AUDIT("01", "商家审核"),
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
     * @return {@link EnumOrderAuditLabel } 实例
     **/
    public static EnumOrderAuditLabel find(String code) {
        for (EnumOrderAuditLabel instance : EnumOrderAuditLabel.values()) {
            if (instance.getCode()
                .equals(code)) {
                return instance;
            }
        }
        return null;
    }
}