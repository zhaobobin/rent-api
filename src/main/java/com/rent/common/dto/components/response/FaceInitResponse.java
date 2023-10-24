package com.rent.common.dto.components.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author xiaoyao
 * @version V1.0
 * @date 2020-7-2 下午 1:54:11
 * @since 1.0
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "人脸认证初始化响应类")
public class FaceInitResponse implements Serializable {

    private static final long serialVersionUID = -2312294577799046892L;

    @Schema(description = "人脸认证地址")
    private String faceUrl;

    @Schema(description = "人脸认证id")
    private String certifyId;
}
