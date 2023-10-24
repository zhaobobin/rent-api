package com.rent.common.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 城市返回数据
 */
@Data
@Schema(description = "城市返回数据")
public class CityDto implements Serializable {
    @Schema(description = "城市code")
    private String value;
    @Schema(description = "城市名称")
    private String name;
    @Schema(description = "地区集合")
    private List<AreaDto> subList;


}
