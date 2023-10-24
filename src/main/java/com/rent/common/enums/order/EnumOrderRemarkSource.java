package com.rent.common.enums.order;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xiaoyao
 * @version V1.0
 * @date 2020-7-24 下午 4:02:53
 * @since 1.0
 */
@Getter
@AllArgsConstructor
public enum EnumOrderRemarkSource {
    /** 描述 */
    OPE("01", "运营中心"),
    BUSINESS("02","商家中心"),
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
     * @return {@link  EnumOrderRemarkSource } 实例
     **/
    public static  EnumOrderRemarkSource find(String code) {
        for ( EnumOrderRemarkSource instance :  EnumOrderRemarkSource.values()) {
            if (instance.getCode()
                .equals(code)) {
                return instance;
            }
        }
        return null;
    }
}