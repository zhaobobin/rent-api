package com.rent.common.enums.order;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
* @Description: 平台类型
*/
@Getter
@AllArgsConstructor
public enum EnumPlatformType {

    REGULAR_OPE("REGULAR_OPE","OPE","常规优惠"),
    WELFARE_OPE("WELFARE_OPE","OPE","天降福利"),
    REGULAR_SHOP("REGULAR_SHOP","SHOP","常规优惠"),
    ;

    /** 状态码 */
    @JsonValue
    @EnumValue
    private String code;

    private String type;


    /** 状态描述 */
    private String description;


    public static  EnumPlatformType find(String code) {
        for ( EnumPlatformType instance :  EnumPlatformType.values()) {
            if (instance.getCode()
                    .equals(code)) {
                return instance;
            }
        }
        return null;
    }
}
