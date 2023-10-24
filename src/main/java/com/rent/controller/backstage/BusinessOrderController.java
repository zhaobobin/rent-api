package com.rent.controller.backstage;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rent.common.constant.RedisKey;
import com.rent.common.dto.CommonResponse;
import com.rent.common.dto.backstage.BackstageUserOrderDetailDto;
import com.rent.common.dto.backstage.BusinessOrderStaticsDto;
import com.rent.common.dto.backstage.QueryUserOrderDetailRequest;
import com.rent.common.dto.bo.LoginUserBo;
import com.rent.common.dto.components.request.DecisionRequest;
import com.rent.common.dto.components.response.ExpressInfoResponse;
import com.rent.common.dto.components.response.RiskReportResponse;
import com.rent.common.dto.order.*;
import com.rent.common.dto.order.response.BackstageBuyOutOrderDetailDto;
import com.rent.common.dto.order.resquest.*;
import com.rent.common.enums.common.EnumProductError;
import com.rent.common.enums.order.*;
import com.rent.exception.HzsxBizException;
import com.rent.service.components.DecisionReportService;
import com.rent.service.components.SxService;
import com.rent.service.order.*;
import com.rent.util.LoginUserUtil;
import com.rent.util.RedisUtil;
import com.rent.util.StringUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@Tag(name = "商家订单模块")
@RequestMapping("/zyj-backstage-web/hzsx/business/order")
@RequiredArgsConstructor
public class BusinessOrderController {

    private final OpeUserOrdersService opeUserOrdersService;
    private final OrderRemarkService orderRemarkService;
    private final OrderAuditService orderAuditService;
    private final UserOrdersStatusTransferService userOrdersStatusTransferService;
    private final OrderShopCloseService orderShopCloseService;
    private final BusinessUserOrdersService businessUserOrdersService;
    private final OrderContractService orderContractService;
    private final DecisionReportService decisionReportService;
    private final OrderAntChainService orderAntChainService;
    private final SxService sxService;
    private final OrderReceiptCore orderReceiptCore;
    private final OrderAddressService orderAddressService;

    @Operation(summary = "查询所有订单")
    @PostMapping("/queryOrderByCondition")
    public CommonResponse<Page<BackstageUserOrderDto>> queryOrderByCondition(@RequestBody OrderByConditionRequest request) {
        LoginUserBo loginUser = LoginUserUtil.getLoginUser();
        if (StringUtil.isEmpty(loginUser.getShopId())) {
            throw new HzsxBizException("9999", "登录用户信息不完善,请联系运营人员");
        }
        request.setShopId(loginUser.getShopId());
        if (request.getIsMyAuditOrder()) {
            request.setBackstageUserId(loginUser.getId());
        }
        return CommonResponse.<Page<BackstageUserOrderDto>>builder().data(opeUserOrdersService.queryOpeOrderByCondition(request)).build();
    }


    @Operation(summary = "查询订单详细")
    @PostMapping("/queryBusinessUserOrderDetail")
    public CommonResponse<BackstageUserOrderDetailDto> queryBusinessUserOrderDetail(@RequestBody QueryUserOrderDetailRequest request) {
        BackstageUserOrderDetailDto response = this.opeUserOrdersService.queryOpeUserOrderDetail(request.getOrderId());
        return CommonResponse.<BackstageUserOrderDetailDto>builder().data(response).build();
    }

    @Operation(summary = "生成订单合同")
    @GetMapping("/generateOrderContract")
    CommonResponse<String> generateOrderContract(@RequestParam("orderId") String orderId) {
        return CommonResponse.<String>builder().data(orderContractService.signOrderContract(orderId)).build();
    }


    @Operation(summary = "查询风控报告")
    @PostMapping("/querySiriusReportByUid")
    public CommonResponse<RiskReportResponse> querySiriusReportByUid(@RequestBody DecisionRequest request) {
        return CommonResponse.<RiskReportResponse>builder().data(decisionReportService.getSiriusReportByOrderId(request)).build();
    }

    @Operation(summary = "查询逾期订单列表")
    @PostMapping("/queryOverDueOrdersByCondition")
    public CommonResponse<Page<BackstageUserOrderDto>> queryOverDueOrdersByCondition(
            @RequestBody OrderByConditionRequest request) {
        LoginUserBo loginUser = LoginUserUtil.getLoginUser();
        if (StringUtil.isEmpty(loginUser.getShopId())) {
            throw new HzsxBizException("999999", "登录用户信息不完善,请联系运营人员");
        }
        request.setShopId(loginUser.getShopId());
        request.setViolationStatusList(Arrays.asList(EnumViolationStatus.SETTLEMENT_OVERDUE, EnumViolationStatus.STAGE_OVERDUE));
        request.setOrderTypeList(Arrays.asList(EnumOrderType.GENERAL_ORDER, EnumOrderType.RELET_ORDER));
        return CommonResponse.<Page<BackstageUserOrderDto>>builder().data(opeUserOrdersService.queryOpeOrderByCondition(request)).build();
    }

