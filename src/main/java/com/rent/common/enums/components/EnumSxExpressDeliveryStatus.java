package com.rent.common.enums.components;

import lombok.Getter;

/**
 * @author zhaowenchao
 */
@Getter
public enum EnumSxExpressDeliveryStatus {

    ON_THE_WAY(1, "在途中"),
    SENDING(2, "派件中"),
    RECEIVED(3, "已签收"),
    FAIL(4, "派送失败(拒签等)"),

    ;

    private int code;
    private String msg;



    EnumSxExpressDeliveryStatus(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }


}
