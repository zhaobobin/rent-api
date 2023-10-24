package com.rent.controller.backstage;


import com.rent.common.dto.CommonResponse;
import com.rent.common.dto.product.PlatformExpressDto;
import com.rent.service.product.PlatformExpressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * @program: hzsx-rent-parent
 * @description:
 * @author: yr
 * @create: 2020-07-27 10:49
 **/
@Slf4j
@RestController
@Tag(name = "物流信息查询")
@RequestMapping("/zyj-backstage-web/hzsx/platformExpress")
@RequiredArgsConstructor
public class PlatformExpressController {

    private final PlatformExpressService platformExpressService;

    @Operation(summary = "获取物流公司列表的接口")
    @GetMapping("/selectExpressList")
    public CommonResponse<List<PlatformExpressDto>> selectExpressList() {
        return CommonResponse.<List<PlatformExpressDto>>builder().data(platformExpressService.selectExpressList()).build();
    }

}
