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
public enum EnumOrderAuditStatus {
    /** 描述 */
    TO_AUDIT("00","待审核"),
    PASS("01", "通过"),
    REFUSE("02", "拒绝"),
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
     * @return {@link  EnumOrderAuditStatus } 实例
     **/
    public static  EnumOrderAuditStatus find(String code) {
        for ( EnumOrderAuditStatus instance :  EnumOrderAuditStatus.values()) {
            if (instance.getCode()
                .equals(code)) {
                return instance;
            }
        }
        return null;
    }
}
