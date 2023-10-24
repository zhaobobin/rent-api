/**
 * hsjry.com Inc. Copyright (c) 2014-2015 All Rights Reserved.
 **/
package com.rent.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 公用错误类型
 *
 * @author xiaoyao
 **/
@Getter
@AllArgsConstructor
public enum EnumResponseType {
    /**
     * 执行成功
     */
    SUCCESS("HZSX_SUCCESS", "执行成功"),
    /**
     * 流程错误
     **/
    FLOW_ERR("HZSX_FLOW_ERR", "流程错误"),
    /**
     * 验证错误
     **/
    VALIDATE_ERR("HZSX_VALIDATE_ERR", "验证错误"),
    /**
     * 系统错误
     **/
    SYS_ERR("HZSX_SYS_ERR", "系统错误"),
    /**
     * 重复提交
     */
    REPEAT_SUBMIT_ERR("HZSX_REPEAT_ERR", "禁止短时间内重复操作！！！"),
    /**
     * 数据库异常
     */
    DB_ERR("HZSX_DB_ERR", "数据库异常"),
    ;

    /**
     * 状态码
     **/
    private String code;
    /**
     * 状态描述
     **/
    private String description;
}
