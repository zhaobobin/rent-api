package com.rent.common.dto.order.resquest;

import com.rent.common.enums.order.EnumOrderSettlementType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author xiaoyao
 * @version V1.0
 * @date 2020-6-18 16:38
 * @since 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "商家结算请求类")
public class BusinessIssuedStatementsReqDto implements Serializable {

    private static final long serialVersionUID = 1774438202421526441L;

    @NotBlank
    @Schema(description = "订单编号", required = true)
    private String orderId;

    @NotNull
    @Schema(description = "结算类型  01:完好 02:损坏 03:丢失 04:违约", required = true)
    private EnumOrderSettlementType settlementType;

    @NotNull
    @Schema(description = "丢失赔偿金额", required = true)
    private BigDecimal lossAmount;

    @NotNull
    @Schema(description = "损坏赔偿金额", required = true)
    private BigDecimal damageAmount;

    @Schema(description = "违约金额")
    @NotNull
    private BigDecimal penaltyAmount;

    /**
     * 操作人名称
     */
    private String operatorName;

}
