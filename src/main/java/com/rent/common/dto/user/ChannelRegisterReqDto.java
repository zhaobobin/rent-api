package com.rent.common.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

/**
 * @author udo
 */
@Data
@Schema(description = "添加渠道用户请求参数")
public class ChannelRegisterReqDto {

    @Schema(description = "手机号码")
    @NotBlank(message = "手机号不能为空")
    private String mobile;

    @Schema(description = "名称")
    @NotBlank(message = "名称不能为空")
    private String name;

    @Schema(description = "分账比例，小于等于1")
    @DecimalMin(value = "0",message = "分账比例不能小于0")
    @DecimalMax(value = "1",message = "分账比例不能大于1")
    private BigDecimal scale;

    @Schema(description = "渠道支付宝账号")
    @NotBlank(message = "渠道支付宝账号不能为空")
    private String identity;

    @Schema(description = "渠道支付宝账号实名认证姓名")
    @NotBlank(message = "渠道支付宝账号实名认证姓名不能为空")
    private String aliName;


}
