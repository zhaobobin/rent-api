package com.rent.common.dto.order.resquest;

import com.rent.common.dto.Page;
import com.rent.common.enums.order.EnumOrderStatus;
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
 * 用户订单
 *
 * @author xiaoyao
 * @Date 2020-06-10 17:02
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChannelUserOrdersReqDto extends Page implements Serializable {

    private static final long serialVersionUID = 6753499257948038540L;

    @Schema(description = "订单号")
    private String orderId;
    @Schema(description = "渠道编号")
    private String marketingChannelId;
    @Schema(description = "渠道名称")
    private String channelName;
    @Schema(description = "订单状态")
    private EnumOrderStatus status;
    @Schema(description = "结算状态")
    private String settleStatus;
    @Schema(description = "下单时间")
    private Date timeBegin;
    @Schema(description = "下单时间")
    private Date timeEnd;
    @Schema(description = "渠道名称")
    private String name;


    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
