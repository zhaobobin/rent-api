package com.rent.common.dto.order.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "订单查询地址归还响应")
public class OrderBackAddressResponse implements Serializable {

    private static final long serialVersionUID = -6386874466919212351L;

    @Schema(description = "id")
    private Integer id;

    @Schema(description = "手机号")
    private String telephone;

    @Schema(description = "姓名")
    private String name;

    @Schema(description = "街道地址")
    private String street;

    @Schema(description = "省id")
    private Integer provinceId;

    @Schema(description = "市id")
    private Integer cityId;

    @Schema(description = "区id")
    private Integer areaId;

    @Schema(description = "地区")
    private String areaStr;

    @Schema(description = "城市")
    private String cityStr;

    @Schema(description = "省份")
    private String provinceStr;
}
