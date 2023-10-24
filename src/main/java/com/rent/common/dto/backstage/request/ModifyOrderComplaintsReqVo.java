package com.rent.common.dto.backstage.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Size;

@Data
@Schema(description = "获取小程序订单投诉详情 请求参数")
public class ModifyOrderComplaintsReqVo {

    @Schema(description = "Id")
    private Long id;

    @Schema(description = "平台处理结果")
    @Size(max=800,message="字数超出，最多{max}个字")
    private String result;



}
