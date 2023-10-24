package com.rent.common.dto.api.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author zhaowenchao
 */
@Data
@Schema(description = "用户确认订单请求参数")
public class LiteConfirmOrderReq implements Serializable {

    @Schema(description = "用户ID")
    @NotBlank(message = "用户ID不能为空")
    private String uid;

    @Schema(description = "skuId")
    @NotNull(message = "skuId不能为空")
    private Long skuId;

    @Schema(description = "租期")
    @NotNull(message = "租期不能为空")
    private Integer duration;

}
