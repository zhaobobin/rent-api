package com.rent.common.dto.order;

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
@Schema(description = "渠道用户订单")
public class ChannelUserOrdersDto implements Serializable {

    private static final long serialVersionUID = 1L;


    @Schema(description = "Id")
    private Long id;
    @Schema(description = "订单号")
    private String orderId;
    @Schema(description = "店铺名称")
    private String shopName;
    @Schema(description = "渠道名称")
    private String channelName;
    @Schema(description = "商品名称")
    private String productName;
    @Schema(description = "当前期次")
    private Integer currentPeriods;
    @Schema(description = "总期次")
    private Integer totalPeriods;
    @Schema(description = "总租金")
    private BigDecimal totalRent;
    @Schema(description = "已付租金")
    private BigDecimal payRent;
    @Schema(description = "客户姓名")
    private String realName;
    @Schema(description = "用户手机号码")
    private String telephone;
    @Schema(description = "下单时间")
    private Date createTime;
    @Schema(description = "订单状态")
    private String status;
    @Schema(description = "结算状态")
    private String settleStatus;
    @Schema(description = "渠道结算金额")
    private BigDecimal settleAmount;

    @Schema(description = "营销码")
    private String marketingId;
    @Schema(description = "下单用户id")
    private String uid;
    @Schema(description = "用户姓名")
    private String userName;
    @Schema(description = "确认结算时间")
    private Date confirmSettlementTime;
    @Schema(description = "RequestNo")
    private String requestNo;
    @Schema(description = "渠道来源")
    private String channelId;
    @Schema(description = "商品id")
    private String productId;



    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
