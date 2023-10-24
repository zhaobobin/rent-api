package com.rent.common.enums.product;

import java.util.Arrays;

/**
 * @author 12132
 */

public enum ProductStatus {

    //商品上下架
    PUT_ON_SHELF(1, "已上架"),
    PUT_ON_DEPOT(2, "已下架"),
    //商品状态
    STATUS(1, "有效"),
    NO_STATUS(0, "无效"),
    //商品审核状态
    AUDIT_PASS_STATS(2, "审核通过"),
    AUDIT_FAIL_STATS(1, "审核不通过"),
    AUDIT_REVIEW_STATS(0, "审核中"),
    SHOP_STATUS(1, "有效"),

    /**
     * 是否支持买断，-是否支持买断 1:可以买断-支持提前买断  2:可以买断-支持到期买断   0:不可以买断
     *买断规则:以前的数据默认支持提前买断
     */
    IS_BUY_OUT(1,"支持买断-支持提前买断"),
    IS_BUY_OUT_MATURE(2,"支持买断-支持到期买断"),
    IS_NOT_BUY_OUT(0,"不支持买断"),


    /**
     * 归还规则，1:支持提前归还  2:支持到期归还
     *归还规则:以前的数据默认支持提前归还
     */
    RETURN_RULE_ADVANCE(1,"支持提前归还"),
    RETURN_RULE_MATURE(2,"支持到期归还"),

    //是否支持分期
    IS_STAGE(1,"支持分期"),
    IS_NOT_STAGE(0,"不支持分期"),


    //分期参数
    STAGE_TYPE_ALL(0,"全部分期类型"),

    //是否包含手续费
    IS_HANDLING_FEE(0,"包含手续费"),
    IS_NOT_HANDLING_FEE(1,"不包含手续费");



    private Integer code;
    private String msg;

    public static ProductStatus find(Integer code) {
        return Arrays.stream(ProductStatus.values())
                .filter(input -> input.getCode()
                        .equals(code))
                .findFirst()
                .orElse(null);
    }


    private ProductStatus(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    }
