package com.rent.dao.order.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rent.common.enums.order.EnumOrderPayDepositStatus;
import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.order.OrderPayDepositDao;
import com.rent.mapper.order.OrderPayDepositMapper;
import com.rent.model.order.OrderPayDeposit;
import com.rent.util.AppParamUtil;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 用户支付押金表
 * @author zhaowenchao
 */
@Repository
public class OrderPayDepositDaoImpl extends AbstractBaseDaoImpl<OrderPayDeposit, OrderPayDepositMapper> implements OrderPayDepositDao {

    @Override
    public void initDepositRecord(String orderId,BigDecimal totalDeposit, BigDecimal amount, String channelId, String uid) {
        if (BigDecimal.ZERO.compareTo(amount) == 0) {
            this.initRecord(orderId,totalDeposit,amount,channelId,uid, EnumOrderPayDepositStatus.WITHDRAW);
        } else {
            this.initRecord(orderId,totalDeposit,amount,channelId,uid,EnumOrderPayDepositStatus.WAITING_PAYMENT);
        }
    }

    private void initRecord(String orderId,BigDecimal totalDeposit, BigDecimal amount, String channelId, String uid, EnumOrderPayDepositStatus status) {
        OrderPayDeposit orderPayDeposit = new OrderPayDeposit();
        orderPayDeposit.setOrderId(orderId);
        orderPayDeposit.setUid(uid);
        orderPayDeposit.setTotalDeposit(totalDeposit);
        orderPayDeposit.setRiskDeposit(amount);
        orderPayDeposit.setAmount(amount);
        orderPayDeposit.setChannelId(channelId);
        orderPayDeposit.setStatus(status);
        if (EnumOrderPayDepositStatus.WITHDRAW.equals(status)) {
            orderPayDeposit.setRefundTime(new Date());
        }
        orderPayDeposit.setCreateTime(new Date());
        save(orderPayDeposit);
    }

    @Override
    public OrderPayDeposit getWaitPaymentDeposit(String orderId) {
        return getOne(new QueryWrapper<OrderPayDeposit>().eq("order_id",orderId).eq("status", EnumOrderPayDepositStatus.WAITING_PAYMENT));
    }

    @Override
    public OrderPayDeposit getByOutTradeNo(String outTradeNo) {
        return getOne(new QueryWrapper<OrderPayDeposit>().eq("out_trade_no",outTradeNo));
    }

    @Override
    public List<OrderPayDeposit> getListByUid(String uid) {
        return list(new QueryWrapper<OrderPayDeposit>().eq("uid",uid).eq("channel_id", AppParamUtil.getChannelId()).orderByDesc("pay_time"));
    }

    @Override
    public OrderPayDeposit getByOrderId(String orderId) {
        return getOne(new QueryWrapper<OrderPayDeposit>().eq("order_id",orderId));
    }
}
