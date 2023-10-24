package com.rent.common.dto.order.resquest;

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
 * @date 2020-7-27 上午 10:45:36
 * @since 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "获取归还地址请求类")
public class OrderGiveBackAddressRequest implements Serializable {

    private static final long serialVersionUID = 4870494124998634301L;

    @Schema(description = "订单id")
    @NotBlank
    private String orderId;
}
