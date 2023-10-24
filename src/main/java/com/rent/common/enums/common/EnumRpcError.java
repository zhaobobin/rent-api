package com.rent.common.enums.common;

import lombok.Getter;

import java.util.Arrays;

/**
 * @Author xiaoyao
 * @Date 11:20 2020-4-20
 * @Param
 * @return
 **/
@Getter
public enum EnumRpcError {
    /** 请求模块不可为未知 */
    ASK_CAN_NOT_BE_UNKNOWN("RPC000001", "请求模块不可为未知"),
    /** 响应模块不可为未知 */
    ANSWER_CAN_NOT_BE_UNKNOWN("RPC000002", "响应模块不可为未知"),
    /** HzsxRequest不可为空 */
    HZSX_REQUEST_CAN_NOT_BE_NULL("RPC000003", "HzsxRequest不可为空"),
    IDEM_SERIAL_NO_NOT_SUPPORT("RPC000004", "幂等流水不可为空"),
    /** 校验异常 */
    VALIDATOR_ERROR("RPC000010", "参数不符: {}"),

    /** 数据异常 */
    DATA_ERROR("RPC000011", "数据异常: {}"),

    /** 验证码错误 */
    VALIDATE_CODE_ERROR("RPC000010", "验证码错误"),


    ;

    /** 码 */
    private String code;

    /** 描述 */
    private String description;

    EnumRpcError(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * 根据code查找枚举
     *
     * @param code code
     * @return 枚举
     */
    public static EnumRpcError find(String code) {
        EnumRpcError enumRpcError = Arrays.stream(
            EnumRpcError.values())
                .filter(input -> input.getCode()
                        .equals(code))
                .findFirst()
                .orElse(null);
        return enumRpcError;
    }
}
