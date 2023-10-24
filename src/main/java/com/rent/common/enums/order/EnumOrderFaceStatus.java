package com.rent.common.enums.order;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xiaoyao
 * @version V1.0
 * @since v1.0 2020-5-20 15:53
 */
@Getter
@AllArgsConstructor
public enum EnumOrderFaceStatus {
    /** 未认证 */
    NOT_AUTH("01","未认证"),
    AUTHING("02","认证中"),
    AUTHED("03","已认证"),
    ;
    @JsonValue
    @EnumValue
    private String code;
    private String msg;
}
