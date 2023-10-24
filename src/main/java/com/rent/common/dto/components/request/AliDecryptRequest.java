package com.rent.common.dto.components.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author Boan
 * @version 1.0
 * @date 2020/2/24
 * @desc 支付宝解密入参
 */
@Data
@Builder
@Schema(description = "支付宝解密入参")
public class AliDecryptRequest {

    /**
     * 解密内容
     */
    @NotBlank
    @Schema(description = "解密内容")
    private String content;

}
