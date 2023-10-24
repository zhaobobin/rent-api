package com.rent.service.order.impl;

import com.rent.common.enums.components.EnumAliPayStatus;
import com.rent.common.enums.order.EnumOrderByStagesStatus;
import com.rent.common.enums.order.EnumOrderPayDepositStatus;
import com.rent.common.enums.order.PaymentMethod;
import com.rent.dao.order.OrderByStagesDao;
import com.rent.dao.order.OrderPayDepositDao;
import com.rent.dao.order.OrderPayDepositLogDao;
import com.rent.exception.HzsxBizException;
import com.rent.model.order.OrderByStages;
import com.rent.model.order.OrderPayDeposit;
import com.rent.model.order.OrderPayDepositLog;
import com.rent.service.order.OrderByStagesService;
import com.rent.service.order.OrderStageService;
import com.rent.util.LoginUserUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;


/**
 * @author zhaowenchao
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class OrderStageServiceImpl implements OrderStageService {

    private final OrderByStagesDao orderByStagesDao;
    private final OrderPayDepositDao orderPayDepositDao;
    private final OrderPayDepositLogDao orderPayDepositLogDao;
    private final OrderByStagesService orderByStagesService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean depositMortgage(Long id) {

        OrderByStages orderByStages = orderByStagesDao.getById(id);
        if(!EnumOrderByStagesStatus.UNPAY_SET.contains(orderByStages.getStatus())){
            throw new HzsxBizException("-1","账单状态错误");
        }
        String orderId = orderByStages.getOrderId();
        OrderPayDeposit deposit = orderPayDepositDao.getByOrderId(orderId);
        if(!deposit.getStatus().equals(EnumOrderPayDepositStatus.PAID)){
            throw new HzsxBizException("-1","押金状态错误");
        }
        if(orderByStages.getCurrentPeriodsRent().compareTo(deposit.getAmount())>0){
            throw new HzsxBizException("-1","押金不足");
        }

        BigDecimal originAmount = deposit.getAmount();
        BigDecimal amount = originAmount.subtract(orderByStages.getCurrentPeriodsRent());
        Long backstageUserId = LoginUserUtil.getLoginUser().getId();
        String mobile = LoginUserUtil.getLoginUser().getMobile();

        String remark = "抵扣:"+orderId+"_"+orderByStages.getCurrentPeriods();
        OrderPayDepositLog depositLog = orderPayDepositLogDao.saveLog(orderId,originAmount,amount,backstageUserId,mobile,remark);
        deposit.setAmount(amount);
        orderPayDepositDao.updateById(deposit);

        String tradeNo = "DEPOSIT_"+backstageUserId+"_"+System.currentTimeMillis();
        String outTradeNo = "DEPOSIT_LOG_"+depositLog.getId();
        List<String> period = Arrays.asList(orderByStages.getCurrentPeriods().toString());
        BigDecimal totalAmount = orderByStages.getCurrentPeriodsRent();
        orderByStagesService.payedOrderByStages(orderId,tradeNo,outTradeNo,period,totalAmount,EnumAliPayStatus.SUCCESS,PaymentMethod.MORTGAGE);
        return Boolean.TRUE;
    }
}
