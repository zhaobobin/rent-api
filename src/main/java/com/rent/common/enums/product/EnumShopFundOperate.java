package com.rent.common.enums.product;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum EnumShopFundOperate {

    WITHDRAW("WITHDRAW", "提现"),
    RECHARGE("RECHARGE", "充值"),
    BROKERAGE("BROKERAGE", "佣金结算"),
    ASSESSMENT("ASSESSMENT", "芝麻租押分离接口费用"),
    CONTRACT("CONTRACT", "电子合同费用结算"),
    CREDIT_REPORT("CREDIT_REPORT", "风控报告费用结算"),
    ;


    @JsonValue
    @EnumValue
    private String code;
    private String msg;

    public static EnumShopFundOperate find(Integer code) {
        return Arrays.stream(EnumShopFundOperate.values())
                .filter(input -> input.getCode()
                        .equals(code))
                .findFirst()
                .orElse(null);
    }
}
