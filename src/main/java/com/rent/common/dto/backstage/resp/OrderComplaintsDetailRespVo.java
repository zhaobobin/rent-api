package com.rent.common.dto.backstage.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Data
@Schema(description = "获取小程序订单投诉详情 响应结果")
public class OrderComplaintsDetailRespVo {

    @Schema(description = "Id")
    private Long id;

    @Schema(description = "用户名称")
    private String name;

    @Schema(description = "投诉订单ID")
    private String orderId;

    @Schema(description = "用户手机")
    private String telphone;

    @Schema(description = "投诉类型名称")
    private String typeName;

    @Schema(description = "投诉商户名称")
    private String shopName;

    @Schema(description = "CreateTime")
    private Date createTime;

    @Schema(description = "投诉内容")
    private String content;

    @Schema(description = "评价图片")
    private List<String> images;

    @Schema(description = "平台处理结果")
    @Size(max=800,message="字数超出，最多{max}个字")
    private String result;
}
