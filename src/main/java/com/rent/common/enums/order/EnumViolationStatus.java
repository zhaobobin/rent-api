package com.rent.common.enums.order;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xiaoyao
 * @version V1.0
 * @since 1.0 2020-6-16 15:04
 */
@Getter
@AllArgsConstructor
public enum EnumViolationStatus {
    /** 01正常 02结算单逾期 03提前归还 04账单逾期 05逾期未归还*/
    NORMAL("01", "正常"),
    SETTLEMENT_OVERDUE("02", "结算单逾期"),
    ADVANCE("03", "提前归还"),
    STAGE_OVERDUE("04", "账单逾期"),
    OVERDUE_RETURN("05","逾期未归还")
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
     * @return {@link EnumViolationStatus } 实例
     **/
    public static EnumViolationStatus find(String code) {
        for (EnumViolationStatus instance : EnumViolationStatus.values()) {
            if (instance.getCode()
                .equals(code)) {
                return instance;
            }
        }
        return null;
    }
}
