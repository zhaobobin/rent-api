package com.rent.common.dto.api;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
public class ProductSearchDto implements Serializable {
    @Schema(description = "查询内容")
    private String content;
    @Schema(description = "最小周期")
    private Integer minRentCycleDays;
    @Schema(description = "新旧")
    private Integer oldNewDegreeStatus;
    @Schema(description = "pageNumber")
    private Integer pageNumber;
    @Schema(description = "pageSize")
    private Integer pageSize;
    @Schema(description = "店铺ID")
    private String shopId;
    @Schema(description = "用户uid-非必填")
    private String uid;
}
