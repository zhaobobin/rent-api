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


@Schema(description = "电子签章签署响应")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContractResponse implements Serializable {

    private static final long serialVersionUID = 6067809887615572886L;

    @Schema(description = "合同下载链接")
    private String downloadUrl;

    @Schema(description = "签署人流水号")
    private String signerCodes;

}
