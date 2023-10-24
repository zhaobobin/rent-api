package com.rent.common.dto.backstage.request;

import com.rent.common.dto.Page;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "分页获取小程序订单投诉 请求参数")
public class QueryOrderComplaintsPageReqVo extends Page {

    @Schema(description = "用户名称")
    private String name;

    @Schema(description = "投诉订单ID")
    private String orderId;

    @Schema(description = "投诉商户名称")
    private String shopName;

    @Schema(description = "投诉类型")
    private Long typeId;

    @Schema(description = "用户手机")
    private String telphone;

    @Schema(description = "处理状态 0未处理 1已处理")
    private Long status;
}
