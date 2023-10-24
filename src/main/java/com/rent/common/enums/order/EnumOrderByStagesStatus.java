package com.rent.common.enums.order;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.Sets;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;

/**
 * @author xiaoyao
 * @version V1.0
 * @date 2020-6-16 16:28
 * @since 1.0
 */
@Getter
@AllArgsConstructor
public enum EnumOrderByStagesStatus {
    /** 状态:  1：待还款 2：已还款 3：逾期已还款 4：逾期待还款 5：已取消 7:已退款 */
    UN_PAY("1", false,"待还款"),
    PAYED("2", true,"已还款"),
    OVERDUE_PAYED("3", true,"逾期已还款"),
    OVERDUE_NOT_PAY("4",false, "逾期待还款"),
    CANCEL("5", false,"已取消"),
    REFUNDED("7", false,"已退款"),
    ;

    /** 状态码 */
    @EnumValue
    @JsonValue
    private String code;

    private Boolean paid;

    /** 状态描述 */
    private String description;

    /**
     * 根据编码查找枚举
     *
     * @param code 编码
     * @return {@link  EnumOrderByStagesStatus } 实例
     **/
    public static  EnumOrderByStagesStatus find(String code) {
        for ( EnumOrderByStagesStatus instance :  EnumOrderByStagesStatus.values()) {
            if (instance.getCode()
                    .equals(code)) {
                return instance;
            }
        }
        return null;
    }


    //需要计算逾期违约收费的订单
    public static final Set<EnumOrderByStagesStatus> OVERDUE_SET = Sets.newHashSet(
         EnumOrderByStagesStatus.OVERDUE_PAYED,  EnumOrderByStagesStatus.OVERDUE_NOT_PAY);


    public static final Set<EnumOrderByStagesStatus> PAY_SET = Sets.newHashSet(EnumOrderByStagesStatus.PAYED, EnumOrderByStagesStatus.OVERDUE_PAYED);
    public static final Set<EnumOrderByStagesStatus> UNPAY_SET = Sets.newHashSet(EnumOrderByStagesStatus.UN_PAY, EnumOrderByStagesStatus.OVERDUE_NOT_PAY);
}
