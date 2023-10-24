package com.rent.common.enums.product;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zhaowenchao
 */
@Getter
@AllArgsConstructor
public enum EnumSplitBillAppVersion {
    /**
     *
     */
    LITE("LITE", "简版小程序"),
    TOUTIAO("TOUTIAO", "头条小程序"),
    ZWZ("ZWZ", "租物租");

    /**
     * 状态码
     */
    @JsonValue
    @EnumValue
    private String code;

    /**
     * 状态描述
     */
    private String msg;

    /**
     * 根据编码查找枚举
     *
     * @param code 编码
     * @return {@link com.hzsx.rent.common.model.enums.product.EnumSplitBillAppVersion } 实例
     **/
    public static EnumSplitBillAppVersion find(String code) {
        for (EnumSplitBillAppVersion instance : EnumSplitBillAppVersion.values()) {
            if (instance.getCode()
                .equals(code)) {
                return instance;
            }
        }
        return null;
    }
}
