package com.rent.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @Author xiaoyao
 * @Date 21:45 2020-4-19
 * @Param
 * @return
 **/
@Getter
@AllArgsConstructor
public enum EnumContext {

    /** 统一渠道流水号 */
    SERIAL_NO("SERIAL_NO", "统一渠道流水号"),
    /** 操作员id */
    OPERATOR_ID("OPERATOR_ID", "操作员id"),
    /** 操作员姓名 */
    OPERATOR_NAME("OPERATOR_NAME", "操作员姓名"),
    /** 登录用户信息 */
    LOGIN_USER("LOGIN_USER", "登录用户信息"),
    ;

    /** 状态码 **/
    private String code;
    /** 状态描述 **/
    private String description;

    /**
     * 根据code查找
     *
     * @param code code
     * @return 枚举
     */
    public static EnumContext find(String code) {
        return Arrays.stream(EnumContext.values())
            .filter(input -> input.getCode()
                .equals(code))
            .findFirst()
            .orElse(null);
    }
}
