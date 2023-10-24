package com.rent.common.enums.user;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum EnumBackstageUserPlatform {

    SYS("SYS", "系统角色"),
    OPE("OPE", "平台账号"),
    SHOP("SHOP", "商家账号"),
    CHANNEL("CHANNEL", "渠道账号"),

    ;


    @JsonValue
    @EnumValue
    private String code;
    private String msg;

    public static EnumBackstageUserPlatform find(Integer code) {
        return Arrays.stream(EnumBackstageUserPlatform.values())
                .filter(input -> input.getCode()
                        .equals(code))
                .findFirst()
                .orElse(null);
    }
}
