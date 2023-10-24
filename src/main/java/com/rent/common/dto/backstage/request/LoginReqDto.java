package com.rent.common.dto.backstage.request;

import com.rent.common.enums.user.EnumBackstageUserPlatform;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author udo
 */
@Data
@Schema(description = "后台用户登录请求参数")
public class LoginReqDto {

    @Schema(description = "手机号")
    @NotBlank(message = "手机号不能为空")
    private String mobile;

    @Schema(description = "密码")
    @NotBlank(message = "密码不能为空")
    private String password;

    @Schema(description = "用户类型")
    @NotNull(message = "用户类型不能为空")
    private EnumBackstageUserPlatform userType;

    @Schema(description = "验证码")
    @NotBlank(message = "验证码不能为空")
    private String code;

}
