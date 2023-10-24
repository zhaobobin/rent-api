package com.rent.common.dto.backstage;

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
 * @date 2020-8-7 下午 3:13:44
 * @since 1.0
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "后台关单请求参数")
public class BackstageCloseOrderRequest implements Serializable {

    private static final long serialVersionUID = 4561603582689782752L;

    @NotBlank
    @Schema(description = "订单编号")
    private String orderId;

    @NotBlank(message = "拒绝原因不能为空")
    @Schema(description = "拒绝原因")
    private String closeReason;


}
