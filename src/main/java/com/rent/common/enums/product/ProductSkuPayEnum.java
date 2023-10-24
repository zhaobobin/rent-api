package com.rent.common.enums.product;

import java.math.BigDecimal;

/**
 * @author 12132
 */
public enum ProductSkuPayEnum {

    PHASE_PRICE3(3, new BigDecimal("0.023")),
    PHASE_PRICE6(6, new BigDecimal("0.045")),
    PHASE_PRICE12(12, new BigDecimal("0.075"));

    private Integer code;
    private BigDecimal msg;

    private ProductSkuPayEnum(Integer code, BigDecimal msg) {
        this.code = code;
        this.msg = msg;
    }

    public static ProductSkuPayEnum find(Integer code) {
        for (ProductSkuPayEnum instance : ProductSkuPayEnum.values()) {
            if (instance.getCode()
                .equals(code)) {
                return instance;
            }
        }
        return null;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public BigDecimal getMsg() {
        return msg;
    }

    public void setMsg(BigDecimal msg) {
        this.msg = msg;
    }

}
