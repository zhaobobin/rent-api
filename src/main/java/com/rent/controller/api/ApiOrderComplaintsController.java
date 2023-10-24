package com.rent.controller.api;

import com.rent.common.dto.CommonResponse;
import com.rent.common.dto.api.request.SubmitOrderComplaintReqVo;
import com.rent.common.dto.api.resp.OrderComplaintsResponse;
import com.rent.common.dto.common.resp.GetOrderComplaintTypeRespVo;
import com.rent.service.marketing.OrderComplaintsService;
import com.rent.util.AppParamUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Tag(name = "小程序订单投诉")
@RequestMapping("/zyj-api-web/hzsx/aliPay/orderComplaints")
@RequiredArgsConstructor
public class ApiOrderComplaintsController {

    private final OrderComplaintsService orderComplaintsService;

    @Operation(summary = "获取订单投诉类型集合")
    @GetMapping("/getOrderComplaintsTypes")
    public CommonResponse<List<GetOrderComplaintTypeRespVo>> getOrderComplaintsTypes() {
        List<GetOrderComplaintTypeRespVo> list =  orderComplaintsService.getOrderComplaintsTypes();
        return CommonResponse.<List<GetOrderComplaintTypeRespVo>>builder().data(list).build();
    }

    @Operation(summary = "用户提交订单投诉")
    @PostMapping("/addOrderComplaints")
    public CommonResponse<Boolean> addOrderComplaints(@RequestBody @Valid SubmitOrderComplaintReqVo request) {
        orderComplaintsService.addOrderComplaints(request);
        return CommonResponse.<Boolean>builder().data(Boolean.TRUE).build();
    }

    @Operation(summary = "获取用户可投诉的订单信息")
    @GetMapping("/getOrderAndShopName")
    public CommonResponse<List<OrderComplaintsResponse>> getOrderAndShopName(@RequestParam("uid") @Parameter(name = "uid",description = "用户ID",required = true)String uid) {
        String channelId = AppParamUtil.getChannelId();
        List<OrderComplaintsResponse> list = orderComplaintsService.getOrderAndShopName(uid,channelId);
        return CommonResponse.<List<OrderComplaintsResponse>>builder().data(list).build();
    }

}
