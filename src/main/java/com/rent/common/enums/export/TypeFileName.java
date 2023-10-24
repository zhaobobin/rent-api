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
public enum TypeFileName {

    XLS("xls"),
    //word
    WORD("word"),

    ;

    @EnumValue
    @JsonValue
    private String code;

}
