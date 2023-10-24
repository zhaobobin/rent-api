package com.rent.common.enums.components;

/**
 * @author udo
 */
public enum OrderCenterStatus {

    APPROVAL("APPROVAL", "审批中"),//ok
    REJECT("REJECT", "审核拒绝"),//ok
    TO_SEND_GOODS("TO_SEND_GOODS", "待发货"),//ok
    IN_DELIVERY("IN_DELIVERY", "已发货"),//ok
    IN_THE_LEASE("IN_THE_LEASE", "租赁中"),//ok
    CLOSED("CLOSED", "已取消"),//ok
    IN_THE_BACK("IN_THE_BACK", "归还中"),//ok
    BUYOUT("BUYOUT", "已买断"),
    FINISHED("FINISHED", "已归还"),//ok
    ;
    private String code;
    private String desc;

    OrderCenterStatus(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static OrderCenterStatus find(String code) {
        for (OrderCenterStatus instance : OrderCenterStatus.values()) {
            if (instance.getCode()
                    .equals(code)) {
                return instance;
            }
        }
        return null;
    }

}
