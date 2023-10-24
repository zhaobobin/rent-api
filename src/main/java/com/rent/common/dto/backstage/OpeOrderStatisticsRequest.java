package com.rent.common.dto.backstage;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author xiaoyao
 * @version V1.0
 * @date 2020-8-11 下午 3:01:11
 * @since 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "订单统计请求类")
public class OpeOrderStatisticsRequest implements Serializable {

    private static final long serialVersionUID = 8522329193714189041L;

    // @NotNull
    @Schema(description = "订单统计开始时间", required = true)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date orderStatisticsStartDate;
    // @NotNull
    @Schema(description = "订单统计结束时间", required = true)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date orderStatisticsEndDate;
    // @NotNull
    @Schema(description = "租金统计开始时间", required = true)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date rentStatisticsStartDate;
    // @NotNull
    @Schema(description = "租金统计结束时间", required = true)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date rentStatisticsEndDate;

}
