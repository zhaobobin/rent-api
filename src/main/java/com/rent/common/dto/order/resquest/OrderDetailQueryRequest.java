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
 * @date 2020-7-8 下午 3:34:07
 * @since 1.0
 */
@Data
@Schema(description = "用户订单详情查询参数")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailQueryRequest implements Serializable {

    private static final long serialVersionUID = -5233217674035915587L;

    /**
     * 订单id
     */
    @NotBlank
    @Schema(description = "订单id")
    private String orderId;
}
