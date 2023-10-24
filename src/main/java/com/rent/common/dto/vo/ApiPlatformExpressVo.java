package com.rent.common.dto.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "快递公司列表")
@Data
public class ApiPlatformExpressVo {

    @Schema(description = "快递公司名称")
    private String name;

    @Schema(description = "快递公司ID")
    private Long id;

}
