package com.rent.controller.api;

import com.rent.common.dto.CommonResponse;
import com.rent.common.dto.order.response.UserPaySettlementResponse;
import com.rent.common.dto.order.resquest.UserModifySettlementReqDto;
import com.rent.common.dto.order.resquest.UserPaySettlementReqDto;
import com.rent.service.order.OrderSettlementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Tag(name = "小程序订单结算模块", description = "订单结算模块")
@RequestMapping("/zyj-api-web/hzsx/api/order/settlement")
@RequiredArgsConstructor
public class UserOrderSettlementController {

    private final OrderSettlementService orderSettlementService;

    @Operation(summary = "用户申请修改结算单")
    @PostMapping("/userModifySettlementApply")
    public CommonResponse<Void> userModifySettlementApply(@RequestBody UserModifySettlementReqDto request) {
        orderSettlementService.userModifySettlementApply(request.getOrderId());
        return CommonResponse.<Void>builder().build();
    }

    @Operation(summary = "支付结算单")
    @PostMapping("/liteUserPaySettlement")
    public CommonResponse<UserPaySettlementResponse> liteUserPaySettlement(@RequestBody UserPaySettlementReqDto request) {
        UserPaySettlementResponse response = orderSettlementService.liteUserPaySettlement(request);
        return CommonResponse.<UserPaySettlementResponse>builder().data(response).build();
    }

}
