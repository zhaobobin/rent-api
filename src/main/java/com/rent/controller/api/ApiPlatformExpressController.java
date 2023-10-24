package com.rent.controller.api;

import com.rent.common.dto.vo.ApiPlatformExpressVo;
import com.rent.service.product.PlatformExpressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
@Tag(name = "小程序物流公司")
@RequestMapping("/zyj-api-web/hzsx/aliPay/platformExpress")
public class ApiPlatformExpressController {

    private final PlatformExpressService platformExpressService;

    @Operation(summary = "获取物流公司列表的接口")
    @GetMapping("/selectExpressList")
    public List<ApiPlatformExpressVo> selectExpressList() {
        return platformExpressService.selectExpressListForApi();
    }

}
