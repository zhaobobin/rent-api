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
 * @date 2020-6-17 13:40
 * @since 1.0
 */
@Schema(description = "用户确认收货请求类")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderConfirmReceiptReqDto implements Serializable {

    private static final long serialVersionUID = 2952473290617061842L;

    @Schema(description = "订单编号")
    @NotBlank
    private String orderId;

}
