package com.rent.controller.backstage;

import com.rent.common.dto.CommonResponse;
import com.rent.config.outside.OutsideConfig;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Tag(name = "通用信息模块")
@RestController
@RequestMapping("/zyj-backstage-web/hzsx/common")
@RequiredArgsConstructor
public class CommonController {
    @Operation(summary = "获取平台信息")
    @GetMapping(value = "/appinfo")
    public CommonResponse<Map> appinfo() {
        Map<String, Object> map = new HashMap<>();
        map.put("appName", OutsideConfig.APP_NAME);
        map.put("appCode", OutsideConfig.APP_CODE);
        map.put("appId", OutsideConfig.APPID);
        map.put("appLogoUrl", OutsideConfig.APP_LOGO_URL);
        map.put("alipaySearchPng", OutsideConfig.ALIPAY_SEARCH_PNG);

        return CommonResponse.<Map>builder().data(map).build();
    }
}
