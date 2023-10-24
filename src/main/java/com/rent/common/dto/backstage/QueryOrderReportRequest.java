package com.rent.common.dto.backstage;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author xiaoyao
 * @version V1.0
 * @date 2020-8-18 上午 10:03:57
 * @since 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "订单统计查询请求类")
public class QueryOrderReportRequest implements Serializable {

    private static final long serialVersionUID = -6471612727340771757L;

    @NotNull
    @Schema(description = "订单统计开始时间", required = true)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date orderStatisticsStartDate;
    @NotNull
    @Schema(description = "订单统计结束时间", required = true)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date orderStatisticsEndDate;
}
