package com.rent.controller.api;


import com.rent.common.dto.CommonResponse;
import com.rent.common.dto.order.response.OrderBackAddressResponse;
import com.rent.common.dto.order.resquest.OrderGiveBackAddressRequest;
import com.rent.common.dto.order.resquest.OrderGiveBackRequest;
import com.rent.service.order.UserOrdersQueryService;
import com.rent.service.order.UserOrdersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Tag(name = "小程序订单归还模块")
@RequestMapping("/zyj-api-web/hzsx/api/order/giveBack")
public class UserGiveBackOrdersController {

    private final UserOrdersQueryService userOrdersQueryService;
    private final UserOrdersService userOrdersService;

    @Operation(summary = "订单归还地址")
    @PostMapping("/queryOrderGiveBackAddress")
    public CommonResponse<List<OrderBackAddressResponse>> queryOrderGiveBackAddress(@RequestBody OrderGiveBackAddressRequest request) {
        List<OrderBackAddressResponse> list =  userOrdersQueryService.queryOrderGiveBackAddress(request.getOrderId());
        return CommonResponse.<List<OrderBackAddressResponse>>builder().data(list).build();
    }

    @Operation(summary = "订单归还提交")
    @PostMapping("/userOrderBackSubmitConfirm")
    public CommonResponse<Void> userOrderBackSubmitConfirm(@RequestBody OrderGiveBackRequest request) {
        userOrdersService.userOrderBackSubmitConfirm(request);
        return CommonResponse.<Void>builder().build();
    }

}
