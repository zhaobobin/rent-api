package com.rent.common.dto.order.resquest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author xiaoyao
 * @version V1.0
 * @date 2020-6-17 13:40
 * @since 1.0
 */
@Schema(description = "用户支付结算单请求类")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserPaySettlementReqDto implements Serializable {

    private static final long serialVersionUID = -6695434499314001227L;

    @Schema(description = "订单编号", required = true)
    @NotBlank
    private String orderId;
    @Schema(description = "订单编号", required = true)
    @DecimalMin(value = "0.01", message = "支付金额不能为零")
    private BigDecimal amount;
    @Schema(description = "阿里用户id", required = true)
    private String aliUserId;
    @Schema(description = "渠道id", required = true)
    @NotBlank
    private String channelId;

}
