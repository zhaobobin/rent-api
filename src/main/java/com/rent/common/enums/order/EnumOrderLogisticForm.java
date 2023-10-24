package com.rent.common.enums.order;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xiaoyao
 * @version V1.0
 * @date 2020-6-16 15:23
 * @since 1.0
 */
@Getter
@AllArgsConstructor
public enum EnumOrderLogisticForm {
    /** 物流方式类型 01为快递 02为上门 03为自提 */
    EXPRESS("01", "快递"),
    VISIT("02", "上门"),
    SELF_EXTRACTING("03", "自提"),
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
     * @return {@link EnumOrderLogisticForm } 实例
     **/
    public static EnumOrderLogisticForm find(String code) {
        for (EnumOrderLogisticForm instance : EnumOrderLogisticForm.values()) {
            if (instance.getCode()
                .equals(code)) {
                return instance;
            }
        }
        return null;
    }
}
