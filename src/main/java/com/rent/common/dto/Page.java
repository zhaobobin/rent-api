package com.rent.common.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author zhaowenchao
 */
@Data
@Schema(description = "分页请求参数")
public class Page {

    @Schema(description = "页码")
    private Integer pageNumber = 1;

    @Schema(description = "页面容量")
    private Integer pageSize = 20;
}
