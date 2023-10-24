package com.rent.common.enums.product;

import java.util.Arrays;

/**
 * @author 12132
 */

public enum ShopStatus {

    //店铺资质类型
    PIC_TYPE_LICENSE(0, "营业执照号"),
    PIC_TYPE_ORGANIZATION(1, "组织机构代码证"),
    PIC_TYPE_TAXATION(2, "税务登记证"),
    PIC_TYPE_IDCARD_DIRECT(3, "法人身份证正面"),
    PIC_TYPE_IDCARD_BACK(4, "法人身份证背面"),



    //店铺状态-0待提交企业资质 1待填写店铺信息 2待提交品牌信息 3正在审核 4审核不通过 5审核通过，准备开店 6开店成功',
    SHOP_STATUS_SUBMIT_ENTER(0,"待提交企业资质"),
    SHOP_STATUS_FILL(1,"待填写店铺信息"),
    SHOP_STATUS_SUBMIT_BRAND(2,"待提交品牌信息"),
    SHOP_STATUS_AUDIT(3,"正在审核"),
    SHOP_STATUS_AUDIT_FAIL(4,"审核不通过"),
    SHOP_STATUS_AUDIT_PASS(5,"审核通过"),
    SHOP_STATUS_SHOP_SUCCESS(6,"开店成功"),
    //店铺冻结状态
    SHOP_STATUS_NOT_LOCK(0,"店铺未冻结"),
    SHOP_STATUS_IS_LOCK(1,"店铺冻结"),







    //企业资质--0|正在审核;1|审核不通过;2|审核通过
    SHOP_ENTERPRISE_AUDIT(0,"正在审核"),
    SHOP_ENTERPRISE_AUDIT_PASS(1,"审核不通过"),
    SHOP_ENTERPRISE_AUDIT_SUCCESS(2,"审核通过");



    private Integer code;
    private String msg;

    public static ShopStatus find(Integer code) {
        return Arrays.stream(ShopStatus.values())
                .filter(input -> input.getCode()
                        .equals(code))
                .findFirst()
                .orElse(null);
    }


    private ShopStatus(Integer code, String msg) {
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
