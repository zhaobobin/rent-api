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
public class ChannelAccountPeriodDetailDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    private Long id;

    @Schema(description = "结算日期")
    private Date settleDate;

    @Schema(description = "渠道名称")
    private String marketingChannelName;

    @Schema(description = "状态")
    private EnumAccountPeriodStatus status;

    @Schema(description = "结算总金额-给商家的钱")
    private BigDecimal totalSettleAmount;

    @Schema(description = "佣金")
    private BigDecimal totalBrokerage;

    @Schema(description = "实际结算金额")
    private BigDecimal realAmount;

    @Schema(description = "结算流程")
    List<AccountPeriodProgressDto> progresses;



    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
