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
public enum EnumShopWithdrawApplyStatus {

    PENDING("0", "审核中"),
    PASS("2", "审核通过"),

    ;


    @JsonValue
    @EnumValue
    private String code;
    private String msg;

    public static EnumShopWithdrawApplyStatus find(Integer code) {
        return Arrays.stream(EnumShopWithdrawApplyStatus.values())
                .filter(input -> input.getCode()
                        .equals(code))
                .findFirst()
                .orElse(null);
    }
}
