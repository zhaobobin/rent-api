package com.rent.common.enums.common;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xiaoyao
 * @version V1.0
 * @date 2020-6-23 下午 6:39:35
 * @since 1.0
 */
@Getter
@AllArgsConstructor
public enum EnumComponentsError {
    /** 远程调用失败 */
    RPC_ERROR("914000000", "远程调用失败"),
    ALIPAY_FREEZE_ERROR("914000001", "预授权冻结失败"),
    SERIAL_NOT_EXISTS_OR_DONE("914000002", "该流水不存在或已处理"),
    FREEZE_RECORD_NOT_EXISTS("914000003", "预授权冻结记录不存在"),
    PARAMS_CHECK_ERROR("914000004", "参数错误"),
    ID_CERT_ERROR("914000005", "身份证认证失败"),
    ;

    /** 状态码 */
    @EnumValue
    private String code;

    /** 状态描述 */
    private String msg;

    /**
     * 根据编码查找枚举
     *
     * @param code 编码
     * @return {@link EnumComponentsError } 实例
     **/
    public static EnumComponentsError find(String code) {
        for (EnumComponentsError instance : EnumComponentsError.values()) {
            if (instance.getCode()
                .equals(code)) {
                return instance;
            }
        }
        return null;
    }
}