package com.rent.common.dto.backstage;

import com.rent.common.enums.user.EnumBackstageUserPlatform;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author udo
 */
@Data
@Schema(description = "后台用户重置密码请求参数")
public class RestPasswordOnLoginedReqDto {

    @Schema(description = "手机号")
    @NotBlank(message = "手机号不能为空")
    @Length(min = 11,max = 11,message = "手机号格式错误")
    private String mobile;

    @Schema(description = "用户类型")
    @NotNull(message = "用户类型不能为空")
    private EnumBackstageUserPlatform userType;

    @Schema(description = "原密码")
    @NotBlank(message = "原密码不能为空")
    private String oldPassword;

    @Schema(description = "新密码")
    @NotBlank(message = "新密码不能为空")
    private String newPassword;

}
