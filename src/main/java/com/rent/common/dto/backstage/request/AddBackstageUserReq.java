package com.rent.common.dto.backstage.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


@Data
@Schema(description = "新增后台用户请求参数")
public class AddBackstageUserReq implements Serializable {

    @Schema(description = "手机号码")
    @NotBlank(message = "手机号不能为空")
    @Length(min = 11,max = 11,message = "手机号格式错误")
    private String mobile;

    @Schema(description = "成员姓名")
    @NotBlank(message = "成员姓名不能为空")
    private String name;

    @Schema(description = "邮箱地址")
    @NotBlank(message = "邮箱地址不能为空")
    @Email(message = "邮箱地址格式错误")
    private String email;

    @Schema(description = "密码")
    @NotBlank(message = "密码不能为空")
    private String password;

    @Schema(description = "备注信息")
    @Length(max = 256,message = "备注信息过长")
    private String remark;

    @Schema(description = "部门ID")
    @NotNull(message = "部门不能为空")
    private Long departmentId;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
