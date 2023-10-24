package com.rent.common.enums.order;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xiaoyao
 * @version V1.0
 * @date 2020-6-23 下午 3:34:07
 * @since 1.0
 */
@Getter
@AllArgsConstructor
public enum EnumOrderBuyOutStatus {
    /** 描述 */
    CANCEL("01", "取消"),
    CLOSE("02", "关闭"),
    FINISH("03", "完成"),
    UN_PAY("04", "待支付"),
    PAYING("05", "支付中"),
    ;

    /** 状态码 */
    @EnumValue
    @JsonValue
    private String code;

    /** 状态描述 */
    private String description;

    /**
     * 根据编码查找枚举
     *
     * @param code 编码
     * @return {@link  EnumOrderBuyOutStatus } 实例
     **/
    public static  EnumOrderBuyOutStatus find(String code) {
        for ( EnumOrderBuyOutStatus instance :  EnumOrderBuyOutStatus.values()) {
            if (instance.getCode()
                .equals(code)) {
                return instance;
            }
        }
        return null;
    }
}