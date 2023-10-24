package com.rent.common.dto.api.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;


@Data
@Schema(description = "用户添加收货地址请求")
public class AddUserAddressReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "所属用户id")
    @NotBlank(message = "用户id不能为空")
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

    @Schema(description = "街道")
    private String street;

    @Schema(description = "手机号码")
    @NotBlank(message = "手机号码不能为空")
    private String telephone;

    @Schema(description = "收货人姓名")
    @NotBlank(message = "收货人姓名不能为空")
    private String realname;

    @Schema(description = "是否为默认收货地址  0否 1是 ")
    private Boolean isDefault;



    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
