package com.rent.common.dto.components.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author xiaoyao
 * @version V1.0
 * @date 2020-9-25 下午 2:05:28
 * @since 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "身份证ocr识别请求类")
public class IdCardOcrRequest implements Serializable {

    private static final long serialVersionUID = -2007613195000982272L;

    @Schema(description = "uid")
    @NotBlank(message = "uid不能为空")
    private String uid;

    @Schema(description = "身份证正面url")
    @NotBlank(message = "身份证正面url不能为空")
    private String frontUrl;

    @Schema(description = "身份证反面url")
    @NotBlank(message = "身份证反面url不能为空")
    private String backUrl;
}
