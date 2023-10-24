package com.rent.common.dto.order.response;

import com.rent.common.enums.order.PaymentMethod;
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
@Schema(description = "订单重新支付响应参数")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderFreezeAgainResponse implements Serializable {

    private static final long serialVersionUID = 9129675550421135874L;

    @Schema(description = "冻结预授权url")
    private String freezeAgainUrl;

    @Schema(description = "流水号")
    private String serialNo;


    @Schema(description = "支付url")
    private String payUrl;

    @Schema(description = "是否是预授权转支付")
    private Boolean freeze;

    @Schema(description = "支付方式")
    private PaymentMethod paymentMethod;


}
