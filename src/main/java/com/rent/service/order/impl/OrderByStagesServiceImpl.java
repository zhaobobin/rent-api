package com.rent.service.order.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rent.common.converter.order.OrderByStagesConverter;
import com.rent.common.dto.backstage.OrderByStagesForValetDto;
import com.rent.common.dto.components.request.AliPayTradeSyncReq;
import com.rent.common.dto.components.response.AliPayTradeCreateResponse;
import com.rent.common.dto.order.OrderByStagesDto;
import com.rent.common.dto.order.response.OrderByStagesPayResponse;
import com.rent.common.dto.order.resquest.OrderByStagesPayRequest;
import com.rent.common.enums.components.EnumAliPayStatus;
import com.rent.common.enums.components.EnumTradeType;
import com.rent.common.enums.order.*;
import com.rent.common.util.OSSFileUtils;
import com.rent.dao.order.OrderByStagesDao;
import com.rent.dao.order.UserOrdersDao;
import com.rent.exception.HzsxBizException;
import com.rent.model.order.OrderByStages;
import com.rent.model.order.UserOrders;
import com.rent.service.components.AliPayCapitalService;
import com.rent.service.order.CapitalOperateCore;
import com.rent.service.order.OrderByStagesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户订单分期Service
 *
 * @author xiaoyao
 * @Date 2020-06-11 17:25
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class OrderByStagesServiceImpl implements OrderByStagesService {

    private final OrderByStagesDao orderByStagesDao;
    private final CapitalOperateCore capitalOperateCore;
    private final UserOrdersDao userOrdersDao;
    private final AliPayCapitalService aliPayCapitalService;
    private final OSSFileUtils ossFileUtils;

    @Override
    public void payedOrderByStages(String orderId, String tradeNo, String outTradeNo, List<String> period,
                                   BigDecimal totalAmount, EnumAliPayStatus status, PaymentMethod paymentMethod) {
        PaymentMethod method = paymentMethod == null ? PaymentMethod.ZFB : paymentMethod;
        //其他扣款的回调处理
        dealPayedOrderByStages(orderId, tradeNo, outTradeNo, period, status, method);
        if (PaymentMethod.VALET_PAYMENT.equals(method)) {
            AliPayTradeSyncReq req = new AliPayTradeSyncReq();
            req.setOrderId(orderId);
            req.setPeriods(period);
            aliPayCapitalService.alipayTradeOrderInfoSync(req);
        }
    }

    @Override
    public OrderByStagesForValetDto selectValetPayment(String orderId, Integer currentPeriod) {
        OrderByStages orderByStages = orderByStagesDao.queryOrderByOrderIdAndPeriod(orderId,currentPeriod);
        OrderByStagesForValetDto order = OrderByStagesConverter.model2DtoForValet(orderByStages);
        order.setProof(ossFileUtils.getPrefix() + orderByStages.getOutTradeNo());
        return order;
    }


    private void dealPayedOrderByStages(String orderId, String tradeNo, String outTradeNo, List<String> period, EnumAliPayStatus status, PaymentMethod paymentMethod) {
        List<Integer> periods = period.stream().map(Integer::valueOf).collect(Collectors.toList());
        List<OrderByStages> orderByStagesList = orderByStagesDao.list(new QueryWrapper<OrderByStages>()
                .eq("order_id",orderId)
                .in("current_periods",periods));

        if (EnumAliPayStatus.CANCEL.equals(status)) {
            orderByStagesList.forEach(orderByStages -> {
                orderByStages.setUpdateTime(new Date());
                orderByStagesDao.updateById(orderByStages);
            });
            return;
        }
        boolean overDuePayedFlag = false;
        for (OrderByStages orderByStages : orderByStagesList) {
            if (EnumOrderByStagesStatus.OVERDUE_NOT_PAY.equals(orderByStages.getStatus())) {
                overDuePayedFlag = true;
            }
            orderByStages.setPaymentMethod(paymentMethod);
            orderByStages.setStatus(EnumOrderByStagesStatus.OVERDUE_NOT_PAY.equals(orderByStages.getStatus()) ?
                    EnumOrderByStagesStatus.OVERDUE_PAYED : EnumOrderByStagesStatus.PAYED);
            orderByStages.setTradeNo(tradeNo);
            orderByStages.setOutTradeNo(outTradeNo);
            orderByStages.setUpdateTime(new Date());
            orderByStages.setRepaymentDate(new Date());
            orderByStagesDao.updateById(orderByStages);
        }
        //更新订单逾期状态
        if (overDuePayedFlag) {
            //没有逾期状态账单，更新订单状态为正常
            List<OrderByStages> overDueOrderByStagesList = orderByStagesDao.list(new QueryWrapper<>(
                    OrderByStages.builder()
                            .orderId(orderId)
                            .status(EnumOrderByStagesStatus.OVERDUE_NOT_PAY)
                            .build()));
            if (CollectionUtil.isEmpty(overDueOrderByStagesList)) {
                UserOrders userOrders = userOrdersDao.selectOneByOrderId(orderId);
                userOrders.setIsViolation(EnumViolationStatus.NORMAL);
                userOrders.setUpdateTime(new Date());
                userOrdersDao.updateById(userOrders);
            }
        }
    }

    @Override
    public List<OrderByStagesDto> queryOrderByStagesByOrderId(String orderId) {
        List<OrderByStages> list = orderByStagesDao.list(new QueryWrapper<>(OrderByStages.builder()
            .orderId(orderId)
            .build()));
        return OrderByStagesConverter.modelList2DtoList(list);
    }

    private void orderByStagesBeforePay(UserOrders userOrders,List<OrderByStages> orderByStagesList,
        OrderByStagesPayRequest request){

        if (!EnumOrderStatus.TO_AUDIT.equals(userOrders.getStatus()) && !EnumOrderStatus.PENDING_DEAL.equals(
                userOrders.getStatus()) && !EnumOrderStatus.RENTING.equals(userOrders.getStatus())
                && !EnumOrderStatus.WAITING_USER_RECEIVE_CONFIRM.equals(userOrders.getStatus())) {
            log.error("订单:{}状态错误，期望的状态是:[{}],实际状态是:[{}]", userOrders.getOrderId(),
                    EnumOrderStatus.TO_AUDIT.getDescription() + "||" + EnumOrderStatus.PENDING_DEAL.getDescription() + "||"
                            + EnumOrderStatus.RENTING.getDescription(), userOrders.getStatus()
                            .getDescription());
            throw new HzsxBizException(EnumOrderError.ORDER_STATUS_NOT_ALLOW_APPLY.getCode(), "订单当前状态不允许进行[账单主动支付]操作");
        }

        if (CollectionUtil.isEmpty(orderByStagesList)) {
            throw new HzsxBizException(EnumOrderError.ORDER_BY_STAGES_NOT_EXISTS.getCode(),EnumOrderError.ORDER_BY_STAGES_NOT_EXISTS.getMsg(), this.getClass());
        }
        orderByStagesList.forEach(orderByStages -> {
            if (!(orderByStages.getStatus().equals(EnumOrderByStagesStatus.UN_PAY) || orderByStages.getStatus().equals(EnumOrderByStagesStatus.OVERDUE_NOT_PAY))) {
                throw new HzsxBizException(EnumOrderError.ORDER_BY_STAGES_NOT_OPERATOR.getCode(),EnumOrderError.ORDER_BY_STAGES_NOT_OPERATOR.getMsg(), this.getClass());
            }
        });
        BigDecimal totalAmount = orderByStagesList.stream().map(OrderByStages::getCurrentPeriodsRent).reduce(BigDecimal::add).get();

        if (request.getTotalAmount().compareTo(totalAmount) != 0) {
            throw new HzsxBizException(EnumOrderError.ORDER_BY_STAGES_RENT_ERROR.getCode(),EnumOrderError.ORDER_BY_STAGES_RENT_ERROR.getMsg(), this.getClass());
        }
    }

    @Override
    @Transactional
    public OrderByStagesPayResponse liteOrderByStagesPay(OrderByStagesPayRequest request) {
        UserOrders userOrders = userOrdersDao.selectOneByOrderId(request.getOrderId());
        List<OrderByStages> orderByStagesList = orderByStagesDao.list(new QueryWrapper<OrderByStages>()
                .eq("order_id",request.getOrderId())
                .in("current_periods", request.getPeriodList().stream().map(Integer::valueOf).collect(Collectors.toList())));
        orderByStagesBeforePay(userOrders,orderByStagesList,request);
        StringBuffer s = new StringBuffer();
        orderByStagesList.forEach(a -> s.append(a.getCurrentPeriods().toString()));
        String outOrderNo = "AAP" + request.getOrderId() + "_" + RandomUtil.randomNumbers(4) + "_" + s;
        String subject = "订单:" + request.getOrderId() + " | 期次:" + request.getPeriodList()
                .toString();

        //调用支付
        AliPayTradeCreateResponse aliPayTradeCreateResponse = capitalOperateCore.alipayTradeCreate(EnumTradeType.USER_PAY_BILL,request.getPeriodList(),
                subject,request.getOrderId(), outOrderNo, request.getTotalAmount(),
                userOrders.getUid(), null, null, userOrders.getProductId());
        return OrderByStagesPayResponse.builder()
                .payUrl(aliPayTradeCreateResponse.getTradeNo())
                .serialNo(aliPayTradeCreateResponse.getSerialNo())
                .build();
    }



    @Override
    public OrderByStages getEarliestPeriod(String orderId) {
        return orderByStagesDao.getEarliestPeriod(orderId);
    }
}