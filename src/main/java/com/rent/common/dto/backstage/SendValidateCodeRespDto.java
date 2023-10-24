package com.rent.common.dto.backstage;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author udo
 */
@Data
@Schema(description = "后台用户发送短信验证码返回结果")
public class SendValidateCodeRespDto {

    @Schema(description = "codeKey,验证时前端原样传给后端")
    private String codeKey;

    @Schema(description = "codeTime,,验证时前端原样传给后端")
    private Long codeTime;
}
