package com.rent.controller.backstage;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.rent.common.constant.RedisKey;
import com.rent.common.dto.CommonResponse;
import com.rent.common.dto.backstage.*;
import com.rent.common.dto.backstage.request.AddEmergencyContactReq;
import com.rent.common.dto.bo.LoginUserBo;
import com.rent.common.dto.components.request.DecisionRequest;
import com.rent.common.dto.components.response.ExpressInfoResponse;
import com.rent.common.dto.components.response.RiskReportResponse;
import com.rent.common.dto.order.*;
import com.rent.common.dto.order.response.BackstageBuyOutOrderDetailDto;
import com.rent.common.dto.order.resquest.*;
import com.rent.common.enums.components.EnumAliPayStatus;
import com.rent.common.enums.components.OrderCenterStatus;
import com.rent.common.enums.order.*;
import com.rent.common.enums.user.EnumBackstageUserPlatform;
import com.rent.common.util.AsyncUtil;
import com.rent.common.util.OSSFileUtils;
import com.rent.exception.HzsxBizException;
import com.rent.model.order.UserOrders;
import com.rent.service.components.DecisionReportService;
import com.rent.service.components.OrderCenterService;
import com.rent.service.components.SxService;
import com.rent.service.order.*;
import com.rent.service.product.OrderPayDepositService;
import com.rent.util.LoginUserUtil;
import com.rent.util.RedisUtil;
import com.rent.util.StringUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@Tag(name = "运营订单模块")
@RequestMapping("/zyj-backstage-web/hzsx/ope/order")
@RequiredArgsConstructor
public class OpeOrderController {

    private final OrderHastenService orderHastenService;
    private final OrderPayDepositService orderPayDepositService;
    private final OrderRemarkService orderRemarkService;
    private final OpeUserOrdersService opeUserOrdersService;
    private final OrderAuditService orderAuditService;
    private final UserOrdersStatusTransferService userOrdersStatusTransferService;
    private final UserOrdersService userOrdersService;
    private final DecisionReportService decisionReportService;
    private final OrderByStagesService orderByStagesService;
    private final TransferOrderRecordService transferOrderRecordService;
    private final OrderCenterService orderCenterService;
    private final SxService sxService;
    private final OrderReceiptCore orderReceiptCore;
    private final OrderAddressService orderAddressService;
    private final OrderReportService orderReportService;
    private final OSSFileUtils ossFileUtils;


    @Operation(summary = "订单统计")
    @PostMapping("/opeOrderStatistics")
    public CommonResponse<OpeOrderStatisticsDto> opeOrderStatistics(@RequestBody OpeOrderStatisticsRequest request) {
        OpeOrderStatisticsDto dto = opeUserOrdersService.opeOrderStatistics(request);
        return CommonResponse.<OpeOrderStatisticsDto>builder().data(dto).build();
    }

    @Operation(summary = "订单统计")
    @PostMapping("/queryOrderReport")
    CommonResponse<List<OrderReportDto>> queryOrderReport(@RequestBody QueryOrderReportRequest request) {
        return CommonResponse.<List<OrderReportDto>>builder().data(orderReportService.queryOrderReport(request))
                .build();
    }


    @Operation(summary = "查询所有订单")
    @PostMapping("/queryOpeOrderByCondition")
    public CommonResponse<Page<BackstageUserOrderDto>> queryOpeOrderByCondition(@RequestBody OpeOrderRequest request) {
        OrderByConditionRequest conditionRequest = new OrderByConditionRequest();
        BeanUtil.copyProperties(request, conditionRequest);
        return CommonResponse.<Page<BackstageUserOrderDto>>builder().data(opeUserOrdersService.queryOpeOrderByCondition(conditionRequest)).build();
    }

    @Operation(summary = "查询订单详细")
    @PostMapping("/queryOpeUserOrderDetail")
    public CommonResponse<BackstageUserOrderDetailDto> queryOpeUserOrderDetail(@RequestBody QueryUserOrderDetailRequest request) {
        return CommonResponse.<BackstageUserOrderDetailDto>builder().data(opeUserOrdersService.queryOpeUserOrderDetail(request.getOrderId())).build();
    }