    @Operation(summary = "查询买断订单")
    @PostMapping("/queryBuyOutOrdersByCondition")
    public CommonResponse<Page<OpeBuyOutOrdersDto>> queryBuyOutOrdersByCondition(@RequestBody QueryBuyOutOrdersRequest request) {
        LoginUserBo loginUser = LoginUserUtil.getLoginUser();
        if (StringUtil.isEmpty(loginUser.getShopId())) {
            throw new HzsxBizException("999999", "登录用户信息不完善,请联系运营人员");
        }
        request.setShopId(loginUser.getShopId());
        return CommonResponse.<Page<OpeBuyOutOrdersDto>>builder().data(opeUserOrdersService.queryOpeBuyOutOrderList(request)).build();
    }

    @Operation(summary = "查询买断订单详情")
    @PostMapping("/queryBuyOutOrderDetail")
    public CommonResponse<BackstageBuyOutOrderDetailDto> queryBuyOutOrderDetail(@RequestBody QueryBuyOutOrderDetailRequest request) {
        BackstageBuyOutOrderDetailDto resp = opeUserOrdersService.queryOpeBuyOutOrderDetail(request.getBuyOutOrderId());
        return CommonResponse.<BackstageBuyOutOrderDetailDto>builder().data(resp).build();
    }

    @Operation(summary = "查询续租订单")
    @PostMapping("/queryReletOrderByCondition")
    public CommonResponse<Page<BackstageUserOrderDto>> queryReletOrderByCondition(@RequestBody OpeOrderRequest request) {
        LoginUserBo loginUser = LoginUserUtil.getLoginUser();
        if (StringUtil.isEmpty(loginUser.getShopId())) {
            throw new HzsxBizException("999999", "登录用户信息不完善,请联系运营人员");
        }
        request.setShopId(loginUser.getShopId());
        request.setOrderType(EnumOrderType.RELET_ORDER);
        OrderByConditionRequest conditionRequest = new OrderByConditionRequest();
        BeanUtil.copyProperties(request, conditionRequest);
        return CommonResponse.<Page<BackstageUserOrderDto>>builder().data(opeUserOrdersService.queryOpeOrderByCondition(conditionRequest)).build();
    }

    @Operation(summary = "查询续租订单详情")
    @PostMapping("/queryUserReletOrderDetail")
    public CommonResponse<BackstageUserOrderDetailDto> queryUserReletOrderDetail(@RequestBody QueryUserOrderDetailRequest request) {
        BackstageUserOrderDetailDto resp = opeUserOrdersService.queryOpeUserOrderDetail(request.getOrderId());
        return CommonResponse.<BackstageUserOrderDetailDto>builder().data(resp).build();
    }

    @Operation(summary = "查询物流信息")
    @GetMapping("/queryExpressInfo")
    public CommonResponse<ExpressInfoResponse> queryExpressInfo(@RequestParam("expressNo") String expressNo, @RequestParam("shortName") String shortName, @RequestParam("receiverPhone") String receiverPhone) {
        ExpressInfoResponse response = sxService.getExpressList(shortName, expressNo, receiverPhone);
        return CommonResponse.<ExpressInfoResponse>builder().data(response).build();
    }

    @Operation(summary = "添加备注")
    @PostMapping("orderRemark")
    public CommonResponse<Void> orderRemark(@RequestBody OrderRemarkRequest request) {
        LoginUserBo loginUser = LoginUserUtil.getLoginUser();
        if (StringUtil.isEmpty(loginUser.getShopId())) {
            throw new HzsxBizException("999999", "登录用户信息不完善,请联系运营人员");
        }
        orderRemarkService.orderRemark(request.getOrderId(), request.getRemark(), loginUser.getName(), EnumOrderRemarkSource.BUSINESS);
        return CommonResponse.<Void>builder().build();
    }

    @Operation(summary = "查询备注")
    @PostMapping("queryOrderRemark")
    public CommonResponse<Page<OrderRemarkDto>> queryOrderRemark(@RequestBody OrderRemarkReqDto request) {
        return CommonResponse.<Page<OrderRemarkDto>>builder().data(orderRemarkService.queryOrderRemarkPage(request)).build();
    }

