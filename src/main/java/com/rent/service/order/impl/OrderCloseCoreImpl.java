package com.rent.service.order.impl;

import com.rent.common.dto.bo.LoginUserBo;
import com.rent.common.dto.components.dto.SendMsgDto;
import com.rent.common.dto.product.ShopDto;
import com.rent.common.enums.components.EnumTradeType;
import com.rent.common.enums.order.EnumOrderByStagesStatus;
import com.rent.common.enums.order.EnumOrderCloseType;
import com.rent.common.enums.order.EnumOrderStatus;
import com.rent.common.enums.order.PaymentMethod;
import com.rent.common.util.AsyncUtil;
import com.rent.dao.order.OrderAddressDao;
import com.rent.dao.order.OrderByStagesDao;
import com.rent.dao.order.UserOrdersDao;
import com.rent.model.order.OrderAddress;
import com.rent.model.order.OrderByStages;
import com.rent.model.order.UserOrders;
import com.rent.service.components.SendSmsService;
import com.rent.service.components.SuningOpenApiService;
import com.rent.service.marketing.LiteCouponCenterService;
import com.rent.service.order.CapitalOperateCore;
import com.rent.service.order.OrderCloseCore;
import com.rent.service.order.OrderOperateCore;
import com.rent.service.product.ShopService;
import com.rent.util.LoginUserUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

