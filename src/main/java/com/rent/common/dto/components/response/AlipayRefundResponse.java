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
 * @date 2020-7-2 上午 10:37:02
 * @since 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "订单退款响应类")
public class AlipayRefundResponse implements Serializable {

    private static final long serialVersionUID = 1809404522552501844L;

    private String orderId;
}
