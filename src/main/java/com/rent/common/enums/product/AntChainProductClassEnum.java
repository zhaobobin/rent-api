package com.rent.common.enums.product;


import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * https://antchain.antgroup.com/docs/11/190781#h3-3-2-1-5
 * 蚂蚁链上链商品类目信息
 * @author zhaowenchao
 */
@Getter
public enum AntChainProductClassEnum {

    TC_MOBILE("3c_mobile", "3C",12,"手机"),
    TC_PC("3c_pc", "3C",12,"电脑"),
    TC_CAMERA("3c_camera", "3C",12,"摄影"),
    TC_OFFICE("3c_office", "3C",12,"办公设备"),
    TC_OTHER("3c_other", "3C",12,"3C-其他"),
    IOT_AUTO_CONTAINER("iot_auto_container", "IOT",12,"售卖柜"),
    IOT_STAGE("iot_stage", "IOT",12,"驿站"),
    IOT_OTHER("iot_other", "IOT",12,"IOT-其他"),
    NE_BATTERY("ne_battery", "NE",12,"电池"),
    NE_ELECTRIC_CAR("ne_electric_car", "NE",12,"电动车"),
    NE_OTHER("ne_other", "NE",12,"新能源-其他"),
    GENERAL_FURNITURE("general_furniture", "GENERAL",12,"家具"),
    GENERAL_TV("general_tv", "GENERAL",12,"家电"),
    GENERAL_OTHER("general_other", "GENERAL",12,"泛其他")
    ;

    @JsonValue
    @EnumValue
    private String code;
    private String parent;
    private int maxInsureMonth;
    private String name;


    private AntChainProductClassEnum(String code, String parent, int maxInsureMonth, String name) {
        this.code = code;
        this.maxInsureMonth = maxInsureMonth;
        this.parent = parent;
        this.name = name;
    }

    public static AntChainProductClassEnum find(String code) {
        for (AntChainProductClassEnum instance : AntChainProductClassEnum.values()) {
            if (instance.getCode()
                    .equals(code)) {
                return instance;
            }
        }
        return null;
    }
}