    @Operation(summary = "查询续租订单")
    @PostMapping("/queryReletOrderByCondition")
    public CommonResponse<Page<BackstageUserOrderDto>> queryReletOrderByCondition(@RequestBody OpeOrderRequest request) {
        request.setOrderType(EnumOrderType.RELET_ORDER);
        OrderByConditionRequest conditionRequest = new OrderByConditionRequest();
        BeanUtil.copyProperties(request, conditionRequest);
        Page<BackstageUserOrderDto> resp = opeUserOrdersService.queryOpeOrderByCondition(conditionRequest);
        return CommonResponse.<Page<BackstageUserOrderDto>>builder().data(resp).build();
    }

    @Operation(summary = "查询续租订单详情")
    @PostMapping("/queryUserReletOrderDetail")
    public CommonResponse<BackstageUserOrderDetailDto> queryUserReletOrderDetail(@RequestBody QueryUserOrderDetailRequest request) {
        BackstageUserOrderDetailDto dto = opeUserOrdersService.queryOpeUserOrderDetail(request.getOrderId());
        return CommonResponse.<BackstageUserOrderDetailDto>builder().data(dto).build();
    }

    @Operation(summary = "根据orderId判断是否有风控报告")
    @GetMapping("/queryWhetherRiskReport")
    public CommonResponse<Boolean> queryWhetherRiskReport(@RequestParam String orderId) {
        return CommonResponse.<Boolean>builder().data(decisionReportService.queryWhetherRiskReport(orderId)).build();
    }

    @Operation(summary = "查询风控报告")
    @PostMapping("/querySiriusReportByUid")
    public CommonResponse<RiskReportResponse> querySiriusReportByUid(@RequestBody DecisionRequest request) {
        return CommonResponse.<RiskReportResponse>builder().data(decisionReportService.getSiriusReportByOrderId(request)).build();
    }

    @Operation(summary = "查询待关闭订单列表")
    @PostMapping("/queryPendingOrderClosureList")
    public CommonResponse<Page<OpeUserOrderClosingDto>> queryPendingOrderClosureList(@RequestBody CloseOrderByConditionRequest request) {
        Page<OpeUserOrderClosingDto> response = opeUserOrdersService.queryPendingOrderClosureList(request);
        return CommonResponse.<Page<OpeUserOrderClosingDto>>builder().data(response).build();
    }

    @Operation(summary = "查询逾期订单列表")
    @PostMapping("/queryOpeOverDueOrdersByCondition")
    public CommonResponse<Page<BackstageUserOrderDto>> queryOpeOverDueOrdersByCondition(@RequestBody OrderByConditionRequest request) {
        request.setViolationStatusList(Arrays.asList(EnumViolationStatus.SETTLEMENT_OVERDUE, EnumViolationStatus.STAGE_OVERDUE));
        request.setOrderTypeList(Arrays.asList(EnumOrderType.GENERAL_ORDER, EnumOrderType.RELET_ORDER));
        Page<BackstageUserOrderDto> response = opeUserOrdersService.queryOpeOrderByCondition(request);
        return CommonResponse.<Page<BackstageUserOrderDto>>builder().data(response).build();
    }

    @Operation(summary = "查询运营买断订单")
    @PostMapping("/queryOpeBuyOutOrdersByCondition")
    public CommonResponse<Page<OpeBuyOutOrdersDto>> queryOpeBuyOutOrdersByCondition(@RequestBody QueryBuyOutOrdersRequest request) {
        Page<OpeBuyOutOrdersDto> response = opeUserOrdersService.queryOpeBuyOutOrderList(request);
        return CommonResponse.<Page<OpeBuyOutOrdersDto>>builder().data(response).build();
    }

    @Operation(summary = "查询运营买断订单详情")
    @PostMapping("/queryOpeBuyOutOrderDetail")
    public CommonResponse<BackstageBuyOutOrderDetailDto> queryOpeBuyOutOrderDetail(@RequestBody QueryBuyOutOrderDetailRequest request) {
        BackstageBuyOutOrderDetailDto dto = opeUserOrdersService.queryOpeBuyOutOrderDetail(request.getBuyOutOrderId());
        return CommonResponse.<BackstageBuyOutOrderDetailDto>builder().data(dto).build();
    }

