package com.rent.common.dto.backstage;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @author udo
 */
@Data
@Schema(description = "商家用户注册请求参数")
public class RegisterReqDto {

    @Schema(description = "手机号")
    @NotBlank(message = "手机号不能为空")
    @Length(min = 11,max = 11,message = "手机号格式错误")
    private String mobile;

    @Schema(description = "密码")
    @NotBlank(message = "密码不能为空")
    private String password;

}
