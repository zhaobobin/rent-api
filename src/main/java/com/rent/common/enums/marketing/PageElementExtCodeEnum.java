package com.rent.common.enums.marketing;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * 页面配置 对应的编码
 * @author zhaowenchao
 */
@Getter
@AllArgsConstructor
public enum PageElementExtCodeEnum {

    ORDER_WAIT_PAY("ORDER_WAIT_PAY", "我的订单-待付款"),
    ORDER_WAIT_DELIVERY("ORDER_WAIT_DELIVERY", "我的订单-待发货"),
    ORDER_WAIT_RECEIVE("ORDER_WAIT_RECEIVE", "我的订单-待收货"),
    ORDER_RENTING("ORDER_RENTING", "我的订单-租用中"),
    ORDER_WAIT_SETTLE("ORDER_WAIT_SETTLE", "我的订单-待结算"),
    ORDER_OVERDUE("ORDER_OVERDUE", "我的订单-已逾期"),
    COUPON("COUPON", "优惠券"),
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
     * @return {@link PageElementExtCodeEnum } 实例
     **/
    public static PageElementExtCodeEnum find(String code) {
        for (PageElementExtCodeEnum instance : PageElementExtCodeEnum.values()) {
            if (instance.getCode().equals(code)) {
                return instance;
            }
        }
        return null;
    }
}
