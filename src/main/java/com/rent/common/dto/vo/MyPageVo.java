package com.rent.common.dto.vo;

import com.rent.common.dto.marketing.PageElementConfigDto;
import com.rent.common.dto.product.RentWellProductDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Schema(description = "我的页面数据")
@Data
public class MyPageVo {

    @Schema(description = "我的服务")
    private List<PageElementConfigDto> services;

    @Schema(description = "我的订单")
    private List<PageElementConfigDto> orders;

    @Schema(description = "更多热销商品")
    private List<RentWellProductDto> products;
}
