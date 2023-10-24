package com.rent.common.enums.marketing;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * @author zhaowenchao
 */
@Getter
@AllArgsConstructor
public enum PageElementConfigTypeEnum {
    /** 描述 */
    INDEX_BANNER("INDEX_BANNER", "首页轮播图"),
    SPECIAL_AREA_MAIN("SPECIAL_AREA_MAIN", "lite版本小程序专区主图"),
    SPECIAL_AREA_SUB("SPECIAL_AREA_SUB", "lite版本小程序专区副图"),
    PRODUCT_BANNER("PRODUCT_BANNER", "商品页面广告位"),
    MY_ORDER("MY_ORDER", "我的订单"),
    MY_SERVICE("MY_SERVICE", "我的服务"),
    SPECIAL_TITLE_MAIN("SPECIAL_TITLE_MAIN", "专区主标题"),
    SPECIAL_TITLE_SUB("SPECIAL_TITLE_SUB", "专区副标题"),
    ICON_AREA("ICON", "金刚区"),
    ;

    /** 状态码 */
    @JsonValue
    @EnumValue
    private String code;

    /** 状态描述 */
    private String description;

    /**
     * 根据编码查找枚举
     *
     * @param code 编码
     * @return {@link PageElementConfigTypeEnum } 实例
     **/
    public static PageElementConfigTypeEnum find(String code) {
        for (PageElementConfigTypeEnum instance : PageElementConfigTypeEnum.values()) {
            if (instance.getCode().equals(code)) {
                return instance;
            }
        }
        return null;
    }
}
