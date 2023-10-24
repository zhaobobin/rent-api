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
public enum EnumProductType {

    TRASH(0, "回收站"),
    ON_SHELF(1, "已上架"),
    OFF_SHELF(2, "已下架"),

    ;


    @JsonValue
    @EnumValue
    private Integer code;
    private String msg;

    public static EnumProductType find(Integer code) {
        return Arrays.stream(EnumProductType.values())
                .filter(input -> input.getCode()
                        .equals(code))
                .findFirst()
                .orElse(null);
    }
}
