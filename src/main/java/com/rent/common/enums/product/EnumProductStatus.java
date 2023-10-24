package com.rent.common.enums.product;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author zhaowenchao
 */

@Getter
@AllArgsConstructor
public enum EnumProductStatus {

    INVALID(0, "失效"),
    VALID(1, "有效"),

    ;


    @JsonValue
    @EnumValue
    private Integer code;
    private String msg;

    public static EnumProductStatus find(Integer code) {
        return Arrays.stream(EnumProductStatus.values())
                .filter(input -> input.getCode()
                        .equals(code))
                .findFirst()
                .orElse(null);
    }
}
