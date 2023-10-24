package com.rent.common.dto.api.request;

import com.rent.common.enums.EnumBankCardType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Schema(description = "用户银行卡签约")
public class BindBankCard {

    @Schema(description = "银行卡类型 1 储蓄卡 ")
    @NotNull(message = "银行卡类型不能为空")
    private EnumBankCardType cardType;

    @Schema(description = "银行预留手机号")
    @NotNull(message = "银行预留手机号不能为空")
    @NotBlank(message = "银行预留手机号不能为空")
    private String mobileNo;

    @Schema(description = "银行卡号")
    @NotBlank(message = "银行卡号不能为空")
    private String cardNo;

    @Schema(description = "信用卡年份有效期")
    private String expYear;


    @Schema(description = "信用卡月份有效期")
    private String expMonth;

    @Schema(description = "信用卡安全码")
    private String cvv;

    @Schema(description = "验证码发送ID")
    private String msgId;

    @Schema(description = "验证码")
    private String captchaCode;
}
