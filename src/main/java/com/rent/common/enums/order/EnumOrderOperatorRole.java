package com.rent.common.enums.order;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xiaoyao
 * @version V1.0
 * @date 2020-7-24 下午 4:02:53
 * @since 1.0
 */
@Getter
@AllArgsConstructor
public enum EnumOrderOperatorRole {
    /** 描述 */
    OPE("01", "运营"),
    BUSINESS("02","商家"),
    USER("03","客户"),
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
     * @return {@link  EnumOrderOperatorRole } 实例
     **/
    public static  EnumOrderOperatorRole find(String code) {
        for ( EnumOrderOperatorRole instance :  EnumOrderOperatorRole.values()) {
            if (instance.getCode()
                .equals(code)) {
                return instance;
            }
        }
        return null;
    }
}