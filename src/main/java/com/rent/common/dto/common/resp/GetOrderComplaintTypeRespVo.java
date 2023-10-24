package com.rent.common.dto.common.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


@Data
@Schema(description = "订单投诉类型")
public class GetOrderComplaintTypeRespVo {

    @Schema(description = "Id")
    private Long id;

    @Schema(description = "类型")
    private String name;

}
