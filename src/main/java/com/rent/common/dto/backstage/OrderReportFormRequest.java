package com.rent.common.dto.backstage;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * @author zhaowenchao
 */
@Data
@Schema(description = "业务报表请求参数")
public class OrderReportFormRequest implements Serializable {

    private static final long serialVersionUID = 4561603582689782752L;

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

    @Schema(description = "店铺名称")
    private String shopName;

    @Schema(description = "店铺ID-前端不传后台查")
    private String shopId;

    @Schema(description = "产品ID")
    private String productId;

    @Schema(description = "渠道编号")
    private List<String> channelId;



}
