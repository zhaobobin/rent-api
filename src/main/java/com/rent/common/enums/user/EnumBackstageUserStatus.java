package com.rent.common.enums.user;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum EnumBackstageUserStatus {



    VALID("VALID", "启用"),
    INVALID("INVALID", "禁用"),

    ;


    @JsonValue
    @EnumValue
    private String code;
    private String msg;

    public static EnumBackstageUserStatus find(Integer code) {
        return Arrays.stream(EnumBackstageUserStatus.values())
                .filter(input -> input.getCode()
                        .equals(code))
                .findFirst()
                .orElse(null);
    }
}
