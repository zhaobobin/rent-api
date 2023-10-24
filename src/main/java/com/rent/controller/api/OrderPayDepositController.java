package com.rent.controller.api;


import com.rent.common.dto.CommonResponse;
import com.rent.common.dto.order.response.OrderByStagesPayResponse;
import com.rent.service.product.OrderPayDepositService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhaowenchao
 */
@RestController
@RequestMapping("/zyj-api-web/hzsx/orderPayDeposit")
@Tag(name = "小程序我的页面的押金")
@RequiredArgsConstructor
public class OrderPayDepositController {

    private final OrderPayDepositService orderPayDepositService;

    @Operation(summary = "押金拉起支付收银台")
    @GetMapping("/getPayInfo")
    public CommonResponse<OrderByStagesPayResponse> getPayInfo(@RequestParam("orderId") String orderId) {
        OrderByStagesPayResponse response = orderPayDepositService.orderDepositPay(orderId);
        return CommonResponse.<OrderByStagesPayResponse>builder().data(response).build();
    }

    @Operation(summary = "押金提现")
    @GetMapping("/withdraw")
    public CommonResponse<Boolean> withdraw(@RequestParam("uid") String uid,
                                            @RequestParam(value = "orderId", required = false) String orderId) {
        orderPayDepositService.refund(uid, orderId);
        return CommonResponse.<Boolean>builder().data(Boolean.TRUE).build();
    }


}


