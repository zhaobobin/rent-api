package com.rent.common.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 省份返回数据
 */
@Data
@Schema(description = "省份返回数据")
public class ProvinceDto implements Serializable {
    @Schema(description = "省份code")
    private String value;
    @Schema(description = "省份名称")
    private String name;
    @Schema(description = "城市集合")
    private List<CityDto> subList;


}
