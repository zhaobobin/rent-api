package com.rent.common.enums.components;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xiaoyao
 * @version V1.0
 * @date 2020-11-17 下午 4:33:09
 * @since 1.0
 */
@Getter
@AllArgsConstructor
public enum EnumInsureOperate {
    /** 描述 */
    INSURE("INSURE", "投保"),
    CANCEL("CANCEL", "退保"),
    ;

    /** 状态码 */
    @EnumValue
    private String code;

    /** 状态描述 */
    private String description;

    /**
     * 根据编码查找枚举
     *
     * @param code 编码
     * @return {@link EnumInsureOperate } 实例
     **/
    public static EnumInsureOperate find(String code) {
        for (EnumInsureOperate instance : EnumInsureOperate.values()) {
            if (instance.getCode()
                .equals(code)) {
                return instance;
            }
        }
        return null;
    }
}