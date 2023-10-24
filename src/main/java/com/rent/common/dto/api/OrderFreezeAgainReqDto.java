package com.rent.common.dto.api;

import com.rent.common.enums.order.PaymentMethod;
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
 * @date 2020-6-17 9:48
 * @since 1.0
 */
@Schema(description = "用户重新支付请求类")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderFreezeAgainReqDto implements Serializable {

    @Schema(description = "订单编号")
    @NotBlank
    private String orderId;


    @Schema(description = "优惠券ID")
    private Long templateId;


    @Schema(description = "支付方式")
    private PaymentMethod paymentMethod;

    @Schema(description = "已绑定卡Id")
    private String bindCardId;

    @Schema(description = "短信验证码-银行卡支付需要验证码")
    private String verifyCode;

}
