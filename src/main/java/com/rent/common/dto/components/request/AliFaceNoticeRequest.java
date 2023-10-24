package com.rent.common.dto.components.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;


@Data
@Schema(description = "人脸认证结果回调入参")
public class AliFaceNoticeRequest implements Serializable {

    private static final long serialVersionUID = -348646664117061184L;

    /***
     * 认证初始化id
     */
    @Schema(description = "用户名称")
    private String certifyId;

}
