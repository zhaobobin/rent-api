package com.rent.controller.api;


import com.rent.common.cache.api.OrderContractDataCache;
import com.rent.common.dto.CommonResponse;
import com.rent.common.dto.components.dto.OrderContractDto;
import com.rent.util.RedisUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Author <a href="mailto:boannpx@163.com">niupq</a>
 * Date: 2019/6/27
 * Desc:
 * Version:V1.0
 */
@RestController
@RequestMapping("/zyj-api-web/hzsx/api/sysConfig")
@Tag(name = "小程序系统配置")
public class SysConfigController {

    private final String redisConfigHead = "zuyouji:sysConfig:";

    /**
     * 系统配置项,默认放在redis中
     * 默认关单间隔 zuyouji:sysConfig:USER_CANCEL_ORDER:HOUR 默认 1 小时
     * zuyouji:sysConfig:CONSUMER_HOTLINE 渠道电话
     * User
     */
    @Operation(summary = "获得配置项")
    @GetMapping("/getSysConfigByKey")
    public CommonResponse<Map<String, Object>> getSysConfig(@RequestParam("configKey") String configKey) {
        Map<String, Object> dataMap = new HashMap<>(1);
        dataMap.put("sysConfigValue", RedisUtil.get(redisConfigHead + configKey));
        return CommonResponse.<Map<String, Object>>builder().data(dataMap).build();
    }

    @Operation(summary = "获得配置项")
    @PostMapping("/cacheConfirmData")
    public CommonResponse<String> cacheConfirmData(@RequestBody OrderContractDto orderContractDto) {
        String orderId = orderContractDto.getOrderId();
        OrderContractDataCache.setOrderContractDataCache(orderId,orderContractDto);
        return CommonResponse.<String>builder().data(orderId).build();
    }

}
