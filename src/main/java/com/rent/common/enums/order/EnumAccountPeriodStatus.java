package com.rent.common.enums.order;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xiaoyao
 * @version V1.0
 * @since 1.0 2020-6-16 13:42
 */
@Getter
@AllArgsConstructor
public enum EnumAccountPeriodStatus {
    /** 描述 */
    WAITING_PAYMENT("WAITING_PAYMENT", "待支付"),
    PAID("PAID","已支付"),
    IN_PAYMENT("IN_PAYMENT","支付中"),
    TO_AUDIT("TO_AUDIT", "待审核"),
    WAITING_SETTLEMENT("WAITING_SETTLEMENT", "待结算"),
    SETTLED("SETTLED", "已结算"),
    ;

    /** 状态码 */
    @JsonValue
    @EnumValue
    private String code;

    /** 状态描述 */
    private String description;


    /**
     * 根据编码查找枚举
     *
     * @param code 编码
     * @return {@link  EnumAccountPeriodStatus } 实例
     **/
    public static  EnumAccountPeriodStatus find(String code) {
        for ( EnumAccountPeriodStatus instance :  EnumAccountPeriodStatus.values()) {
            if (instance.getCode()
                .equals(code)) {
                return instance;
            }
        }
        return null;
    }

}
