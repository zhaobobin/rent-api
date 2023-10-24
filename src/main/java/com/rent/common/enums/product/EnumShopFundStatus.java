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
public enum EnumShopFundStatus {

    SUCCESS("SUCCESS", "已完成"),
    PROCESSING("PROCESSING", "处理中"),
    ;


    @JsonValue
    @EnumValue
    private String code;
    private String msg;

    public static EnumShopFundStatus find(Integer code) {
        return Arrays.stream(EnumShopFundStatus.values())
                .filter(input -> input.getCode()
                        .equals(code))
                .findFirst()
                .orElse(null);
    }
}
