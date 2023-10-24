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
public enum EnumRiskReportType {
    /** 描述 */
    SIRIUS("01", "天郎星"),
    NEW_RISK("02", "新版风控报告"),
    DECISION_RISK("03", "决策报告"),
    FORBIDDEN("04", "禁言"),
    RENT_CREDIT("05", "租赁授信"),
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
     * @return {@link EnumRiskReportType } 实例
     **/
    public static EnumRiskReportType find(String code) {
        for (EnumRiskReportType instance : EnumRiskReportType.values()) {
            if (instance.getCode()
                .equals(code)) {
                return instance;
            }
        }
        return null;
    }
}