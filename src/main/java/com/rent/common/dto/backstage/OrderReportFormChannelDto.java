package com.rent.common.dto.backstage;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author zhaowenchao
 */
@Data
@Schema(description = "业务报表响应参数-单个渠道")
public class OrderReportFormChannelDto {

    @Schema(description = "渠道来源")
    private String channelName;

    @Schema(description = "下单总量")
    private Integer total;

    @Schema(description = "未支付主动取消")
    private Integer unPayUserApply;

    @Schema(description = "未支付主动取消率")
    private String unPayUserApplyRate;

    @Schema(description = "支付失败")
    private Integer payFailed;

    @Schema(description = "支付失败率")
    private String payFailedRate;


    @Schema(description = "支付超时")
    private Integer overTimePay;

    @Schema(description = "支付超时率")
    private String overTimePayRate;

    @Schema(description = "支付总数")
    private Integer totalPaid;

    @Schema(description = "支付转化率")
    private String totalPaidRate;


    @Schema(description = "系统风控拒绝")
    private Integer riskClose;

    @Schema(description = "系统风控拒绝率")
    private String riskCloseRate;


    @Schema(description = "商家审批订单数")
    private Integer businessAudit;

    @Schema(description = "商家拒绝")
    private Integer businessAuditRefuse;

    @Schema(description = "商家拒绝率")
    private String businessAuditRefuseRate;

    @Schema(description = "平台审批订单数")
    private Integer platformAudit;

    @Schema(description = "平台拒绝")
    private Integer platformAuditRefuse;

    @Schema(description = "平台拒绝率")
    private String platformAuditRefuseRate;

    @Schema(description = "风控+人工审批拒绝率")
    private String totalRefuseRate;

    @Schema(description = "已支付主动取消")
    private Integer paidUserApply;

    @Schema(description = "已支付主动取消率")
    private String paidUserApplyRate;

    @Schema(description = "待审批")
    private Integer toAudit;

    @Schema(description = "待终审")
    private Integer toFinalAudit;

    @Schema(description = "待发货")
    private Integer pendingDeal;

    @Schema(description = "成交订单")
    private Integer dealt;

    @Schema(description = "成交/支付")
    private String dealtPaidRate;

    @Schema(description = "成交/下单")
    private String dealtOrderRate;

}
