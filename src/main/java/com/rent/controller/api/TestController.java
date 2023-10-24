package com.rent.controller.api;

import com.alipay.api.response.AlipayTradeCloseResponse;
import com.rent.common.dto.CommonResponse;
import com.rent.service.components.AliPayCapitalService;
import com.rent.service.components.TestService;
import com.rent.service.order.AccountPeriodService;
import com.rent.service.order.ChannelAccountPeriodService;
import com.rent.service.order.UserOrdersTaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/zyj-api-web/hzsx/api/test")
@Tag(name = "小程序系统配置")
@RequiredArgsConstructor
public class TestController {
    private final TestService testService;
    private final AliPayCapitalService aliPayCapitalService;
    private final UserOrdersTaskService userOrdersTaskService;
    private final AccountPeriodService accountPeriodService;
    private final ChannelAccountPeriodService channelAccountPeriodService;

    @Operation(summary = "获得配置项")
    @GetMapping("/test")
    public CommonResponse<String> getSysConfig() {
//        testService.test();
//        AlipayTradeCloseResponse response = aliPayCapitalService.alipayTradeClose(null, "2023091222001412761400734753");
//        accountPeriodService.generateAccountPeriod();
//        userOrdersTaskService.generateSplitBillTask();
//        accountPeriodService.generateAccountPeriod();
//        channelAccountPeriodService.generateAccountPeriod();
        return CommonResponse.<String>builder().data("").build();
    }
}
