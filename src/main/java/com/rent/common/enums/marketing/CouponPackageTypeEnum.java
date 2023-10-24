package com.rent.common.enums.marketing;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * @author zhaowenchao
 */
@Getter
@AllArgsConstructor
public enum CouponPackageTypeEnum {
    /** 描述 */
    NULL("", "独立"),
    SINGLE("SINGLE", "独立"),
    ACTIVITY("ACTIVITY", "营销活动"),
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
     **/
    public static CouponPackageTypeEnum find(String code) {
        for (CouponPackageTypeEnum instance : CouponPackageTypeEnum.values()) {
            if (instance.getCode()
                    .equals(code)) {
                return instance;
            }
        }
        return null;
    }
}
