package com.rent.common.dto.backstage.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "获取小程序订单投诉详情 请求参数")
public class QueryOrderComplaintsDetailReqVo {

    @Schema(description = "Id")
    private Long id;

}