    @Operation(summary = "查询电审订单")
    @PostMapping("/queryTelephoneAuditOrder")
    public CommonResponse<Page<BackstageUserOrderDto>> queryTelephoneAuditOrder(@RequestBody QueryTelephoneOrderAuditRequest request) {
        OrderByConditionRequest conditionRequest = new OrderByConditionRequest();
        BeanUtil.copyProperties(request, conditionRequest);
        if (null == request.getExamineStatus()) {
            conditionRequest.setStatus(EnumOrderStatus.TO_AUDIT.getCode());
            conditionRequest.setExamineStatusList(Arrays.asList(EnumOrderExamineStatus.PLATFORM_TO_EXAMINE, EnumOrderExamineStatus.PLATFORM_EXAMINED));
        } else {
            if (EnumOrderExamineStatus.PLATFORM_TO_EXAMINE.equals(request.getExamineStatus())) {
                conditionRequest.setStatus(EnumOrderStatus.TO_AUDIT.getCode());
            }
            conditionRequest.setExamineStatusList(Arrays.asList(request.getExamineStatus()));
        }
        Page<BackstageUserOrderDto> resp = opeUserOrdersService.queryOpeOrderByCondition(conditionRequest);
        return CommonResponse.<Page<BackstageUserOrderDto>>builder().data(resp).build();
    }

    @Operation(summary = "电审审核")
    @PostMapping("/telephoneAuditOrder")
    public CommonResponse<Void> telephoneAuditOrder(@RequestBody TelephoneOrderAuditRequest request) {
        request.setOperatorRole(EnumOrderOperatorRole.OPE);
        try {
            if (!RedisUtil.tryLock(RedisKey.ORDER_BACKSTAGE_OPERATOR_LOCK + request.getOrderId(), 7)) {
                throw new HzsxBizException("00000", "请不要连续点击!!!");
            }
            opeUserOrdersService.telephoneAuditOrder(request);
        } finally {
            RedisUtil.unLock(RedisKey.ORDER_BACKSTAGE_OPERATOR_LOCK + request.getOrderId());
        }
        return CommonResponse.<Void>builder().build();
    }

    @Operation(summary = "修改订单租金")
    @PostMapping("/orderRentChange")
    public CommonResponse<Boolean> updateOrderRent(@RequestBody OrderRentChangeReqDto request) {
        return CommonResponse.<Boolean>builder().data(opeUserOrdersService.orderRentChange(request)).build();
    }

    @Operation(summary = "订单租金修改历史")
    @PostMapping("/orderRentChangeList")
    public CommonResponse<Void> listOrderRentChangeList(@RequestParam String orderId) {
        return CommonResponse.<Void>builder().build();
    }

    @Operation(summary = "修改用户收货地址")
    @PostMapping("/opeOrderAddressModify")
    public CommonResponse<Void> opeOrderAddressModify(@RequestBody UserOrderAddressModifyRequest request) {
        LoginUserBo loginUser = LoginUserUtil.getLoginUser();
        if (StringUtil.isEmpty(loginUser.getShopId())) {
            throw new HzsxBizException("999999", "登录用户信息不完善,请联系运营人员");
        }
        orderAddressService.orderAddressModifyWithoutCheck(request);
        return CommonResponse.<Void>builder().build();
    }

    @Operation(summary = "关闭订单并退款")
    @PostMapping("/closeUserOrderAndRefundPrice")
    public CommonResponse<Void> closeUserOrderAndRefundPrice(@RequestBody BackstageCloseOrderRequest closeOrderRequest) {
        LoginUserBo loginUser = LoginUserUtil.getLoginUser();
        if (StringUtil.isEmpty(loginUser.getShopId())) {
            throw new HzsxBizException("999999", "登录用户信息不完善,请联系运营人员");
        }
        try {
            if (!RedisUtil.tryLock(RedisKey.ORDER_BACKSTAGE_OPERATOR_LOCK + closeOrderRequest.getOrderId(), 7)) {
                throw new HzsxBizException("00000", "请不要连续点击!!!");
            }
            OrderRemarkDto remarkDto = new OrderRemarkDto();
            remarkDto.setSource(EnumOrderRemarkSource.OPE);
            remarkDto.setOrderType(EnumOrderType.GENERAL_ORDER);
            remarkDto.setOrderId(closeOrderRequest.getOrderId());
            remarkDto.setUserName(loginUser.getName());
            remarkDto.setRemark("关闭订单-" + closeOrderRequest.getCloseReason());
            remarkDto.setCreateTime(new Date());
            orderRemarkService.addOrderRemark(remarkDto);
            userOrdersService.payedCloseOrder(
                    closeOrderRequest.getOrderId(),
                    EnumOrderCloseType.PLATFORM_CLOSE,
                    EnumOrderCloseType.PLATFORM_CLOSE.getDescription()
            );
            AsyncUtil.runAsync(() -> orderCenterService.merchantOrderSync(closeOrderRequest.getOrderId(), OrderCenterStatus.CLOSED));
        } finally {
            RedisUtil.unLock(RedisKey.ORDER_BACKSTAGE_OPERATOR_LOCK + closeOrderRequest.getOrderId());
        }
        return CommonResponse.<Void>builder().build();
    }

