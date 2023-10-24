package com.rent.common.dto.backstage;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author zhaowenchao
 */
@Data
@Schema(description = "业务报表响应参数")
public class OrderReportFormDto {

    @Schema(description = "开始时间")
    private Date begin;

    @Schema(description = "结束时间")
    private Date end;

    @Schema(description = "action")
    private String action;

    @Schema(description = "position")
    private String position;

    @Schema(description = "营销场景-channel")
    private String channel;

    @Schema(description = "店铺ID")
    private String shopName;

    @Schema(description = "商品名称")
    private String productName;

    @Schema(description = "统计数据")
    List<OrderReportFormChannelDto> orderReportFormChannel;



}
