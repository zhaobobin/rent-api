package com.rent.common.enums.order;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 订单关闭类型
 *
 * @author xiaoyao
 * @version V1.0
 * @date 2020-6-17 14:27
 * @since 1.0
 */
@Getter
@AllArgsConstructor
public enum EnumOrderCloseType {
    /** 描述 */
    UN_PAY_USER_APPLY("01", "未支付用户主动申请"),
    PAY_FAILED("02", "支付失败"),
    OVER_TIME_PAY("03", "超时支付"),
    PAYED_USER_APPLY("04", "已支付用户主动申请"),
    RISK_CLOSE("05", "风控拒绝"),
    BUSINESS_CLOSE("06", "商家关闭(客户要求)"),
    BUSINESS_RISK_CLOSE("07", "商家风控关闭订单"),
    BUSINESS_OVER_TIME_DELIVERY("08", "商家超时发货"),
    PLATFORM_CLOSE("09", "平台关闭订单"),
    USER_APPLY("10", "用户主动申请"),
    OPE_RISK_CLOSE("11", "平台风控关闭订单"),
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
     * @return {@link EnumOrderCloseType } 实例
     **/
    public static EnumOrderCloseType find(String code) {
        for (EnumOrderCloseType instance : EnumOrderCloseType.values()) {
            if (instance.getCode()
                .equals(code)) {
                return instance;
            }
        }
        return null;
    }
}
