package com.rent.common.enums.order;


import java.util.HashSet;
import java.util.Set;

/**
 * 禁止提现押金的订单状态
 * @author zhaowenchao
 */
public class OrderDepositWithdrawStatusSet {

    public static Set<EnumOrderStatus> withDrawOrderStatusSet =  new HashSet<EnumOrderStatus>(){{
        add(EnumOrderStatus.FINISH);
        add(EnumOrderStatus.CLOSED);
    }};



}