    @Operation(summary = "查询物流信息")
    @GetMapping("/queryExpressInfo")
    public CommonResponse<ExpressInfoResponse> queryExpressInfo(@RequestParam("expressNo") String expressNo,
                                                                @RequestParam("shortName") String shortName, @RequestParam("receiverPhone") String receiverPhone) {
        ExpressInfoResponse response = sxService.getExpressList(shortName, expressNo, receiverPhone);
        return CommonResponse.<ExpressInfoResponse>builder().data(response).build();
    }

    @Operation(summary = "添加备注")
    @PostMapping("orderRemark")
    public CommonResponse<Void> orderRemark(@RequestBody OrderRemarkRequest request) {
        LoginUserBo loginUser = LoginUserUtil.getLoginUser();
        if (null == loginUser) {
            throw new HzsxBizException("999999", "请登录后操作");
        }
        orderRemarkService.orderRemark(request.getOrderId(), request.getRemark(), loginUser.getName(), EnumOrderRemarkSource.OPE);
        return CommonResponse.<Void>builder().build();
    }

    @Operation(summary = "添加催收记录")
    @PostMapping("/orderHasten")
    public CommonResponse<Void> orderHasten(@RequestBody OrderHastenRequest request) {
        LoginUserBo loginUser = LoginUserUtil.getLoginUser();
        if (null == loginUser) {
            throw new HzsxBizException("999999", "请登录后操作");
        }
        request.setUserName(loginUser.getName());
        EnumOrderRemarkSource source = null;
        if (EnumBackstageUserPlatform.OPE.equals(loginUser.getType())) {
            source = EnumOrderRemarkSource.OPE;
        }
        if (EnumBackstageUserPlatform.SHOP.equals(loginUser.getType())) {
            source = EnumOrderRemarkSource.BUSINESS;
        }
        OrderHastenDto hastenDto = new OrderHastenDto();
        hastenDto.setSource(source);
        hastenDto.setOrderId(request.getOrderId());
        hastenDto.setUserName(request.getUserName());
        hastenDto.setResult(request.getResult());
        hastenDto.setNotes(request.getNotes());
        hastenDto.setCreateTime(new Date());
        orderHastenService.addOrderHasten(hastenDto);
        return CommonResponse.<Void>builder().build();
    }

    @Operation(summary = "查询催收记录")
    @PostMapping("/queryOrderHasten")
    public CommonResponse<Page<OrderHastenDto>> queryOrderHasten(@RequestBody OrderHastenReqDto request) {
        return CommonResponse.<Page<OrderHastenDto>>builder().data(orderHastenService.queryOrderHastenPage(request)).build();
    }


    @Operation(summary = "查询备注")
    @PostMapping("queryOrderRemark")
    public CommonResponse<Page<OrderRemarkDto>> queryOrderRemark(@RequestBody OrderRemarkReqDto request) {
        return CommonResponse.<Page<OrderRemarkDto>>builder().data(orderRemarkService.queryOrderRemarkPage(request)).build();
    }

    @Operation(summary = "运营中心查询审核记录")
    @PostMapping("/queryOrderAuditRecord")
    public CommonResponse<OrderAuditRecordDto> queryOrderAuditRecord(@RequestBody QueryOrderAuditRequest request) {
        return CommonResponse.<OrderAuditRecordDto>builder().data(orderAuditService.getByOrderId(request.getOrderId())).build();
    }


