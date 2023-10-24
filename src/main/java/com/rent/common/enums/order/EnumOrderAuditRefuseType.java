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
public enum EnumOrderAuditRefuseType {
    /** 描述 */
    REFUSE01("01","小贷过多"),
    REFUSE02("02", "在途订单过多"),
    REFUSE03("03", "偿还能力"),
    REFUSE04("04", "欺诈风险"),
    REFUSE05("05", "法院涉案"),
    REFUSE06("06", "非本人租用"),
    REFUSE07("07", "他处还款逾期"),
    REFUSE08("08", "客户失联"),
    REFUSE09("09", "敏感行业"),


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
     * @return {@link  EnumOrderAuditRefuseType } 实例
     **/
    public static  EnumOrderAuditRefuseType find(String code) {
        for ( EnumOrderAuditRefuseType instance :  EnumOrderAuditRefuseType.values()) {
            if (instance.getCode()
                .equals(code)) {
                return instance;
            }
        }
        return null;
    }
}
