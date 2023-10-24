package com.rent.controller.api;

import com.rent.common.dto.CommonResponse;
import com.rent.common.dto.api.OrderFreezeAgainReqDto;
import com.rent.common.dto.components.dto.OrderContractDto;
import com.rent.common.dto.components.dto.UserOrderAgreementRequest;
import com.rent.common.dto.components.response.ExpressInfoResponse;
import com.rent.common.dto.components.response.FaceInitResponse;
import com.rent.common.dto.order.OrderDetailResponse;
import com.rent.common.dto.order.UserOrderPaySignDto;
import com.rent.common.dto.order.UserOrdersDto;
import com.rent.common.dto.order.UserOrdersReqDto;
import com.rent.common.dto.order.response.DepositOrderPageDto;
import com.rent.common.dto.order.response.DepositOrderRecordDto;
import com.rent.common.dto.order.response.OrderCloseResponse;
import com.rent.common.dto.order.response.OrderFreezeAgainResponse;
import com.rent.common.dto.order.resquest.*;
import com.rent.common.enums.order.EnumOrderCloseType;
import com.rent.common.util.DesensitizationUtil;
import com.rent.dao.order.OrderAddressDao;
import com.rent.exception.HzsxBizException;
import com.rent.model.order.OrderAddress;
import com.rent.model.product.PlatformExpress;
import com.rent.service.components.SxService;
import com.rent.service.order.*;
import com.rent.service.product.PlatformExpressService;
import com.rent.util.StringUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Tag(name = "小程序订单模块", description = "订单模块")
@RequestMapping("/zyj-api-web/hzsx/api/order")
@RequiredArgsConstructor
public class UserOrdersController {

    private final UserOrdersQueryService userOrdersQueryService;
    private final UserOrdersService userOrdersService;
    private final PlatformExpressService platformExpressService;
    private final OrderAddressDao orderAddressDao;
    private final UserOrderCertificationService userOrderCertificationService;
    private final OrderContractService orderContractService;
    private final SxService sxService;
    private final OrderReceiptCore orderReceiptCore;
    private final OrderAddressService orderAddressService;

    @Operation(summary = "用户订单详细")
    @PostMapping("/selectUserOrderDetail")
    public CommonResponse<OrderDetailResponse> selectUserOrderDetail(@RequestBody OrderDetailQueryRequest request) {
        OrderDetailResponse response = userOrdersQueryService.selectUserOrderDetail(request.getOrderId());
        String telephone = response.getOrderAddressDto().getTelephone();
        response.getOrderAddressDto().setTelephone(DesensitizationUtil.mobileEncrypt(telephone));
        String realName = response.getOrderAddressDto().getRealname();
        response.getOrderAddressDto().setRealname(DesensitizationUtil.nameEncrypt(realName));
        return CommonResponse.<OrderDetailResponse>builder().data(response).build();
    }

    @Operation(summary = "未支付用户取消订单")
    @PostMapping("/userCancelOrder")
    public CommonResponse<OrderCloseResponse> userCancelOrder(@RequestBody OrderCloseReqDto orderCloseReqDto) {
        orderCloseReqDto.setCloseType(EnumOrderCloseType.UN_PAY_USER_APPLY);
        OrderCloseResponse response = userOrdersService.closeOrder(orderCloseReqDto.getOrderId(), orderCloseReqDto.getCloseType(), orderCloseReqDto.getCancelReason());
        return CommonResponse.<OrderCloseResponse>builder().data(response).build();
    }

    @Operation(summary = "已支付用户取消订单")
    @PostMapping("/payedCloseOrder")
    public CommonResponse<Void> payedCloseOrder(@RequestBody OrderCloseReqDto orderCloseReqDto) {
        throw new HzsxBizException("-1", "当前订单状态请联系客服取消");
    }

    @Operation(summary = "用户确认收货")
    @PostMapping("/userConfirmReceipt")
    public CommonResponse<Void> userConfirmReceipt(@RequestBody OrderConfirmReceiptReqDto orderConfirmReceiptReqDto) {
        this.orderReceiptCore.userConfirm(orderConfirmReceiptReqDto.getOrderId());
        return CommonResponse.<Void>builder().build();
    }

