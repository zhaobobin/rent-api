package com.rent.common.enums.order;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zhaowenchao
 */
@Getter
@AllArgsConstructor
public enum EnumFeeBillType {

    ASSESSMENT("ASSESSMENT", "租押分离","assessment:fee"),
    CONTRACT("CONTRACT", "电子合同","contract:fee"),
    CREDIT_REPORT("CREDIT_REPORT", "风控报告","report:fee"),
    SPLIT_BILL("SPLIT_BILL", "佣金费用",null),
            ;

    /** 状态码 */
    @JsonValue
    @EnumValue
    private String code;


    /** 状态描述 */
    private String description;

    /** 缓存key */
    private String configCode;


    public static  EnumFeeBillType find(String code) {
        for ( EnumFeeBillType instance :  EnumFeeBillType.values()) {
            if (instance.getCode()
                    .equals(code)) {
                return instance;
            }
        }
        return null;
    }
}
