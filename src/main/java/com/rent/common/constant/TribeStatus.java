package com.rent.common.constant;

/**
 * @author udo
 */
public enum TribeStatus {

    //部落视频播放
    SUPPORT(1, "支持视频播放"),
    NOT_SUPPORT(0, "不支持视频播放"),

    //是否发送过消息
    IS_SEND(1, "已发送"),
    NOT_SEND(0, "未发送"),

    //是否生效
    IS_STATUS(0, "有效"),
    IS_NOT_STATUS(1, "无效");


    private Integer code;
    private String desc;

    TribeStatus(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
