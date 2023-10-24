package com.rent.common.dto.components.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author xiaoyao
 * @version V1.0
 * @date 2020-8-4 下午 5:20:52
 * @since 1.0
 */
@Schema(description = "查询天狼星报告条件")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuerySiriusReportRequest implements Serializable {
    @Schema(description = "用户uid")
    @NotBlank
    private String uid;

    @Schema(description = "订单编号")
    // @NotBlank
    private String orderId;
}
