package com.rent.common.dto.order.resquest;


import com.rent.common.dto.Page;
import com.rent.common.enums.order.EnumAccountPeriodStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.util.Date;

/**
 * 渠道账期表
 *
 * @author xiaotong
 * @Date 2020-06-16 10:09
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "渠道账期表")
public class ChannelAccountPeriodReqDto extends Page implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 商家id
     */
    @Schema(description = "渠道id")
    private String marketingChannelId;
    /**
     * 商家名称
     */
    @Schema(description = "渠道名称")
    private String marketingChannelName;
    /**
     * 状态：待结算；待审核；待支付；已支付
     */
    @Schema(description = "状态")
    private EnumAccountPeriodStatus status;
    /**
     * 结算日期
     */
    @Schema(description = "结算日期")
    private Date settleDate;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
