package com.rent.common.constant;

import java.util.Arrays;

/**
 * @program: components-center
 * @description:
 * @author: yr
 * @create: 2020-09-21 13:56
 **/
public enum MessageTypeStatus {
    //模板消息类型--LEASE，AUTHORIZED_LOGIN，COLLECTION
    LEASE("LEASE", "租赁"),
    AUTHORIZED_LOGIN("AUTHORIZED_LOGIN", "授权"),
    COLLECTION("COLLECTION", "收藏");


    private String code;
    private String msg;

    public static MessageTypeStatus find(String code) {
        return Arrays.stream(MessageTypeStatus.values())
                .filter(input -> input.getCode()
                        .equals(code))
                .findFirst()
                .orElse(null);
    }


    private MessageTypeStatus(String code, String msg) {
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
