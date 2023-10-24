package com.rent.common.enums.export;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 *
 */
@Getter
@AllArgsConstructor
public enum EnumOrderScale {

    NORMAL("02", "正常"),
    SHOP_SPLIT_ERROR("01", "商家未设置分账比例"),
    NO_SUPPERT_BUY_OUT("00","该商品不支持买断"),

    ;

    /** 状态码 **/
    private String code;
    /** 状态描述 **/
    private String description;

    public static EnumOrderScale find(String code) {
        return Arrays.stream(EnumOrderScale.values()).filter(input -> input.getCode().equals(code)).findFirst()
            .orElse(null);
    }
}
