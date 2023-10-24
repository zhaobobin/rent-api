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
 * @since 1.0 2020-6-16 13:42
 */
@Getter
@AllArgsConstructor
public enum EnumOrderStatus {
    /** 描述 */
    WAITING_PAYMENT("01", "待支付","WAITING_PAYMENT"),
    PAYING("02", "支付中","PAYING"),
    PAYED_USER_APPLY_CLOSE("03", "已支付申请关单","PAYED_USER_APPLY_CLOSE"),
    TO_AUDIT("11", "待审核","TO_AUDIT"),
    PENDING_DEAL("04", "待发货","PENDING_DEAL"),
    WAITING_USER_RECEIVE_CONFIRM("05", "待确认收货","WAITING_USER_RECEIVE_CONFIRM"),
    RENTING("06", "租用中","RENTING"),
    TO_GIVE_BACK("12", "待归还","TO_GIVE_BACK"),
    WAITING_SETTLEMENT("07", "待结算","WAITING_SETTLEMENT"),
    WAITING_SETTLEMENT_PAYMENT("08", "结算待支付","WAITING_SETTLEMENT_PAYMENT"),
    FINISH("09", "订单完成","FINISH"),
    CLOSED("10", "交易关闭","CLOSED"),
    ;

    /** 状态码 */
    @JsonValue
    @EnumValue
    private String code;

    /** 状态描述 */
    private String description;

    private String extra;



    /**
     * 根据编码查找枚举
     *
     * @param code 编码
     * @return {@link EnumOrderStatus } 实例
     **/
    public static EnumOrderStatus find(String code) {
        for (EnumOrderStatus instance : EnumOrderStatus.values()) {
            if (instance.getCode()
                .equals(code)) {
                return instance;
            }
        }
        return null;
    }


    public static final Set<EnumOrderStatus> RETURN_SET = Sets.newHashSet(EnumOrderStatus.RENTING,
            EnumOrderStatus.TO_GIVE_BACK,EnumOrderStatus.WAITING_SETTLEMENT);


    public static final Set<EnumOrderStatus> DELIVERY_SET = Sets.newHashSet(EnumOrderStatus.RENTING,EnumOrderStatus.WAITING_USER_RECEIVE_CONFIRM,
            EnumOrderStatus.PENDING_DEAL);


    public static final Set<EnumOrderStatus> RENTING_SET = Sets.newHashSet(EnumOrderStatus.TO_AUDIT,EnumOrderStatus.PENDING_DEAL,EnumOrderStatus.WAITING_USER_RECEIVE_CONFIRM,
            EnumOrderStatus.RENTING,EnumOrderStatus.WAITING_SETTLEMENT,EnumOrderStatus.WAITING_SETTLEMENT_PAYMENT);


    public static final Set<EnumOrderStatus> SETTLEMENT_SET = Sets.newHashSet(EnumOrderStatus.WAITING_SETTLEMENT_PAYMENT,EnumOrderStatus.WAITING_SETTLEMENT);


    public static final Set<EnumOrderStatus> SUCCESS_SMS_SET = Sets.newHashSet(EnumOrderStatus.TO_AUDIT,EnumOrderStatus.PENDING_DEAL);

    public static final Set<EnumOrderStatus> WITHHOLD_SET = Sets.newHashSet(EnumOrderStatus.PENDING_DEAL, EnumOrderStatus.RENTING, EnumOrderStatus.WAITING_USER_RECEIVE_CONFIRM, EnumOrderStatus.TO_GIVE_BACK, EnumOrderStatus.WAITING_SETTLEMENT, EnumOrderStatus.WAITING_SETTLEMENT_PAYMENT);

    public static final Set<EnumOrderStatus> TRANSFER_SET = Sets.newHashSet(EnumOrderStatus.TO_AUDIT, EnumOrderStatus.PENDING_DEAL);

}
