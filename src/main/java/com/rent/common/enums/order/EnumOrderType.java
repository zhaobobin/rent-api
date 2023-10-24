package com.rent.common.enums.order;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xiaoyao
 * @version V1.0
 * @date 2020-6-16 15:15
 * @since 1.0
 */
@Getter
@AllArgsConstructor
public enum EnumOrderType {
    /** 01为常规订单 02为拼团订单    03 续租订单  04买断订单 */
    GENERAL_ORDER("01", "常规订单"),
    GROUP_ORDER("02", "拼团订单"),
    RELET_ORDER("03", "续租订单"),
    BUYOUT_ORDER("04", "买断订单"),
    PURCHASE_ORDER("05", "购买订单"),
    ;

    /** 状态码 */
    @JsonValue
    @EnumValue
    private String code;

    /** 状态描述 */
    private String description;

    /**
     * 根据编码查找枚举
     *
     * @param code 编码
     * @return {@link  EnumOrderType } 实例
     **/
    public static EnumOrderType find(String code) {
        for (EnumOrderType instance : EnumOrderType.values()) {
            if (instance.getCode()
                .equals(code)) {
                return instance;
            }
        }
        return null;
    }
}
