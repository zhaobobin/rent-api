package com.rent.common.dto.api.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author zhaowenchao
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(description = "账单试算请求类")
public class TrailLiteRequest implements Serializable {

    private static final long serialVersionUID = -240898011031761579L;

    @Schema(description = "租期")
    @NotNull(message = "租期不能为空")
    private int duration;

    @Schema(description = "用户优惠券ID")
    private String couponId;

    @Schema(description = "用户ID")
    @NotBlank(message = "用户ID不能为空")
    private String uid;

    @Schema(description = "skuId")
    @NotNull(message = "skuId不能为空")
    private Long skuId;

    @Schema(description = "增值服务ID列表")
    private List<String> additionalServicesIds;



}
