package com.rent.common.enums.order;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xiaoyao
 * @version V1.0
 * @since 1.0 2020-6-15 15:05
 */
@Getter
@AllArgsConstructor
public enum EnumOrderAppointmentError {
    /**
     * ------------------------业务类异常-----------------------
     */
    BIZ_SUCCESS("000000", "success"),
    BIZ_FAILED("999999", "failed"),
    BIZ_TIMEOUT("020440", "timeout"),
    DB_ERROR("100000", "db_error"),


    /**
     * 预约
     */
    APPOINTMENT_UID_ERROR("9130906", "用户uid不可为空！！！"),
    APPOINTMENT_TEL_ERROR("9130906", "用户手机号不可为空！！！"),
    APPOINTMENT_PRODUCT_NAME_ERROR("9130906", "预约商品名称不可为空！！！"),
    APPOINTMENT_PRODUCT_COLOE_ERROR("9130906", "预约商品颜色不可为空！！！"),
    APPOINTMENT_PRODUCT_RAM_ERROR("9130906", "预约商品规格不可为空！！！"),
    APPOINTMENT_PRICE_ERROR("9130906", "支付价格不可为空！！！"),




    SHOP_CHECK_IDCARD_NAME("9130704", "法人姓名与身份证号不匹配,请仔细检查!");


    /**
     * 状态码
     */
    private String code;

    /**
     * 状态描述
     */
    private String msg;

    /**
     * 根据编码查找枚举
     *
     * @param code 编码
     * @return {@link  EnumOrderAppointmentError } 实例
     **/
    public static  EnumOrderAppointmentError find(String code) {
        for ( EnumOrderAppointmentError instance :  EnumOrderAppointmentError.values()) {
            if (instance.getCode()
                    .equals(code)) {
                return instance;
            }
        }
        return null;
    }
}
