package com.rent.dao.order;

import com.rent.config.mybatis.IBaseDao;
import com.rent.model.order.OrderPayDeposit;

import java.math.BigDecimal;
import java.util.List;

/**
 * 用户支付押金表
 * @author zhaowenchao
 */
public interface OrderPayDepositDao extends IBaseDao<OrderPayDeposit> {

    /**
     * 初始化押金记录
     * @param orderId
     * @param totalDeposit
     * @param amount
     * @param channelId
     * @param uid
     */
    void initDepositRecord(String orderId, BigDecimal totalDeposit,BigDecimal amount, String channelId, String uid);

    /**
     * 获取需要支付的押金列表
     * @param orderId
     * @return
     */
    OrderPayDeposit getWaitPaymentDeposit(String orderId);

    /**
     * 根据外部交易号获取押金记录
     * @param outTradeNo
     * @return
     */
    OrderPayDeposit getByOutTradeNo(String outTradeNo);


    /**
     * 获取用户uid的押金列表
     * @param uid
     * @return
     */
    List<OrderPayDeposit> getListByUid(String uid);

    /**
     *
     * @param orderId
     * @return
     */
    OrderPayDeposit getByOrderId(String orderId);
}
