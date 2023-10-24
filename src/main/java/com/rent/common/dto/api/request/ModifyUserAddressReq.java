package com.rent.common.dto.api.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


@Data
@Schema(description = "用户添加收货地址请求")
public class ModifyUserAddressReq implements Serializable {

    @Schema(description = "用户收货地址ID")
    @NotNull(message = "用户收货地址ID不能为空")
    private Long id;

    @Schema(description = "省")
    private Integer province;

    @Schema(description = "市")
    private Integer city;

    @Schema(description = "区")
    private Integer area;

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
