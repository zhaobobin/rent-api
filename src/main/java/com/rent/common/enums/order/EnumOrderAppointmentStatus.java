package com.rent.common.enums.order;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 购买订单状态枚举
 * @author zhaowenchao
 */
@Getter
@AllArgsConstructor
public enum EnumOrderAppointmentStatus {
    /** 描述 */
    NONE("", ""),
    WAITING_FOR_PAY("WAITING_FOR_PAY", "待支付"),
    CANCEL("CANCEL", "已取消"),
    FINISH("FINISH", "已完成"),
    ;

    /** 状态码 */
    @EnumValue
    @JsonValue
    private String code;

    /** 状态描述 */
    private String description;

    /**
     * 根据编码查找枚举
     *
     * @param code 编码
     * @return {@link  EnumOrderAppointmentStatus } 实例
     **/
    public static  EnumOrderAppointmentStatus find(String code) {
        for ( EnumOrderAppointmentStatus instance :  EnumOrderAppointmentStatus.values()) {
            if (instance.getCode()
                .equals(code)) {
                return instance;
            }
        }
        return null;
    }
}