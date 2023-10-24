package com.rent.common.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 县级行政单位返回数据
 */
@Data
@Schema(description = "县级行政单位返回数据")
public class AreaDto implements Serializable {
    @Schema(description = "地区code")
    private String value;
    @Schema(description = "地区名称")
    private String name;

}
