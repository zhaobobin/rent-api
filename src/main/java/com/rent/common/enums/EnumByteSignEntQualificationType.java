package com.rent.common.enums;

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
public enum EnumByteSignEntQualificationType {

    TYPE_1("1", "企业营业执照统一社会信用代码"),
    TYPE_2("2", "企业营业执照注册号"),
    TYPE_3("3", "个体工商户营业执照统一社会信用代码"),
    TYPE_4("4", "个体工商户营业执照注册号"),
    TYPE_5("5", "社会组织机构代码"),
    TYPE_6("6", "律师事务所执业许可证号"),
    TYPE_7("7", "其他类型企业"),

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
     * @return {@link EnumByteSignEntQualificationType } 实例
     **/
    public static EnumByteSignEntQualificationType find(String code) {
        for (EnumByteSignEntQualificationType instance : EnumByteSignEntQualificationType.values()) {
            if (instance.getCode()
                .equals(code)) {
                return instance;
            }
        }
        return null;
    }
}