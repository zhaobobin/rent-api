package com.rent.common.dto.components.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author xiaoyao
 * @version V1.0
 * @date 2020-9-7 上午 9:18:40
 * @since 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "风控报告响应类")
public class RiskReportResponse implements Serializable {

    private static final long serialVersionUID = -1825105134074136412L;

    /** 天狼星报告 */
    @Schema(description = "天狼星报告")
    private String  siriusRiskReport;

    /** 报告类型 01：天狼星 02：新版风控报告 03：决策报告 04：被禁言*/
    @Schema(description = "报告类型 01：天狼星 02：新版风控报告 03：决策报告 04：被禁言")
    private String reportType;

}
