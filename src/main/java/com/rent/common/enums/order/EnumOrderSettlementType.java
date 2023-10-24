package com.rent.common.enums.order;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xiaoyao
 * @version V1.0
 * @date 2020-6-19 10:31
 * @since 1.0
 */
@Getter
@AllArgsConstructor
public enum EnumOrderSettlementType {
    /**
     * 描述
     */
    INTACT("01", "完好"),

    DAMAGE("02", "损坏"),

    LOSS("03", "丢失"),

    VIOLATE("04", "违约"),

    OVERDUE("05", "逾期"),

    ;

    /**
     * 状态码
     */
    @JsonValue
    @EnumValue
    private String code;

    /**
     * 状态描述
     */
    private String description;

    /**
     * 根据编码查找枚举
     *
     * @param code 编码
     * @return {@link  EnumOrderSettlementType } 实例
     **/
    public static  EnumOrderSettlementType find(String code) {
        for ( EnumOrderSettlementType instance :  EnumOrderSettlementType.values()) {
            if (instance.getCode()
                .equals(code)) {
                return instance;
            }
        }
        return null;
    }
}
