package com.rent.common.enums.order;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 订单来源
 * @author xiaoyao
 * @version V1.0
 * @date 2020-7-21 上午 10:24:59
 * @since 1.0
 */
@Getter
@AllArgsConstructor
public enum EnumOrderSource {
    /** 描述 */
    SEARCH("01", "搜索"),
    ACTIVITY("02", "活动"),
    SHOP("03","店铺"),
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
     * @return {@link  EnumOrderSource } 实例
     **/
    public static  EnumOrderSource find(String code) {
        for ( EnumOrderSource instance :  EnumOrderSource.values()) {
            if (instance.getCode()
                .equals(code)) {
                return instance;
            }
        }
        return null;
    }
}