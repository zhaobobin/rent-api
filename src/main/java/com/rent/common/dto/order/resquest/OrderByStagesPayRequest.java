package com.rent.common.dto.order.resquest;

import com.rent.common.enums.order.PaymentMethod;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@ToString
public class OrderByStagesPayRequest implements Serializable {

    //订单号
    @NotBlank
    private String orderId;

    //金额
    @DecimalMin(value = "0.01", message = "还款不能为零")
    private BigDecimal totalAmount;

    //支付宝Id h5传ip地址 jd openId
    private String buyerId;

    //期次
    @NotNull
    private List<String> periodList;


    @Schema(description = "支付方式")
    private PaymentMethod paymentMethod;

    @Schema(description = "已绑定卡Id")
    private String bindCardId;

    @Schema(description = "短信验证码-银行卡支付需要验证码")
    private String verifyCode;


}
