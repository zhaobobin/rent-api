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
public enum EnumOderFeeBillInvoiceStatus {
    /** 描述 */
    UN_TICKET("UN_TICKET", "未开票"),
    APPLYING("APPLYING","申请中"),
    TICKETED("TICKETED", "已开票"),
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
     * @return {@link  EnumOderFeeBillInvoiceStatus } 实例
     **/
    public static EnumOderFeeBillInvoiceStatus find(String code) {
        for ( EnumOderFeeBillInvoiceStatus instance :  EnumOderFeeBillInvoiceStatus.values()) {
            if (instance.getCode()
                .equals(code)) {
                return instance;
            }
        }
        return null;
    }

}
