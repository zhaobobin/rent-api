package com.rent.common.enums.order;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 订单来源
 * @author xiaoyao
 * @version V1.0
 * @date 2020-7-21 上午 10:24:59
 * @since 1.0
 */
@Getter
@AllArgsConstructor
public enum EnumOrderNsfLevel {
    /** 描述 */
    RANK0("D", "rank0","审核"),
    RANK1("C", "rank1","拒绝"),
    RANK2("B","rank2","审核"),
    RANK3("A","rank3","准入")
    ;

    /** 状态码 */
    @JsonValue
    @EnumValue
    private String code;

    private String value;

    /** 状态描述 */
    private String description;

    /**
     * 根据编码查找枚举
     *
     * @param code 编码
     * @return {@link  EnumOrderNsfLevel } 实例
     **/
    public static  EnumOrderNsfLevel find(String code) {
        for ( EnumOrderNsfLevel instance :  EnumOrderNsfLevel.values()) {
            if (instance.getCode()
                .equals(code)) {
                return instance;
            }
        }
        return null;
    }

    /**
     * 根据编码查找枚举
     *
     * @param value 编码
     * @return {@link  EnumOrderNsfLevel } 实例
     **/
    public static  EnumOrderNsfLevel findValue(String value) {
        for ( EnumOrderNsfLevel instance :  EnumOrderNsfLevel.values()) {
            if (instance.getValue()
                .equals(value)) {
                return instance;
            }
        }
        return null;
    }
}