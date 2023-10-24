package com.rent.controller.backstage;

import com.rent.common.dto.CommonResponse;
import com.rent.common.dto.user.ProvinceDto;
import com.rent.config.annotation.ExcludeWebLog;
import com.rent.service.user.DistrictService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * 区域表控制器
 *
 * @author zhao
 * @Date 2020-07-06 15:29
 */
@RestController
@RequestMapping("/zyj-backstage-web/hzsx/district")
@RequiredArgsConstructor
public class DistrictController {

    private final DistrictService districtService;

    @ExcludeWebLog
    @Operation(summary = "获取地区名称信息")
    @GetMapping("/selectDistrict")
    public CommonResponse<List<ProvinceDto>> selectDistrict() {
        return CommonResponse.<List<ProvinceDto>>builder().data(districtService.selectDistrict()).build();
    }




}
