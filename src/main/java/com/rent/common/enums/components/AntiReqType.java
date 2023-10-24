package com.rent.common.enums.components;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zhaowenchao
 */

@Getter
@AllArgsConstructor
public enum AntiReqType {
//    租赁宝所属阶段，分为 product/user/order/orderProduct/logistic/leasePromise/rental 详见租赁平台详细字段
    PRODUCT("product", "个人客户"),
    USER("user", "企业客户"),
    ORDER("order", "企业客户"),
    ORDER_PRODUCT("orderProduct", "企业客户"),
    LOGISTIC("logistic", "企业客户"),
    LEASE_PROMISE("leasePromise", "企业客户"),
    RENTAL("rental", "企业客户"),

    ;

    @EnumValue
    private String code;

    private String description;

    public static AntiReqType find(String code) {
        for (AntiReqType instance : AntiReqType.values()) {
            if (instance.getCode()
                .equals(code)) {
                return instance;
            }
        }
        return null;
    }
}