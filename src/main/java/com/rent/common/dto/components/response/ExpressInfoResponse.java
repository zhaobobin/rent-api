/**
 * Copyright 2020 bejson.com
 */
package com.rent.common.dto.components.response;

import com.rent.common.dto.components.bean.ExpressResult;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Schema(description = "物流信息查询响应")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExpressInfoResponse implements Serializable {

    private static final long serialVersionUID = 6067809887615572886L;
    @Schema(description = "查询结果")
    private ExpressResult result;
    @Schema(description = "描述")
    private String reason;
    /** 错误码，0表示查询正常，其他表示查询不到物流信息或发生了其他错误 */
    @Schema(description = "错误码，0表示查询正常，其他表示查询不到物流信息或发生了其他错误 ")
    private int error_code;
    @Schema(description = "查询结果")
    private String resultcode;

}
