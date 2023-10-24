package com.rent.common.enums.product;

import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * @author zhaowenchao
 */
@Getter
@AllArgsConstructor
public enum EnumSplitBillAccountStatus {
    /**
     */
    UNSET("UNSET", "未设置"),
    PENDING("PENDING", "待审核"),
    PASS("PASS", "审核通过"),
    REJECT("REJECT", "审核拒绝");


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
     * @return {@link EnumSplitBillAccountStatus } 实例
     **/
    public static EnumSplitBillAccountStatus find(String code) {
        for (EnumSplitBillAccountStatus instance : EnumSplitBillAccountStatus.values()) {
            if (instance.getCode()
                    .equals(code)) {
                return instance;
            }
        }
        return null;
    }
}
