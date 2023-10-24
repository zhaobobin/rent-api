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
public enum EnumProductAuditState {

    PENDING(0, "审核中"),
    REJECT(1, "审核不通过"),
    PASS(2, "审核通过"),

    ;


    @JsonValue
    @EnumValue
    private Integer code;
    private String msg;

    public static EnumProductAuditState find(Integer code) {
        return Arrays.stream(EnumProductAuditState.values())
                .filter(input -> input.getCode()
                        .equals(code))
                .findFirst()
                .orElse(null);
    }
}
