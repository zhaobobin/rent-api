package com.rent.controller.api;


import com.rent.common.dto.CommonResponse;
import com.rent.common.dto.order.UserOrderReletSubmitRequest;
import com.rent.common.dto.order.response.OrderReletConfirmResponse;
import com.rent.common.dto.order.response.OrderReletSubmitResponse;
import com.rent.common.dto.order.response.UserOrderReletPageResponse;
import com.rent.common.dto.order.resquest.UserOrderReletConfirmRequest;
import com.rent.common.dto.order.resquest.UserOrderReletPageRequest;
import com.rent.service.order.UserOrderReletService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Tag(name = "小程序续租订单模块", description = "续租订单模块")
@RequestMapping("/zyj-api-web/hzsx/api/orderRelet")
public class UserOrderReletController {

    private UserOrderReletService userOrderReletService;

    public UserOrderReletController(UserOrderReletService userOrderReletService) {
        this.userOrderReletService = userOrderReletService;
    }

    /**
     * 续租展示页面查询
     *
     * @param request 条件
     * @return boolean
     */
    @PostMapping("/userOrderReletPage")
    @Operation(summary = "续租展示页面查询")
    public CommonResponse<UserOrderReletPageResponse> userOrderReletPage(@RequestBody UserOrderReletPageRequest request) {
        UserOrderReletPageResponse response  = userOrderReletService.userOrderReletPage(request.getOrderId());
        return CommonResponse.<UserOrderReletPageResponse>builder().data(response).build();
    }

    /**
     * 确认续租订单
     *
     * @param request 条件
     * @return boolean
     */
    @PostMapping("/userOrderReletConfirm")
    @Operation(summary = "续租确认")
    public CommonResponse<OrderReletConfirmResponse> userOrderReletConfirm(@RequestBody UserOrderReletConfirmRequest request) {
        OrderReletConfirmResponse response = userOrderReletService.userOrderReletConfirm(request.getOriginalOrderId(),request.getDuration(),request.getSkuId(),request.getUid(),request.getPrice(),request.getAdditionalServicesIds());
        return CommonResponse.<OrderReletConfirmResponse>builder().data(response).build();
    }

    /**
     * 确认续租订单
     *
     * @param request 条件
     * @return boolean
     */
    @Operation(summary = "续租确认提交")
    @PostMapping("/liteUserOrderReletSubmit")
    public CommonResponse<OrderReletSubmitResponse> liteUserOrderReletSubmit(@RequestBody UserOrderReletSubmitRequest request) {
        OrderReletSubmitResponse response = userOrderReletService.liteUserOrderReletSubmit(request);
        return CommonResponse.<OrderReletSubmitResponse>builder().data(response).build();
    }

}