    @Operation(summary = "运营中心查询订单操作节点")
    @PostMapping("/queryOrderStatusTransfer")
    CommonResponse<List<UserOrdersStatusTransferDto>> queryOrderStatusTransfer(@RequestBody QueryOrderStatusTransferRequest request) {
        return CommonResponse.<List<UserOrdersStatusTransferDto>>builder().data(userOrdersStatusTransferService.queryRecordByOrderId(request.getOrderId())).build();
    }

    @Operation(summary = "查询订单账单相关信息")
    @PostMapping("/queryOrderStagesDetail")
    public CommonResponse<BackstageOrderStagesDto> queryOrderStagesDetail(@RequestBody OrderStagesQueryRequest request) {
        return CommonResponse.<BackstageOrderStagesDto>builder().data(opeUserOrdersService.queryOrderStagesDetail(request.getOrderId())).build();
    }

    @Operation(summary = "查询待归还订单订单")
    @PostMapping("/queryWaitingGiveBackOrder")
    public CommonResponse<Page<BackstageUserOrderDto>> queryWaitingGiveBackOrder(@RequestBody OpeOrderRequest request) {
        request.setUnrentDateEnd(new Date());
        request.setStatus(EnumOrderStatus.RENTING.getCode());
        request.setOrderTypeList(Arrays.asList(EnumOrderType.GENERAL_ORDER, EnumOrderType.RELET_ORDER));
        OrderByConditionRequest conditionRequest = new OrderByConditionRequest();
        BeanUtil.copyProperties(request, conditionRequest);
        Page<BackstageUserOrderDto> response = opeUserOrdersService.queryOpeOrderByCondition(conditionRequest);
        return CommonResponse.<Page<BackstageUserOrderDto>>builder().data(response).build();
    }

    @Operation(summary = "平台确认收货")
    @PostMapping("/forceConfirmReceipt")
    public CommonResponse<Void> forceConfirmReceipt(@RequestBody ForceConfirmReceiptRequest request) {
        LoginUserBo loginUser = LoginUserUtil.getLoginUser();
        if (null == loginUser) {
            throw new HzsxBizException("999999", "请登录后操作");
        }
        orderReceiptCore.confirmReceipt(request.getOrderId(), request.getConfirmDate(), loginUser.getName(), loginUser.getId().toString());
        return CommonResponse.<Void>builder().build();
    }

    @Operation(summary = "平台退押金")
    @GetMapping("/forceDepositRefund")
    public CommonResponse<Void> forceDepositRefund(@RequestParam("orderId") String orderId) {
        LoginUserBo loginUser = LoginUserUtil.getLoginUser();
        if (null == loginUser) {
            throw new HzsxBizException("999999", "请登录后操作");
        }
        orderPayDepositService.forceRefund(orderId, loginUser.getId());
        return CommonResponse.<Void>builder().build();
    }

    @Operation(summary = "代客支付")
    @GetMapping("/stagesValetPay")
    public CommonResponse<Void> stagesValetPay(@RequestParam("orderId") String orderId, @RequestParam("period") String period, @RequestParam("url") String url) {
        LoginUserBo loginUser = LoginUserUtil.getLoginUser();
        if (null == loginUser) {
            throw new HzsxBizException("999999", "请登录后操作");
        }
        String operator = loginUser.getId() + "-" + loginUser.getName();
        url = ossFileUtils.getObjectKey(url);
        orderByStagesService.payedOrderByStages(orderId, operator, url, Lists.newArrayList(period), null, EnumAliPayStatus.SUCCESS, PaymentMethod.VALET_PAYMENT);
        return CommonResponse.<Void>builder().build();
    }


    @Operation(summary = "查看代客支付凭证")
    @GetMapping("/selectValetPayment")
    public CommonResponse<OrderByStagesForValetDto> selectValetPayment(@RequestParam("orderId") String orderId, @RequestParam("currentPeriod") Integer currentPeriod) {
        OrderByStagesForValetDto dto = orderByStagesService.selectValetPayment(orderId, currentPeriod);
        return CommonResponse.<OrderByStagesForValetDto>builder().data(dto).build();
    }