    @Operation(summary = "用户确认订单页面-协议内容")
    @PostMapping("/userOrderAgreementV2")
    public CommonResponse<OrderContractDto> agreementV2(@RequestBody UserOrderAgreementRequest request) {

        if (StringUtil.isNotEmpty(request.getOrderId())) {
            return CommonResponse.<OrderContractDto>builder().data(orderContractService.getOrderContractInfo(request.getOrderId())).build();
        } else {
            return CommonResponse.<OrderContractDto>builder().data(userOrdersQueryService.agreementV2(request))
                    .build();
        }
    }

    @Operation(summary = "用户重新发起授权支付")
    @PostMapping("/userFreezeAgain")
    public CommonResponse<OrderFreezeAgainResponse> userFreezeAgain(
            @RequestBody OrderFreezeAgainReqDto orderFreezeAgainReqDto) {
        OrderFreezeAgainResponse orderFreezeAgainResponse = userOrdersService.liteOrderFreezeAgain(
                orderFreezeAgainReqDto.getOrderId(), orderFreezeAgainReqDto.getTemplateId());
        return CommonResponse.<OrderFreezeAgainResponse>builder().data(orderFreezeAgainResponse)
                .build();
    }


    @Operation(summary = "修改订单地址")
    @PostMapping("/userOrderAddressModify")
    public CommonResponse<Void> userOrderAddressModify(@RequestBody UserOrderAddressModifyRequest request) {
        orderAddressService.orderAddressModify(request);
        return CommonResponse.<Void>builder().build();
    }

    @Operation(summary = "查询用户物流单号")
    @GetMapping("/userGetExpressByOrderId")
    public CommonResponse<ExpressInfoResponse> userGetExpressByOrderId(@RequestParam("orderId") String orderId) {
        UserOrdersReqDto userOrdersReqDto = new UserOrdersReqDto();
        userOrdersReqDto.setOrderId(orderId);
        UserOrdersDto userOrdersDto = userOrdersService.queryUserOrdersDetail(userOrdersReqDto);
        PlatformExpress platformExpress = platformExpressService.queryPlatformExpressDetailById(userOrdersDto.getExpressId());
        OrderAddress orderAddress = orderAddressDao.queryByOrderId(orderId);
        String telephone = orderAddress.getTelephone();
        ExpressInfoResponse response = sxService.getExpressList(platformExpress.getShortName(), userOrdersDto.getExpressNo(), telephone);
        return CommonResponse.<ExpressInfoResponse>builder().data(response).build();
    }

    /**
     * 返回刷脸认证url
     *
     * @param request 订单id
     * @return
     */
    @Operation(summary = "订单用户人脸认证初始化")
    @PostMapping("/getFaceAuthCertifyUrl")
    public CommonResponse<FaceInitResponse> getFaceAuthCertifyUrl(@RequestBody FaceAuthCertifyRequest request) {
        FaceInitResponse response = userOrderCertificationService.getAilFaceAuthCertifyUrl(request.getOrderId());
        return CommonResponse.<FaceInitResponse>builder().data(response).build();
    }

    /**
     * 查询押金订单列表
     */
    @GetMapping("/depositOrderList")
    public CommonResponse<DepositOrderPageDto> depositOrderList(@RequestParam("uid") String uid) {
        DepositOrderPageDto dto = userOrdersQueryService.depositOrderList(uid);
        return CommonResponse.<DepositOrderPageDto>builder().data(dto).build();
    }

    /**
     * 查询押金订单记录
     */
    @GetMapping("/queryDepositOrderList")
    public CommonResponse<DepositOrderRecordDto> queryDepositOrderList(@RequestParam("uid") String uid) {
        DepositOrderRecordDto dto = userOrdersQueryService.queryDepositOrderList(uid);
        return CommonResponse.<DepositOrderRecordDto>builder().data(dto).build();
    }

    @Operation(summary = "查询免押及签约周期扣款信息")
    @GetMapping("/queryOrderPaySignInfo")
    public CommonResponse<UserOrderPaySignDto> queryOrderPaySignInfo(@RequestParam("uid") String uid) {
        UserOrderPaySignDto dto = userOrdersQueryService.queryOrderPaySignInfo(uid);
        return CommonResponse.<UserOrderPaySignDto>builder().data(dto).build();
    }

}