    @Operation(summary = "电审审核")
    @PostMapping("/telephoneAuditOrder")
    public CommonResponse<Void> telephoneAuditOrder(@RequestBody TelephoneOrderAuditRequest request) {
        LoginUserBo loginUser = LoginUserUtil.getLoginUser();
        if (StringUtil.isEmpty(loginUser.getShopId())) {
            throw new HzsxBizException("999999", "登录用户信息不完善,请联系运营人员");
        }
        request.setOperatorRole(EnumOrderOperatorRole.BUSINESS);
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


    @PostMapping("/orderDelivery")
    @Operation(summary = "商家发货")
    public CommonResponse<String> orderDelivery(@RequestBody BusinessOrderDeliveryReqDto request) {
        LoginUserBo loginUser = LoginUserUtil.getLoginUser();
        if (StringUtil.isEmpty(loginUser.getShopId())) {
            throw new HzsxBizException("999999", "登录用户信息不完善,请联系运营人员");
        }
        request.setOperatorName(loginUser.getName());
        String result = businessUserOrdersService.orderDelivery(request.getOrderId(), request.getExpressId(), request.getExpressNo(), request.getOperatorName(), request.getCostPrice(), request.getSerialNumber());
        if ("已发货".equals(result)) {
            if (request.getAntChain()) {
                orderAntChainService.syncToAntChain(request.getOrderId());
            }
        }
        return CommonResponse.<String>builder().data(result).build();
    }

    @GetMapping("/checkOrderIsAuth")
    @Operation(summary = "校验订单是否用户认证")
    public CommonResponse<String> checkOrderIsAuth(@RequestParam("orderId") String orderId) {
        return CommonResponse.<String>builder().data(businessUserOrdersService.checkOrderIsAuth(orderId)).build();
    }

    @PostMapping("/orderPickUp")
    @Operation(summary = "用户自提")
    public CommonResponse<Void> orderPickUp(@RequestBody BusinessOrderPickUpReqDto request) {
        LoginUserBo loginUser = LoginUserUtil.getLoginUser();
        if (StringUtil.isEmpty(loginUser.getShopId())) {
            throw new HzsxBizException("999999", "登录用户信息不完善,请联系运营人员");
        }
        orderReceiptCore.pickUp(request.getOrderId(), loginUser.getName());
        return CommonResponse.<Void>builder().build();
    }

    @Operation(summary = "商家出具结算单")
    @PostMapping("/merchantsIssuedStatements")
    public CommonResponse<Void> merchantsIssuedStatements(@RequestBody BusinessIssuedStatementsReqDto reqDto) {
        LoginUserBo loginUser = LoginUserUtil.getLoginUser();
        if (StringUtil.isEmpty(loginUser.getShopId())) {
            throw new HzsxBizException("999999", "登录用户信息不完善,请联系运营人员");
        }
        reqDto.setOperatorName(loginUser.getName());
        businessUserOrdersService.merchantsIssuedStatements(reqDto);
        return CommonResponse.<Void>builder().build();
    }

    @Operation(summary = "商家确认归还")
    @PostMapping("/businessConfirmReturnOrder")
    public CommonResponse<Void> businessConfirmReturnOrder(@RequestBody BusinessConfirmReturnReqDto reqDto) {
        LoginUserBo loginUser = LoginUserUtil.getLoginUser();
        if (StringUtil.isEmpty(loginUser.getShopId())) {
            throw new HzsxBizException("999999", "登录用户信息不完善,请联系运营人员");
        }
        reqDto.setOperatorName(loginUser.getName());
        if (null == reqDto.getReturnTime() || null == reqDto.getExpressId()) {
            throw new HzsxBizException(EnumProductError.PARAMS_NOT_EXISTS.getCode(), EnumProductError.PARAMS_NOT_EXISTS.getMsg());
        }
        businessUserOrdersService.businessConfirmReturnOrder(reqDto);
        return CommonResponse.<Void>builder().build();
    }

    @Operation(summary = "商家关单")
    @PostMapping("/businessClosePayedOrder")
    public CommonResponse<Void> businessClosePayedOrder(@RequestBody ShopRiskReviewOrderIdDto request) {
        LoginUserBo loginUser = LoginUserUtil.getLoginUser();
        if (StringUtil.isEmpty(loginUser.getShopId())) {
            throw new HzsxBizException("999999", "登录用户信息不完善,请联系运营人员");
        }
        request.setShopId(loginUser.getShopId());
        request.setShopOperatorId(loginUser.getMobile());
        try {
            if (!RedisUtil.tryLock(RedisKey.ORDER_BACKSTAGE_OPERATOR_LOCK + request.getOrderId(), 7)) {
                throw new HzsxBizException("00000", "请不要连续点击!!!");
            }
            OrderRemarkDto remarkDto = new OrderRemarkDto();
            remarkDto.setSource(EnumOrderRemarkSource.BUSINESS);
            remarkDto.setOrderType(EnumOrderType.GENERAL_ORDER);
            remarkDto.setOrderId(request.getOrderId());
            remarkDto.setUserName(loginUser.getName());
            remarkDto.setRemark("关闭订单-" + request.getCloseReason());
            remarkDto.setCreateTime(new Date());
            orderRemarkService.addOrderRemark(remarkDto);
            orderShopCloseService.shopRiskCloseOrder(request);
        } finally {
            RedisUtil.unLock(RedisKey.ORDER_BACKSTAGE_OPERATOR_LOCK + request.getOrderId());
        }
        return CommonResponse.<Void>builder().build();
    }

    @Operation(summary = "商家查询审核记录")
    @PostMapping("/queryOrderAuditRecord")
    public CommonResponse<OrderAuditRecordDto> queryOrderAuditRecord(@RequestBody QueryOrderAuditRequest request) {
        return CommonResponse.<OrderAuditRecordDto>builder().data(orderAuditService.getByOrderId(request.getOrderId())).build();
    }

    @Operation(summary = "商家查询订单操作节点")
    @PostMapping("/queryOrderStatusTransfer")
    CommonResponse<List<UserOrdersStatusTransferDto>> queryOrderStatusTransfer(@RequestBody QueryOrderStatusTransferRequest request) {
        return CommonResponse.<List<UserOrdersStatusTransferDto>>builder().data(userOrdersStatusTransferService.queryRecordByOrderId(request.getOrderId())).build();
    }

    @Operation(summary = "商家订单统计")
    @PostMapping("/businessOrderStatistics")
    public CommonResponse<BusinessOrderStaticsDto> businessOrderStatistics() {
        LoginUserBo loginUser = LoginUserUtil.getLoginUser();
        if (StringUtil.isEmpty(loginUser.getShopId())) {
            throw new HzsxBizException("999999", "登录用户信息不完善,请联系运营人员");
        }
        BusinessOrderStaticsDto dto = businessUserOrdersService.businessOrderStatistics(loginUser.getShopId());
        return CommonResponse.<BusinessOrderStaticsDto>builder().data(dto).build();
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

    @Operation(summary = "查询订单账单相关信息")
    @ResponseBody
    @PostMapping("/queryOrderStagesDetail")
    public CommonResponse<BackstageOrderStagesDto> queryOrderStagesDetail(@RequestBody OrderStagesQueryRequest request) {
        return CommonResponse.<BackstageOrderStagesDto>builder().data(opeUserOrdersService.queryOrderStagesDetail(request.getOrderId())).build();
    }

    @Operation(summary = "查询待归还订单订单")
    @PostMapping("/queryWaitingGiveBackOrder")
    public CommonResponse<Page<BackstageUserOrderDto>> queryWaitingGiveBackOrder(@RequestBody OpeOrderRequest request) {
        LoginUserBo loginUser = LoginUserUtil.getLoginUser();
        if (StringUtil.isEmpty(loginUser.getShopId())) {
            throw new HzsxBizException("999999", "登录用户信息不完善,请联系运营人员");
        }
        request.setShopId(loginUser.getShopId());
        request.setUnrentDateEnd(new Date());
        request.setStatus(EnumOrderStatus.RENTING.getCode());
        request.setOrderTypeList(Arrays.asList(EnumOrderType.GENERAL_ORDER, EnumOrderType.RELET_ORDER));
        OrderByConditionRequest conditionRequest = new OrderByConditionRequest();
        BeanUtil.copyProperties(request, conditionRequest);
        return CommonResponse.<Page<BackstageUserOrderDto>>builder().data(opeUserOrdersService.queryOpeOrderByCondition(conditionRequest)).build();
    }

    @Operation(summary = "根据orderId判断是否有风控报告")
    @GetMapping("/queryWhetherRiskReport")
    public CommonResponse<Boolean> queryWhetherRiskReport(@RequestParam String orderId) {
        return CommonResponse.<Boolean>builder().data(decisionReportService.queryWhetherRiskReport(orderId)).build();
    }
}
