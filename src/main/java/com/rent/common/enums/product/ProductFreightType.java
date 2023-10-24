package com.rent.common.enums.product;

import java.util.Arrays;

/**
 * @author 12132
 */

public enum ProductFreightType {


    //商品物流状态--
    // `freight_type` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '快递方式-包邮:FREE-到付:PAY-自提:SELF-自定义:12',
    IS_FREE_TYPE("FREE","商家包邮"),
    IS_PAY_TYPE("PAY","用户支付"),
    IS_SELF_TYPE("SELF","自提"),
    //此字段仅支持前端调试--
    IS_CUSTOMIZE_TYPE("CUSTOMIZE","自定义");






    private String code;
    private String msg;

    public static ProductFreightType find(String code) {
        return Arrays.stream(ProductFreightType.values())
                .filter(input -> input.getCode()
                        .equals(code))
                .findFirst()
                .orElse(null);
    }


    private ProductFreightType(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    }
