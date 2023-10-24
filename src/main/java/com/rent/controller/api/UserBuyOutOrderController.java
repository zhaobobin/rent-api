package com.rent.controller.api;

import com.rent.common.dto.CommonResponse;
import com.rent.common.dto.order.response.BuyOutOrderPageDto;
import com.rent.common.dto.order.response.BuyOutOrderPayResponse;
import com.rent.common.dto.order.resquest.BuyOutOrderPayRequest;
import com.rent.common.dto.order.resquest.UserOrderBuyOutReqDto;
import com.rent.service.order.UserOrderBuyOutService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author udo
 */
@RestController
@Tag(name = "小程序用户买断订单模块")
@RequestMapping("/zyj-api-web/hzsx/api/buyOutOrder")
@RequiredArgsConstructor
public class UserBuyOutOrderController {

    private final UserOrderBuyOutService userOrderBuyOutService;

    @Operation(summary = "用户买断模块-买断页面支付页面需要的数据")
    @PostMapping("/buyOutPage")
    public CommonResponse<BuyOutOrderPageDto> buyOutPage(@RequestBody UserOrderBuyOutReqDto request) {
        BuyOutOrderPageDto dto = userOrderBuyOutService.buyOutPage(request.getOrderId());
        return CommonResponse.<BuyOutOrderPageDto>builder().data(dto).build();
    }

    @Operation(summary = "用户买断模块-买断页面支付页面需要的数据")
    @PostMapping("/buyOutTrial")
    public CommonResponse<BuyOutOrderPageDto> buyOutTrial(@RequestBody UserOrderBuyOutReqDto request) {
        BuyOutOrderPageDto dto = userOrderBuyOutService.buyOutTrial(request);
        return CommonResponse.<BuyOutOrderPageDto>builder().data(dto).build();
    }

    @Operation(summary = "用户买断模块-买断创建订单并支付")
    @PostMapping("/liteBuyOutOrderPay")
    public CommonResponse<BuyOutOrderPayResponse> liteBuyOutOrderPay(@RequestBody BuyOutOrderPayRequest request) {
        BuyOutOrderPayResponse response = userOrderBuyOutService.liteBuyOutOrderPay(request.getOrderId(),request.getCouponId());
        return CommonResponse.<BuyOutOrderPayResponse>builder().data(response).build();
    }

}


