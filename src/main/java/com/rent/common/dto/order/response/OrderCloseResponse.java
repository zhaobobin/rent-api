package com.rent.common.dto.order.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author xiaoyao
 */
@Data
@Schema(description = "订单关闭响应参数")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderCloseResponse implements Serializable {

    private static final long serialVersionUID = 9129675550421135874L;

    @Schema(description = "订单编号")
    private String orderId;

}
