package com.rent.controller.backstage;

import com.rent.common.dto.CommonResponse;
import com.rent.service.order.OrderStageService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author zhaowenchao
 */
@RestController
@RequestMapping("/zyj-backstage-web/hzsx/order/stage")
@Slf4j
@RequiredArgsConstructor
public class OrderStageController {

    private final OrderStageService orderStageService;

    @Operation(summary = "押金抵扣")
    @GetMapping("/depositMortgage")
    public CommonResponse<Boolean> depositMortgage(@RequestParam("id") Long id) {
        return CommonResponse.<Boolean>builder().data(orderStageService.depositMortgage(id)).build();
    }

}
