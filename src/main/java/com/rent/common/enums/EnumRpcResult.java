package com.rent.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 通讯结果枚举
 *
 * @author xiaoyao
 */
@Getter
@AllArgsConstructor
public enum EnumRpcResult {
    /** 成功 */
    SUCCESS("000000", "成功"),
    /** 超时异常 */
    TIMEOUT_ERR("999998", "超时异常"),
    /** 失败 */
    FAIL("999999", "其他通讯异常");

    /** 编码 */
    private String code;
    /** 描述 */
    private String description;

}
