package com.rent.common.enums.export;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zhaowenchao
 */
@Getter
@AllArgsConstructor
public enum  ExportStatus {

    SUCCESS("SUCCESS", "成功"),
    FAIL("FAIL", "失败"),
    PROCESSING("PROCESSING", "处理中"),
    EMPTY("EMPTY", "空记录"),
    ;

    @EnumValue
    @JsonValue
    private String code;

    private String description;


    public static ExportStatus find(String code) {
        for (ExportStatus instance : ExportStatus.values()) {
            if (instance.getCode()
                    .equals(code)) {
                return instance;
            }
        }
        return null;
    }
}
