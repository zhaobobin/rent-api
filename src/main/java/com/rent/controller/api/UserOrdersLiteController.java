package com.rent.controller.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.dto.CommonResponse;
import com.rent.common.dto.api.LiteListReq;
import com.rent.common.dto.order.OrderPricesDto;
import com.rent.common.dto.order.UserOrderListDto;
import com.rent.common.dto.api.resp.LiteConfirmOrderResp;
import com.rent.common.dto.api.resp.OrderSubmitResponse;
import com.rent.common.dto.api.request.LiteConfirmOrderReq;
import com.rent.common.dto.order.resquest.OrderListQueryRequest;
import com.rent.common.dto.api.request.TrailLiteRequest;
import com.rent.common.dto.api.request.UserOrderSubmitReq;
import com.rent.common.enums.order.EnumOrderStatus;
import com.rent.service.order.UserOrdersLiteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaowenchao
 */
@Tag(name = "小程序用户下单相关接口")
@RestController
@RequestMapping("/zyj-api-web/hzsx/liteUserOrders")
@RequiredArgsConstructor
public class UserOrdersLiteController {

    private final UserOrdersLiteService userOrderLiteService;

    @Operation(summary = "确认订单")
    @PostMapping("/confirm")
    public CommonResponse<LiteConfirmOrderResp> confirm(@RequestBody @Valid LiteConfirmOrderReq request) {
        LiteConfirmOrderResp confirm = userOrderLiteService.confirm(request);
        return CommonResponse.<LiteConfirmOrderResp>builder().data(confirm).build();
    }

    @Operation(summary = "账单试算")
    @PostMapping("/trail")
    public CommonResponse<OrderPricesDto> trail(@RequestBody @Valid TrailLiteRequest request) {
        OrderPricesDto trail = userOrderLiteService.trail(request);
        return CommonResponse.<OrderPricesDto>builder().data(trail).build();
    }

    @Operation(summary = "提交订单")
    @PostMapping("/submit")
    public CommonResponse<OrderSubmitResponse> submit(@RequestBody @Valid UserOrderSubmitReq request) {
        OrderSubmitResponse submit = userOrderLiteService.submit(request);
        return CommonResponse.<OrderSubmitResponse>builder().data(submit).build();
    }

    @Operation(summary = "订单列表")
    @PostMapping("/userOrderList")
    public CommonResponse<Page<UserOrderListDto>> userOrderList(@RequestBody @Valid LiteListReq req) {
        OrderListQueryRequest request = new OrderListQueryRequest();
        List<String> statusList = new ArrayList<>();
        request.setOverDueQueryFlag(Boolean.FALSE);
        if(StringUtils.isNotEmpty(req.getStatus())){
            switch (req.getStatus()){
                case "WAITING_PAYMENT":
                    statusList.add(EnumOrderStatus.WAITING_PAYMENT.getCode());
                    break;
                case "PENDING_DEAL":
                    statusList.add(EnumOrderStatus.TO_AUDIT.getCode());
                    statusList.add(EnumOrderStatus.PENDING_DEAL.getCode());
                    break;
                case "WAITING_USER_RECEIVE_CONFIRM":
                    statusList.add(EnumOrderStatus.WAITING_USER_RECEIVE_CONFIRM.getCode());
                    break;
                case "RENTING":
                    statusList.add(EnumOrderStatus.RENTING.getCode());
                    statusList.add(EnumOrderStatus.TO_GIVE_BACK.getCode());
                    break;
                case "WAITING_SETTLEMENT":
                    statusList.add(EnumOrderStatus.WAITING_SETTLEMENT.getCode());
                    statusList.add(EnumOrderStatus.WAITING_SETTLEMENT_PAYMENT.getCode());
                    break;
                case "OVER_DUE":
                    request.setOverDueQueryFlag(Boolean.TRUE);
                    break;
                case "CLOSED":
                    statusList.add(EnumOrderStatus.CLOSED.getCode());
                    break;
                case "FINISH":
                    statusList.add(EnumOrderStatus.FINISH.getCode());
                    break;
            }
        }
        request.setStatusList(statusList);
        request.setUid(req.getUid());
        request.setPageNumber(req.getPageNumber());
        request.setPageSize(req.getPageSize());
        Page<UserOrderListDto> userOrderListDtoPage = userOrderLiteService.userOrderList(request);
        return CommonResponse.<Page<UserOrderListDto>>builder().data(userOrderListDtoPage).build();
    }


}
