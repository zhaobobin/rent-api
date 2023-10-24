/**
 * Copyright 2020 bejson.com
 */
package com.rent.common.dto.components.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;


@Schema(description = "电子签章签署结果响应")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContractResultResponse implements Serializable {

    private static final long serialVersionUID = 6067809887615572886L;

    @Schema(description = "合同code")
    private String contractCode;

    @Schema(description = "签署人流水号")
    private String signerCodes;

    @Schema(description = "文件下载地址")
    private String fileUrl;


}
