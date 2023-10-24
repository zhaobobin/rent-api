package com.rent.common.dto.order;

import com.rent.common.enums.order.EnumOrderSettlementStatus;
import com.rent.common.enums.order.EnumOrderSettlementType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单结算记录
 *
 * @author xiaoyao
 * @Date 2020-06-19 10:49
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "订单结算记录")
public class OrderSettlementInfoDto implements Serializable {

    private static final long serialVersionUID = 6789963248792137890L;


    /**
     * OrderId
     */
    @Schema(description = "OrderId")
    private String orderId;

    /**
     * 结算状态 01：待支付，02:支付中 03:已结算 04：用户申请修改
     */
    @Schema(description = "结算状态 01：待支付，02:支付中 03:已结算 04：用户申请修改")
    private EnumOrderSettlementStatus settlementStatus;

    /**
     * 结算类型 01:完好 02:损坏 03:丢失 04:违约
     */
    @Schema(description = "结算类型 01:完好 02:损坏 03:丢失 04:违约")
    private EnumOrderSettlementType settlementType;

    /**
     * 金额
     */
    @Schema(description = "金额")
    private BigDecimal amount;

    /**
     * 支付时间
     * 
     */
    @Schema(description = "支付时间")
    private Date paymentTime;

}
