package com.rent.service.order.impl;

import com.rent.common.dto.components.response.AliPayTradeCreateResponse;
import com.rent.common.dto.order.response.UserPaySettlementResponse;
import com.rent.common.dto.order.resquest.PaySettlementCallBackRequest;
import com.rent.common.dto.order.resquest.UserPaySettlementReqDto;
import com.rent.common.enums.components.EnumAliPayStatus;
import com.rent.common.enums.components.EnumTradeType;
import com.rent.common.enums.order.EnumOrderError;
import com.rent.common.enums.order.EnumOrderSettlementStatus;
import com.rent.common.enums.order.EnumOrderStatus;
import com.rent.common.util.AsyncUtil;
import com.rent.dao.order.OrderSettlementDao;
import com.rent.dao.order.UserOrdersDao;
import com.rent.exception.HzsxBizException;
import com.rent.model.order.OrderSettlement;
import com.rent.model.order.UserOrders;
import com.rent.service.order.CapitalOperateCore;
import com.rent.service.order.OrderFinishCore;
import com.rent.service.order.OrderOperateCore;
import com.rent.service.order.OrderSettlementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 订单结算Service
 *
 * @author xiaoyao
 * @Date 2020-06-18 18:16
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class OrderSettlementServiceImpl implements OrderSettlementService {

    private final OrderSettlementDao orderSettlementDao;
    private final UserOrdersDao userOrdersDao;
    private final CapitalOperateCore capitalOperateCore;
    private final OrderOperateCore orderOperateCore;
    private final OrderFinishCore orderFinishCore;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void userModifySettlementApply(String orderId) {
        OrderSettlement orderSettlement = orderSettlementDao.selectOneByOrderId(orderId);
        if (null == orderSettlement) {
            throw new HzsxBizException(EnumOrderError.SETTLEMENT_NOT_EXISTS_ERROR.getCode(),EnumOrderError.SETTLEMENT_NOT_EXISTS_ERROR.getMsg(), this.getClass());
        }
        if (!EnumOrderSettlementStatus.UNPAID.equals(orderSettlement.getSettlementStatus())) {
            throw new HzsxBizException(EnumOrderError.ORDER_SETTLEMENT_STATUS_ERROR.getCode(),
                EnumOrderError.ORDER_SETTLEMENT_STATUS_ERROR.getMsg(), this.getClass());
        }
        //修改订单状态及结算单状态
        Date now = new Date();
        UserOrders userOrders = new UserOrders();
        userOrders.setOrderId(orderId);
        userOrders.setUpdateTime(now);
        userOrders.setCancelTime(now);
        userOrders.setStatus(EnumOrderStatus.WAITING_SETTLEMENT);
        userOrdersDao.updateByOrderId(userOrders);

        orderSettlement.setApplyModifyTimes(orderSettlement.getApplyModifyTimes() + 1);
        orderSettlement.setSettlementStatus(EnumOrderSettlementStatus.APPLY_MODIFY);
        orderSettlement.setUpdateTime(now);
        orderSettlementDao.updateById(orderSettlement);
    }

    @Override
    public UserPaySettlementResponse liteUserPaySettlement(UserPaySettlementReqDto userPaySettlementReqDto) {
        OrderSettlement orderSettlement = orderSettlementDao.selectOneByOrderId(userPaySettlementReqDto.getOrderId());
        //校验状态
        if (null == orderSettlement || !EnumOrderSettlementStatus.UNPAID.equals(
                orderSettlement.getSettlementStatus())) {
            throw new HzsxBizException(EnumOrderError.SETTLEMENT_NOT_EXISTS_ERROR.getCode(), "结算单不存在或状态错误",
                    this.getClass());
        }
        if (userPaySettlementReqDto.getAmount()
                .compareTo(orderSettlement.getLoseAmount()
                        .add(orderSettlement.getDamageAmount()
                                .add(orderSettlement.getPenaltyAmount()))) != 0) {
            throw new HzsxBizException(EnumOrderError.SETTLEMENT_AMOUNT_ERROR.getCode(),
                    EnumOrderError.SETTLEMENT_AMOUNT_ERROR.getMsg(), this.getClass());
        }
        orderSettlement.setUpdateTime(new Date());
        orderSettlementDao.updateById(orderSettlement);
        UserOrders userOrders = userOrdersDao.selectOneByOrderId(orderSettlement.getOrderId());

        AliPayTradeCreateResponse aliPayTradeCreateResponse = capitalOperateCore.alipayTradeCreate(EnumTradeType.SETTLEMENT_PAY,null,
                "结算单支付 订单号:" + orderSettlement.getOrderId(),userOrders.getOrderId(), orderSettlement.getOutTrandNo(), userPaySettlementReqDto.getAmount(),
                userOrders.getUid(), null, null, userOrders.getProductId());

        return UserPaySettlementResponse.builder()
                .orderId(userPaySettlementReqDto.getOrderId())
                .payUrl(aliPayTradeCreateResponse.getTradeNo())
                .serialNo(aliPayTradeCreateResponse.getSerialNo())
                .build();
    }

    @Override
    public void paySettlementCallBack(PaySettlementCallBackRequest request) {
        OrderSettlement orderSettlement = orderSettlementDao.selectOneByOrderId(request.getOrderId());
        UserOrders userOrders = userOrdersDao.selectOneByOrderId(request.getOrderId());
        //校验状态
        if (null == orderSettlement || !EnumOrderSettlementStatus.UNPAID.equals(
            orderSettlement.getSettlementStatus())) {
            throw new HzsxBizException(EnumOrderError.SETTLEMENT_NOT_EXISTS_ERROR.getCode(), "结算单不存在或状态错误",
                this.getClass());
        }
        if (request.getPayStatus()
            .equals(EnumAliPayStatus.SUCCESS)) {
            //添加订单操作记录 add at 2020年10月27日13:58:41
            AsyncUtil.runAsync(() -> orderOperateCore.orderOperationRegister(userOrders.getOrderId(), userOrders.getStatus(), userOrders.getStatus(),userOrders.getUid(), userOrders.getUserName(), "买家支付结算单"));
            //修改结算单状态，订单状态
            orderSettlement.setSettlementStatus(EnumOrderSettlementStatus.SETTLED);
            orderSettlement.setUpdateTime(new Date());
            orderSettlement.setPaymentTime(request.getPaymentTime());
            orderSettlement.setTradeNo(request.getTradeNo());
            orderSettlementDao.updateById(orderSettlement);
            //订单完成
            orderFinishCore.orderFinishCommHandle(userOrders);
            return;
        }
    }

}