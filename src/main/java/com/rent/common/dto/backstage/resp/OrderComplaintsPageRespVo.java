package com.rent.common.dto.backstage.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Data
@Schema(description = "分页获取小程序订单投诉 响应结果")
public class OrderComplaintsPageRespVo {

    @Schema(description = "Id")
    private Long id;

    @Schema(description = "用户名称")
    private String name;

    @Schema(description = "投诉订单ID")
    private String orderId;

    @Schema(description = "投诉商户名称")
    private String shopName;

    @Schema(description = "投诉类型名称")
    private String typeName;

    @Schema(description = "用户手机")
    private String telphone;

    @Schema(description = "CreateTime")
    private Date createTime;

    @Schema(description = "处理状态 0未处理 1已处理")
    private Long status;
}
