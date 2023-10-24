package com.rent.common.enums.order;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.Sets;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;

/**
 * @author xiaoyao
 * @version V1.0
 * @date 2020-8-6 下午 4:54:29
 * @since 1.0
 */
@Getter
@AllArgsConstructor
public enum EnumOrderExamineStatus {
    /** 描述 */
    PLATFORM_TO_EXAMINE("00", "待平台审核"),
    BUSINESS_TO_EXAMINE("01", "待商家审核"),
    PLATFORM_EXAMINED("02","平台已审核"),
    BUSINESS_EXAMINED("03","商家已审核"),
    PLATFORM_TO_FINAL_EXAMINE("04", "待平台终审"),
    PLATFORM_FINAL_EXAMINED("05", "平台终审已审核"),
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
     * @return {@link EnumOrderExamineStatus } 实例
     **/
    public static EnumOrderExamineStatus find(String code) {
        for (EnumOrderExamineStatus instance : EnumOrderExamineStatus.values()) {
            if (instance.getCode()
                .equals(code)) {
                return instance;
            }
        }
        return null;
    }

    public static final Set<EnumOrderExamineStatus> FIANL_SET = Sets.newHashSet(EnumOrderExamineStatus.PLATFORM_TO_FINAL_EXAMINE, EnumOrderExamineStatus.PLATFORM_FINAL_EXAMINED);
}