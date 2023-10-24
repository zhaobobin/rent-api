package com.rent.common.enums.order;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xiaoyao
 * @version V1.0
 * @since 1.0 2020-6-16 13:42
 */
@Getter
@AllArgsConstructor
public enum EnumAccountPeriodOperator {
    /** 描述 */
    GENERATE("GENERATE", "生成结算单"),
    SUBMIT_SETTLE("SUBMIT_SETTLE","提交结算"),
    AUDIT_PASS("AUDIT_PASS", "审核通过"),
    AUDIT_DENY("AUDIT_DENY", "审核拒绝"),
    SUBMIT_PAY("SUBMIT_PAY", "提交支付"),
    PAY_FAIL("PAY_FAIL", "支付失败"),
    PAY_SUCCESS("PAY_SUCCESS", "支付成功"),
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
     * @return {@link  EnumAccountPeriodOperator } 实例
     **/
    public static  EnumAccountPeriodOperator find(String code) {
        for ( EnumAccountPeriodOperator instance :  EnumAccountPeriodOperator.values()) {
            if (instance.getCode()
                .equals(code)) {
                return instance;
            }
        }
        return null;
    }

}
