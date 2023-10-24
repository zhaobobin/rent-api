package com.rent.common.enums.order;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 各业务名称及关联主键url
 *
 * @author xiaoyao
 */
@Getter
@AllArgsConstructor
public enum EnumSerialModalName {

    /**
     * 订单ID
     */
    ORDER_ID("OI", "orderId", "订单ID"),
    BUY_OUT_ID("BOI", "buyOutId", "买断订单ID"),
    RELET_ORDER_ID("ROI", "reletOrderId", "续租订单ID"),
    SPLIT_BILL("SPB_", "splitBill", "分账支付外部订单号"),


    ;

    /**
     * 状态码
     **/
    private String code;
    /**
     * 类型
     **/
    private String type;
    /**
     * 状态描述
     **/
    private String description;

}
