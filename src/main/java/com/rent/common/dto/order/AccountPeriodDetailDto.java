package com.rent.common.dto.order;

import com.rent.common.enums.order.EnumAccountPeriodStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 账期表
 *
 * @author xiaotong
 * @Date 2020-06-16 10:09
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "账期表")
public class AccountPeriodDetailDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "结算日期")
    private Long id;

    @Schema(description = "结算日期")
    private Date settleDate;

    @Schema(description = "商家名称")
    private String shopName;

    @Schema(description = "结算账户")
    private String accountIdentity;

    @Schema(description = "结算账户姓名")
    private String accountName;

    @Schema(description = "状态")
    private EnumAccountPeriodStatus status;

    @Schema(description = "结算总金额-给商家的钱")
    private BigDecimal totalSettleAmount;

    @Schema(description = "佣金")
    private BigDecimal totalBrokerage;

    @Schema(description = "实际结算金额")
    private BigDecimal settleAmount;


    @Schema(description = "常规订单结算总金额")
    private BigDecimal rentAmount;

    @Schema(description = "常规订单结算金额")
    private BigDecimal rentSettleAmount;

    @Schema(description = "常规订单佣金")
    private BigDecimal rentBrokerage;

    @Schema(description = "购买订单结算总金额")
    private BigDecimal purchaseAmount;

    @Schema(description = "购买订单结算金额")
    private BigDecimal purchaseSettleAmount;

    @Schema(description = "购买订单佣金")
    private BigDecimal purchaseBrokerage;

    @Schema(description = "买断订单结算总金额")
    private BigDecimal buyoutAmount;

    @Schema(description = "买断订单结算金额")
    private BigDecimal buyoutSettleAmount;

    @Schema(description = "买断订单佣金")
    private BigDecimal buyoutBrokerage;

    @Schema(description = "结算单结算总金额")
    private BigDecimal orderSettleAmount;

    @Schema(description = "结算单结算金额")
    private BigDecimal orderSettleSettleAmount;

    @Schema(description = "结算单佣金")
    private BigDecimal orderSettleBrokerage;

    @Schema(description = "结算流程")
    List<AccountPeriodProgressDto> progresses;



    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
