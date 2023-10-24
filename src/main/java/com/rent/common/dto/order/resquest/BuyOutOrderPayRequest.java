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
 * @date 2020-7-2 上午 9:31:37
 * @since 1.0
 */
@Data
@Schema(description = "买断支付请求类")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BuyOutOrderPayRequest implements Serializable {

    private static final long serialVersionUID = 9192401301083003784L;

    @NotBlank(message = "订单id不能为空")
    @Schema(description = "订单id")
    private String orderId;

    @Schema(description = "优惠券id")
    private String couponId;

}