    @Operation(summary = "修改押金金额")
    @PostMapping("/updatePayDepositAmount")
    public CommonResponse<Boolean> updatePayDepositAmount(@RequestBody UpdateDepositPayReq request) {
        if (BigDecimal.ZERO.compareTo(request.getAfterAmount()) > 0) {
            throw new HzsxBizException("999999", "请输入大于0的数字");
        }
        orderPayDepositService.updateAmount(request.getOrderId(), request.getAfterAmount(), LoginUserUtil.getLoginUser().getName(), LoginUserUtil.getLoginUser().getId());
        return CommonResponse.<Boolean>builder().data(Boolean.TRUE).build();
    }

    @Operation(summary = "查询押金信息以及修改日志")
    @GetMapping("/queryPayDepositLog")
    public CommonResponse<OpeDepositOrderPageDto> queryPayDepositLog(@RequestParam("orderId") String orderId) {
        return CommonResponse.<OpeDepositOrderPageDto>builder().data(orderPayDepositService.queryLog(orderId)).build();
    }

    @Operation(summary = "易付宝回调")
    @PostMapping("/suningStageOrderWithholdCallback")
    public CommonResponse<String> suningStageOrderWithholdCallback() {
//        StageOrderWithholdResponse response = opeUserOrdersService.stageOrderWithhold(request);
        return CommonResponse.<String>builder().data("").build();
    }

    @Operation(summary = "手动发起代扣")
    @PostMapping("/stageOrderWithhold")
    public CommonResponse<StageOrderWithholdResponse> stageOrderWithhold(@RequestBody StageOrderWithholdRequest request) {
        StageOrderWithholdResponse response = opeUserOrdersService.stageOrderWithhold(request);
        return CommonResponse.<StageOrderWithholdResponse>builder().data(response).build();
    }

    @Operation(summary = "运营配置转单")
    @PostMapping("/transferOrder")
    public CommonResponse<Boolean> transferOrder(@RequestBody TransferOrderRequest request) {
        LoginUserBo loginUser = LoginUserUtil.getLoginUser();
        if (null == loginUser) {
            throw new HzsxBizException("999999", "请登录后操作");
        }
        request.setOperator(loginUser.getName());
        request.setOperatorId(loginUser.getId().toString());
        Boolean result = transferOrderRecordService.transferOrder(request);
        return CommonResponse.<Boolean>builder().data(result).build();
    }

    @Operation(summary = "转单列表")
    @PostMapping("/queryTransferOrderRecordPage")
    public CommonResponse<Page<TransferOrderRecordDto>> queryTransferOrderRecordPage(@RequestBody TransferOrderRecordReqDto request) {
        Page<TransferOrderRecordDto> page = transferOrderRecordService.queryTransferOrderRecordPage(request);
        return CommonResponse.<Page<TransferOrderRecordDto>>builder().data(page).build();
    }

    @PostMapping("/queryChannelUserOrdersPage")
    public CommonResponse<Page<ChannelUserOrdersDto>> queryChannelUserOrdersPage(@RequestBody ChannelUserOrdersReqDto request) {
        if (!LoginUserUtil.getLoginUser().getShopId().equals("OPE")) {
            request.setMarketingChannelId(LoginUserUtil.getLoginUser().getShopId());
        }
        return CommonResponse.<Page<ChannelUserOrdersDto>>builder().data(userOrdersService.queryChannelUserOrdersPage(request))
                .build();
    }

    @PostMapping("/addEmergencyContact")
    public CommonResponse<Boolean> addEmergencyContact(@RequestBody AddEmergencyContactReq request) {
        return CommonResponse.<Boolean>builder().data(userOrdersService.addEmergencyContact(request))
                .build();
    }

    @PostMapping("/exportChannelUserOrdersPage")
    public CommonResponse<String> exportChannelUserOrdersPage(@RequestBody ChannelUserOrdersReqDto request) {
        if (!LoginUserUtil.getLoginUser().getShopId().equals("OPE")) {
            request.setMarketingChannelId(LoginUserUtil.getLoginUser().getShopId());
        }
        return CommonResponse.<String>builder().data(userOrdersService.exportChannelUserOrdersPage(request)).build();
    }
}
