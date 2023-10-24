package com.rent.controller.api;

import com.rent.common.dto.CommonResponse;
import com.rent.common.dto.order.OrderByStagesDto;
import com.rent.common.dto.order.response.OrderByStagesPayResponse;
import com.rent.common.dto.order.resquest.OrderByStagesPayRequest;
import com.rent.service.order.OrderByStagesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
@Tag(name = "小程序分期订单模块")
@RequestMapping("/zyj-api-web/hzsx/api/orderByStages")
public class UserOrderByStagesController {

    private final OrderByStagesService orderByStagesService;

    @Operation(summary = "查询账单信息")
    @GetMapping("/queryOrderByStagesByOrderId")
    public CommonResponse<List<OrderByStagesDto>> queryOrderByStagesByOrderId(@Parameter(description = "orderId",required = true)String orderId) {
        List<OrderByStagesDto> list = orderByStagesService.queryOrderByStagesByOrderId(orderId);
        return CommonResponse.<List<OrderByStagesDto>>builder().data(list).build();
    }

    @Operation(summary = "分期订单支付")
    @PostMapping("/liteOrderByStagesPay")
    public CommonResponse<OrderByStagesPayResponse> liteOrderByStagesPay(@RequestBody OrderByStagesPayRequest request) {
        OrderByStagesPayResponse response = orderByStagesService.liteOrderByStagesPay(request);
        return CommonResponse.<OrderByStagesPayResponse>builder().data(response).build();
    }

}
