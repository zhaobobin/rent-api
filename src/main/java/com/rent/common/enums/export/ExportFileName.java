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
public enum ExportFileName {

    RENT_ORDER_LIST("订单列表"),
    OVER_DUE_ORDER("逾期订单"),
    NOT_GIVE_BACK("到期未归还订单"),
    BUY_OUT_ORDER("买断订单"),
    PURCHASE("购买订单"),

    ACCOUNT_PERIOD_PURCHASE("财务结算-购买订单"),
    ACCOUNT_PERIOD_RENT("财务结算-常规订单"),
    ACCOUNT_PERIOD_BUY_OUT("财务结算-买断订单"),

    STOCK("库存导出"),

    //word
    PREQUALIFICATION_SHEET("预审单"),
    RECEIPT_CONFIRMATION_RECEIPT("回执单"),

    CHANNEL_RENT_ORDERS("渠道订单列表"),

    PRODUCT("商品列表"),

    FEE_BILL("费用"),

    ;

    @EnumValue
    @JsonValue
    private String code;

}
