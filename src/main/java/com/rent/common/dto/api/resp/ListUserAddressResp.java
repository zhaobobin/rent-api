package com.rent.common.dto.api.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;


@Data
@Schema(description = "用户添加收货地址请求")
public class ListUserAddressResp implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "Id")
    private Long id;

    @Schema(description = "所属用户id")
    private String uid;

    @Schema(description = "省")
    private Integer province;

    @Schema(description = "省-字符串")
    private String provinceStr;

    @Schema(description = "市")
    private Integer city;

    @Schema(description = "市-字符串")
    private String cityStr;

    @Schema(description = "区")
    private Integer area;

    @Schema(description = "区-字符串")
    private String areaStr;

    @Schema(description = "详细地址")
    private String street;

    @Schema(description = "手机号码")
    private String telephone;

    @Schema(description = "收货人姓名")
    private String realname;

    @Schema(description = "是否为默认收货地址  0否 1是 ")
    private Boolean isDefault;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
