package com.rent.common.enums.order;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zhaowenchao
 */
@Getter
@AllArgsConstructor
public enum EnumSettleStatus {

    WAITING_SETTLEMENT("WAITING_SETTLEMENT", "待结算"),
    SETTLED("SETTLED", "已结算"),
            ;

    /** 状态码 */
    @JsonValue
    @EnumValue
    private String code;

    /** 状态描述 */
    private String description;


    public static  EnumSettleStatus find(String code) {
        for ( EnumSettleStatus instance :  EnumSettleStatus.values()) {
            if (instance.getCode()
                    .equals(code)) {
                return instance;
            }
        }
        return null;
    }
}
