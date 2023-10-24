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
public enum EnumOrderPayDepositStatus {
    /** 描述 */
    WAITING_PAYMENT("WAITING_PAYMENT", "待支付"),
    PAID("PAID","已支付"),
    WITHDRAW ("WITHDRAW","已提现"),
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
     * @return {@link  EnumOrderPayDepositStatus } 实例
     **/
    public static  EnumOrderPayDepositStatus find(String code) {
        for ( EnumOrderPayDepositStatus instance :  EnumOrderPayDepositStatus.values()) {
            if (instance.getCode()
                .equals(code)) {
                return instance;
            }
        }
        return null;
    }

}