/**
 * @author xiaoyao
 * @version V1.0
 * @date 2020-6-17 14:56
 * @since 1.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class OrderCloseCoreImpl implements OrderCloseCore {

    private final UserOrdersDao userOrdersDao;
    private final OrderByStagesDao orderByStagesDao;
    private final OrderAddressDao orderAddressDao;
    private final CapitalOperateCore capitalOperateCore;
    private final OrderOperateCore orderOperateCore;
    private final LiteCouponCenterService liteCouponCenterService;
    private final ShopService shopService;
    private final SendSmsService sendSmsService;
    private final SuningOpenApiService suningOpenApiService;


    @Override
    public void closeOrderCommon(UserOrders userOrders, EnumOrderStatus status, EnumOrderCloseType orderCloseType,
                                 String cancelReason) {
        //更新订单状态为关闭
        Date now = new Date();
        userOrders.setUpdateTime(now);
        userOrders.setCancelTime(now);
        userOrders.setStatus(status);
        userOrders.setCloseType(orderCloseType);
        userOrders.setCancelReason(cancelReason);
        userOrdersDao.updateByOrderId(userOrders);
        //添加订单操作记录 add at 2020年10月27日13:58:41
        AsyncUtil.runAsync(
                () -> orderOperateCore.orderOperationRegister(userOrders.getOrderId(), userOrders.getStatus(), userOrders.getStatus(),
                        userOrders.getUid(), userOrders.getUserName(), "订单完结"));
        //更新还款账单记录状态
        orderByStagesDao.updateStatusByOrderId(userOrders.getOrderId(), EnumOrderByStagesStatus.CANCEL, EnumOrderByStagesStatus.UN_PAY);
        //优惠券退回
        liteCouponCenterService.updateCouponUnused(userOrders.getOrderId());
    }


    @Override
    public void payedCloseOrder(UserOrders userOrders, EnumOrderCloseType closeType, String cancelReason) {
        // 解冻
        capitalOperateCore.alipayUnfreeze(userOrders, 1, "关闭订单解冻", null, EnumTradeType.CLOSE_ORDER);
        //退租金
        OrderByStages example = OrderByStages.builder().orderId(userOrders.getOrderId()).build();
        BigDecimal totolAmount = new BigDecimal(BigInteger.ZERO);
        List<OrderByStages> orderByStagesList = orderByStagesDao.queryOrderByStagesList(example);
        if (CollectionUtils.isNotEmpty(orderByStagesList)) {
            for (OrderByStages orderByStages : orderByStagesList) {
                if (orderByStages.getStatus().equals(EnumOrderByStagesStatus.PAYED) && orderByStages.getCurrentPeriodsRent().compareTo(BigDecimal.ZERO) != 0) {
                    BigDecimal refundAmount = orderByStages.getCurrentPeriodsRent();
                    // 账单支付状态是 支付宝支付或者押金抵扣的方式则 退款走支付宝退款
                    if (PaymentMethod.ZFB.equals(orderByStages.getPaymentMethod()) || PaymentMethod.MORTGAGE.equals(orderByStages.getPaymentMethod())) {
                        // 支付宝支付的订单走 支付宝退款
                        this.capitalOperateCore.alipayTradeRefund(orderByStages, userOrders, "关闭订单退款", refundAmount, EnumTradeType.CLOSE_ORDER, userOrders.getUid());
                    } else if (PaymentMethod.YFB.equals(orderByStages.getPaymentMethod())) {
                        // 易付宝支付的账单 易付宝退款
                        this.suningOpenApiService.yfbTradeRefund(orderByStages, userOrders, "关闭订单退款", refundAmount, EnumTradeType.CLOSE_ORDER, userOrders.getUid());
                    }
                    orderByStages.setStatus(EnumOrderByStagesStatus.REFUNDED);
                    totolAmount = totolAmount.add(orderByStages.getCurrentPeriodsRent());
                } else {
                    orderByStages.setStatus(EnumOrderByStagesStatus.CANCEL);
                }
                //更新账单状态
                this.orderByStagesDao.updateById(orderByStages);
            }
        }
        //订单操作记录
        orderOperate(userOrders, closeType);
        //通用处理
        this.closeOrderCommon(userOrders, EnumOrderStatus.CLOSED, closeType, cancelReason);
        //发送短信
        AsyncUtil.runAsync(() -> closeSendSms(userOrders, closeType));
    }

    private void orderOperate(UserOrders userOrders, EnumOrderCloseType closeType) {
        try {
            String operatorId = "";
            String operatorName = "";
            LoginUserBo loginUser = LoginUserUtil.getLoginUser();
            if (null != loginUser) {
                operatorName = loginUser.getName();
                operatorId = loginUser.getId().toString();
            }
            switch (closeType) {
                case PAY_FAILED:
                    orderOperateCore.orderOperationRegister(userOrders.getOrderId(), userOrders.getStatus(),
                            EnumOrderStatus.CLOSED, userOrders.getUid(), userOrders.getUserName(), "支付失败");
                    break;
                case PLATFORM_CLOSE:
                    orderOperateCore.orderOperationRegister(userOrders.getOrderId(), userOrders.getStatus(),
                            EnumOrderStatus.CLOSED, operatorId, operatorName, "平台关单");
                    break;
                case RISK_CLOSE:
                    orderOperateCore.orderOperationRegister(userOrders.getOrderId(), userOrders.getStatus(),
                            EnumOrderStatus.CLOSED, "system", "系统", "系统风控关单");
                    break;
                case OPE_RISK_CLOSE:
                    orderOperateCore.orderOperationRegister(userOrders.getOrderId(), userOrders.getStatus(),
                            EnumOrderStatus.CLOSED, operatorId, operatorName, "平台风控关单");
                    break;
                case BUSINESS_RISK_CLOSE:
                    orderOperateCore.orderOperationRegister(userOrders.getOrderId(), userOrders.getStatus(),
                            EnumOrderStatus.CLOSED, operatorId, operatorName, "商家风控关单");
                    break;
                case BUSINESS_CLOSE:
                    orderOperateCore.orderOperationRegister(userOrders.getOrderId(), userOrders.getStatus(),
                            EnumOrderStatus.CLOSED, operatorId, operatorName, "商家关单(客户要求)");
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            log.error("order operator register error", e);
        }
    }

    /**
     * 关单发送短信
     *
     * @param userOrders 订单信息
     * @param closeType  关单类型
     */
    private void closeSendSms(UserOrders userOrders, EnumOrderCloseType closeType) {
        OrderAddress orderAddress = orderAddressDao.queryByOrderId(userOrders.getOrderId());
        SendMsgDto sendMsgDto = new SendMsgDto();
        sendMsgDto.setTelephone(orderAddress.getTelephone());
        sendMsgDto.setOrderId(userOrders.getOrderId());
        ShopDto shopDto = shopService.queryByShopId(userOrders.getShopId());
        sendMsgDto.setShopServiceTel(shopDto.getServiceTel());
        switch (closeType) {
            case PAY_FAILED:
                sendSmsService.payFailOrder(sendMsgDto);
                break;
            case PLATFORM_CLOSE:
                sendSmsService.platformCloseOrder(sendMsgDto);
                break;
            case RISK_CLOSE:
            case OPE_RISK_CLOSE:
            case BUSINESS_RISK_CLOSE:
                sendSmsService.riskCloseOrder(sendMsgDto);
                break;
            default:
                sendSmsService.closeOrderBySms(sendMsgDto);
                break;
        }
    }
}
