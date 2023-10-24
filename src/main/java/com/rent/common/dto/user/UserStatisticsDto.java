package com.rent.common.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author zhaowenchao
 */
@Data
@Schema(description = "首页用户统计数据")
public class UserStatisticsDto {

    @Schema(description = "今日新增")
    private Integer todayIncrease;

    @Schema(description = "昨日新增")
    private Integer yesterdayIncrease;

    @Schema(description = "本月新增")
    private Integer monthIncrease;
}
