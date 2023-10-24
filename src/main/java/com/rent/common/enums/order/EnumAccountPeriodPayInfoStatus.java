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
public enum EnumAccountPeriodPayInfoStatus {
    /** 描述 */
    PAY_SUCCESS("PAID","支付成功"),
    IN_PAYMENT("IN_PAYMENT","支付中"),
    PAY_FAIL("PAY_FAIL", "支付失败"),
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
     * @return {@link  EnumAccountPeriodPayInfoStatus } 实例
     **/
    public static  EnumAccountPeriodPayInfoStatus find(String code) {
        for ( EnumAccountPeriodPayInfoStatus instance :  EnumAccountPeriodPayInfoStatus.values()) {
            if (instance.getCode()
                .equals(code)) {
                return instance;
            }
        }
        return null;
    }

}
