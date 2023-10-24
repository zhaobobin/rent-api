package com.rent.common.enums.product;


import lombok.Getter;

/**
 * @author 12132
 */
@Getter
public enum ProductSearchTypeEnum {

    SHOP_RULE("SHOP_RULE", "店铺查询"),
    ;


    private String code;
    private String msg;

    private ProductSearchTypeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }



}
