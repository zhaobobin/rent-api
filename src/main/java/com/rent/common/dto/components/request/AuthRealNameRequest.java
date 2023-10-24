package com.rent.common.dto.components.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @author Boan
 * @version 1.0
 * @date 2020/2/24
 * @desc 实名认证入参
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "实名认证入参")
public class AuthRealNameRequest {

    /**
     * 姓名
     */
    @NotBlank
    @Schema(description = "姓名")
    private String realName;

    /***
     * 身份证号
     */
    @NotBlank
    @Schema(description = "身份证号")
    private String idNo;

}
