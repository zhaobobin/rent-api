package com.rent.common.dto.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rent.common.enums.order.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author xiaoyao
 * @version V1.0
 * @date 2020-8-4 上午 9:41:08
 * @since 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "后台订单列表")
public class BackstageUserOrderDto implements Serializable {

    private static final long serialVersionUID = 5846276777705107535L;

    @Schema(description = "订单编号")
    private String orderId;

    @Schema(description = "原订单编号")
    private String originalOrderId;

    @Schema(description = "店铺名称")
    private String shopName;

    @Schema(description = "渠道来源")
    private String channelName;

    @Schema(description = "渠道ID")
    private String channelId;

    @Schema(description = "商品名称")
    private String productName;

    @Schema(description = "当前租期")
    private int currentPeriods;

    @Schema(description = "已支付期数")
    private int payedPeriods;

    @Schema(description = "总租期租期")
    private int totalPeriods;

    @Schema(description = "已付租金")
    private BigDecimal payedRentAmount;

    @Schema(description = "已付租金")
    private BigDecimal settlementAmount;

    @Schema(description = "总租金")
    private BigDecimal totalRentAmount;

    @Schema(description = "下单人姓名")
    private String realName;

    @Schema(description = "下单人手机号")
    private String telephone;

    @Schema(description = "下单时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date placeOrderTime;

    @Schema(description = "收货人手机号")
    private String addressUserPhone;

    @Schema(description = "起租时间")
    private Date rentStart;

    @Schema(description = "归还时间")
    private Date unrentTime;

    @Schema(description = "订单状态 01:待支付 02:支付中 03:已支付申请关单 04:待发货 05:待确认收货 06:租用中 07:待结算 08:结算待支付 09:订单完成 10:交易关闭")
    private EnumOrderStatus status;

    @Schema(description = "电审状态 00:待平台审核 01:待商家审核 02:已审核")
    private EnumOrderExamineStatus examineStatus;

    @Schema(description = "00平台审核，01商家审核")
    private EnumOrderAuditLabel auditLabel;

    @Schema(description ="关单类型 01:未支付用户主动申请 02:支付失败 03:超时支付 04:已支付用户主动申请 05:风控拒绝 06:商家关闭订单 07:商家风控关闭订单 08:商家超时发货 09:平台关闭订单")
    private EnumOrderCloseType closeType;

    @Schema(description = "关闭原因")
    private String cancelReason;


    @Schema(description = "审核时间")
    private Date approveTime;

    @Schema(description = "审核人")
    private String approveUserName;


    @Schema(description = "审核结论")
    private EnumOrderAuditStatus approveStatus;

}
