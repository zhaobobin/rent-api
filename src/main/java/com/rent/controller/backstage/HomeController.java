package com.rent.controller.backstage;

import com.rent.common.dto.CommonResponse;
import com.rent.common.dto.product.ProductCountsDto;
import com.rent.service.product.ProductStatisticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: hzsx-rent-parent
 * @description: 首页数据统计接口
 * @author: yr
 * @create: 2020-08-05 16:54
 **/
@Slf4j
@RestController
@Tag(name = "运营首页数据统计接口接口", description = "首页首页数据统计接口")
@RequestMapping("/zyj-backstage-web/hzsx/home")
@RequiredArgsConstructor
public class HomeController {

    private final ProductStatisticsService productStatisticsService;

    @Operation(summary = "首页商品数据统计")
    @GetMapping("/selectProductCounts")
    public CommonResponse<ProductCountsDto> selectProductCounts() {
        ProductCountsDto dto = productStatisticsService.selectProductCounts();
        return CommonResponse.<ProductCountsDto>builder().data(dto).build();
    }



}
