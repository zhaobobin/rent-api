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
public enum EnumOrderSettlementStatus {
    /** 描述 */
    UNPAID("01", "待支付"),
    PAYING("02", "支付中"),
    SETTLED("03", "已结算"),
    APPLY_MODIFY("04", "用户申请修改"),
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
     * @return {@link  EnumOrderSettlementStatus } 实例
     **/
    public static  EnumOrderSettlementStatus find(String code) {
        for ( EnumOrderSettlementStatus instance :  EnumOrderSettlementStatus.values()) {
            if (instance.getCode()
                .equals(code)) {
                return instance;
            }
        }
        return null;
    }
}
