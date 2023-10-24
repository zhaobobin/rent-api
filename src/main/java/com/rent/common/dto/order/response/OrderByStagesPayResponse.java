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
 * @version V1.0
 * @date 2020-7-17 下午 5:06:35
 * @since 1.0
 */
@Data
@Schema(description = "分期主动支付响应类")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderByStagesPayResponse implements Serializable {

    private static final long serialVersionUID = -6475538227066465731L;

    /**
     * 支付url
     */
    @Schema(description = "支付地址")
    private String payUrl;

    @Schema(description = "支付流水号")
    private String serialNo;


    @Schema(description = "支付方式")
    private PaymentMethod paymentMethod;

}
