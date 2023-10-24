package com.rent.common.enums.components;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 支付状态
 *
 * @author xiaoyao
 * @version V1.0
 * @date 2020-6-28 上午 11:49:16
 * @since 1.0
 */
@Getter
@AllArgsConstructor
public enum EnumAliPayStatus {
    /** 描述 */
    INIT("00", "初始化"),
    PAYING("01", "处理中"),
    SUCCESS("02", "成功"),
    FAILED("03", "失败"),
    CANCEL("04","取消"),
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
     * @return {@link com.rent.common.enums.components.EnumAliPayStatus } 实例
     **/
    public static EnumAliPayStatus find(String code) {
        for (EnumAliPayStatus instance : EnumAliPayStatus.values()) {
            if (instance.getCode()
                .equals(code)) {
                return instance;
            }
        }
        return null;
    }
}