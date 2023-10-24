package com.rent.common.enums.marketing;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * @author zhaowenchao
 */
@Getter
@AllArgsConstructor
public enum OrderComplaintStatusEnum {
    INIT("0", "未处理"),
    FINISH("1", "已处理"),
    ;

    @EnumValue
    @JsonValue
    private String code;

    private String description;

    public static OrderComplaintStatusEnum find(String code) {
        for (OrderComplaintStatusEnum instance : OrderComplaintStatusEnum.values()) {
            if (instance.getCode()
                    .equals(code)) {
                return instance;
            }
        }
        return null;
    }
}
