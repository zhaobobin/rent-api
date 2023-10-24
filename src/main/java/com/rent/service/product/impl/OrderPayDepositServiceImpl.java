package com.rent.service.product.impl;

import com.rent.common.dto.components.request.AliPayTradeCreateRequest;
import com.rent.common.dto.components.request.AlipayOrderRefundRequest;
import com.rent.common.dto.components.response.AliPayTradeCreateResponse;
import com.rent.common.dto.order.DepositCallBackRequest;
import com.rent.common.dto.order.DepositOrderLogDto;
import com.rent.common.dto.order.OpeDepositOrderPageDto;
import com.rent.common.dto.order.response.OrderByStagesPayResponse;
import com.rent.common.dto.user.UserThirdInfoDto;
import com.rent.common.enums.components.EnumTradeType;
import com.rent.common.enums.order.EnumOrderPayDepositStatus;
import com.rent.common.enums.order.EnumOrderStatus;
import com.rent.common.enums.order.OrderDepositWithdrawStatusSet;
import com.rent.dao.order.OrderPayDepositDao;
import com.rent.dao.order.OrderPayDepositLogDao;
import com.rent.dao.order.UserOrdersDao;
import com.rent.dao.user.UserThirdInfoDao;
import com.rent.exception.HzsxBizException;
import com.rent.model.order.OrderPayDeposit;
import com.rent.model.order.UserOrders;
import com.rent.service.components.AliPayCapitalService;
import com.rent.service.product.OrderPayDepositService;
import com.rent.util.RandomUtil;
import com.rent.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * @author zhaowenchao
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class OrderPayDepositServiceImpl implements OrderPayDepositService {

    private final OrderPayDepositDao orderPayDepositDao;
    private final UserOrdersDao userOrdersDao;
    private final OrderPayDepositLogDao orderPayDepositLogDao;
    private final AliPayCapitalService aliPayCapitalService;
    private final UserThirdInfoDao userThirdInfoDao;


    @Override
    public OrderByStagesPayResponse orderDepositPay(String orderId) {

        String lockKey = "orderDepositPay::"+orderId;
        if(!RedisUtil.tryLock(lockKey,12)){
            throw new HzsxBizException("-1","支付中，请稍后再试");
        }

        OrderPayDeposit orderPayDeposit = orderPayDepositDao.getWaitPaymentDeposit(orderId);
        BigDecimal totalAmount = orderPayDeposit.getAmount();
        String outTradeNo = "AAP" + orderId + "_" + RandomUtil.randomNumbers(4);
        String subject = "订单:" + orderId + " | 押金:" + totalAmount;
        orderPayDeposit.setOutTradeNo(outTradeNo);
        orderPayDepositDao.updateById(orderPayDeposit);

        //调用支付
        UserThirdInfoDto userThirdInfoDto = userThirdInfoDao.getByUid(orderPayDeposit.getUid());
        AliPayTradeCreateRequest aliPayTradeCreateRequest = new AliPayTradeCreateRequest();
        aliPayTradeCreateRequest.setOutTradeNo(outTradeNo);
        aliPayTradeCreateRequest.setTotalAmount(totalAmount);
        aliPayTradeCreateRequest.setSubject(subject);
        aliPayTradeCreateRequest.setOrderId(orderId);
        aliPayTradeCreateRequest.setTradeType(EnumTradeType.DEPOSIT_PAY);
        aliPayTradeCreateRequest.setUid(orderPayDeposit.getUid());
        aliPayTradeCreateRequest.setBuyerId(userThirdInfoDto.getThirdId());
        AliPayTradeCreateResponse aliPayTradeCreateResponse = aliPayCapitalService.alipayTradeCreate(aliPayTradeCreateRequest);

        return OrderByStagesPayResponse.builder()
                .payUrl(aliPayTradeCreateResponse.getTradeNo())
                .serialNo(aliPayTradeCreateResponse.getSerialNo())
                .build();
    }

    @Override
    public void orderDepositPayCallBack(DepositCallBackRequest request) {
        OrderPayDeposit orderPayDeposit = orderPayDepositDao.getByOutTradeNo(request.getOutTradeNo());
        orderPayDeposit.setStatus(EnumOrderPayDepositStatus.PAID);
        orderPayDeposit.setPayTime(new Date());
        orderPayDeposit.setTradeNo(request.getTradeNo());
        orderPayDepositDao.updateById(orderPayDeposit);
    }

    @Override
    public void refund(String uid, String orderId) {
        List<OrderPayDeposit> list =  orderPayDepositDao.getListByUid(uid);
        //todo 记录流水
        if(StringUtils.isNotEmpty(orderId)){
            for (OrderPayDeposit orderPayDeposit : list) {
                if(!orderId.equals(orderPayDeposit.getOrderId())){
                    continue;
                }
                UserOrders userOrders = userOrdersDao.selectOneByOrderId(orderPayDeposit.getOrderId());
                if(!OrderDepositWithdrawStatusSet.withDrawOrderStatusSet.contains(userOrders.getStatus())){
                    throw new HzsxBizException("-1","订单状态不允许退款");
                }
                if(!orderPayDeposit.getStatus().equals(EnumOrderPayDepositStatus.PAID)){
                    throw new HzsxBizException("-1","订单状态不允许退款");
                }
                AlipayOrderRefundRequest refundRequest = new AlipayOrderRefundRequest();
                refundRequest.setRefundAmount(orderPayDeposit.getAmount());
                refundRequest.setTradeNo(orderPayDeposit.getTradeNo());
                refundRequest.setOutTradeNo(orderPayDeposit.getOutTradeNo());
                refundRequest.setRefundReason("押金提现");
                refundRequest.setOrderId(orderPayDeposit.getOrderId());
                refundRequest.setChannelId(orderPayDeposit.getChannelId());
                refundRequest.setUid(orderPayDeposit.getUid());
                refundRequest.setTradeType(EnumTradeType.DEPOSIT_REFUND);
                aliPayCapitalService.alipayTradeRefund(refundRequest);
                //更新押金订单状态
                orderPayDeposit.setStatus(EnumOrderPayDepositStatus.WITHDRAW);
                orderPayDeposit.setRefundTime(new Date());
                orderPayDepositDao.updateById(orderPayDeposit);
                return;
            }
        }else {
            for (OrderPayDeposit orderPayDeposit : list) {
                UserOrders userOrders = userOrdersDao.selectOneByOrderId(orderPayDeposit.getOrderId());
                if(!OrderDepositWithdrawStatusSet.withDrawOrderStatusSet.contains(userOrders.getStatus())){
                    continue;
                }
                if(!orderPayDeposit.getStatus().equals(EnumOrderPayDepositStatus.PAID)){
                    continue;
                }
                AlipayOrderRefundRequest refundRequest = new AlipayOrderRefundRequest();
                refundRequest.setRefundAmount(orderPayDeposit.getAmount());
                refundRequest.setTradeNo(orderPayDeposit.getTradeNo());
                refundRequest.setOutTradeNo(orderPayDeposit.getOutTradeNo());
                refundRequest.setRefundReason("押金提现");
                refundRequest.setOrderId(orderPayDeposit.getOrderId());
                refundRequest.setChannelId(orderPayDeposit.getChannelId());
                refundRequest.setUid(orderPayDeposit.getUid());
                refundRequest.setTradeType(EnumTradeType.DEPOSIT_REFUND);
                aliPayCapitalService.alipayTradeRefund(refundRequest);
                //更新押金订单状态
                orderPayDeposit.setStatus(EnumOrderPayDepositStatus.WITHDRAW);
                orderPayDeposit.setRefundTime(new Date());
                orderPayDepositDao.updateById(orderPayDeposit);
            }
        }
    }

    @Override
    public void forceRefund(String orderId, Long backstageUserId) {
        OrderPayDeposit orderPayDeposit = orderPayDepositDao.getByOrderId(orderId);
        if(orderPayDeposit==null){
            throw new HzsxBizException("-1","未查询到押金支付信息");
        }
        if(!orderPayDeposit.getStatus().equals(EnumOrderPayDepositStatus.PAID)){
            throw new HzsxBizException("-1","押金支付状态错误");
        }
        AlipayOrderRefundRequest refundRequest = new AlipayOrderRefundRequest();
        refundRequest.setRefundAmount(orderPayDeposit.getAmount());
        refundRequest.setTradeNo(orderPayDeposit.getTradeNo());
        refundRequest.setOutTradeNo(orderPayDeposit.getOutTradeNo());
        refundRequest.setRefundReason("押金提现");
        refundRequest.setOrderId(orderPayDeposit.getOrderId());
        refundRequest.setChannelId(orderPayDeposit.getChannelId());
        refundRequest.setUid(orderPayDeposit.getUid());
        refundRequest.setTradeType(EnumTradeType.DEPOSIT_REFUND);
        aliPayCapitalService.alipayTradeRefund(refundRequest);
        //更新押金订单状态
        orderPayDeposit.setStatus(EnumOrderPayDepositStatus.WITHDRAW);
        orderPayDeposit.setRefundTime(new Date());
        orderPayDeposit.setRefundUser(backstageUserId);
        orderPayDepositDao.updateById(orderPayDeposit);
    }

    @Override
    public void updateAmount(String orderId, BigDecimal afterAmount, String backstageUserName, Long backstageUserId) {
        UserOrders userOrders = userOrdersDao.selectOneByOrderId(orderId);
        if(!userOrders.getStatus().equals(EnumOrderStatus.TO_AUDIT)
                && !userOrders.getStatus().equals(EnumOrderStatus.PENDING_DEAL)
                && !userOrders.getStatus().equals(EnumOrderStatus.WAITING_USER_RECEIVE_CONFIRM)){
            throw new HzsxBizException("-1","订单状态不允许修改押金");
        }


        OrderPayDeposit orderPayDeposit = orderPayDepositDao.getByOrderId(orderId);
        BigDecimal originAmount = orderPayDeposit.getAmount();
        if(orderPayDeposit==null){
            throw new HzsxBizException("-1","未查询到押金支付信息");
        }
        if(!orderPayDeposit.getStatus().equals(EnumOrderPayDepositStatus.WAITING_PAYMENT) && orderPayDeposit.getAmount().compareTo(BigDecimal.ZERO)!=0){
            throw new HzsxBizException("-1","押金支付状态错误");
        }
        orderPayDeposit.setAmount(afterAmount);
        if(afterAmount.compareTo(BigDecimal.ZERO)<=0){
            orderPayDeposit.setStatus(EnumOrderPayDepositStatus.WITHDRAW);
        }else{
            orderPayDeposit.setStatus(EnumOrderPayDepositStatus.WAITING_PAYMENT);
        }
        orderPayDepositDao.updateById(orderPayDeposit);
        orderPayDepositLogDao.saveLog(orderId,originAmount,afterAmount,backstageUserId,backstageUserName,null);
    }

    @Override
    public OpeDepositOrderPageDto queryLog(String orderId) {
        OpeDepositOrderPageDto dto = new OpeDepositOrderPageDto();
        OrderPayDeposit orderPayDeposit = orderPayDepositDao.getByOrderId(orderId);
        if(orderPayDeposit==null){
            return dto;
        }

        dto.setTotalDeposit(orderPayDeposit.getTotalDeposit());
        dto.setCreditAmount(orderPayDeposit.getTotalDeposit().subtract(orderPayDeposit.getRiskDeposit()));
        if(orderPayDeposit.getStatus().equals(EnumOrderPayDepositStatus.PAID)){
            dto.setPaidAmount(orderPayDeposit.getAmount());
        }else {
            dto.setPaidAmount(BigDecimal.ZERO);
        }
        dto.setWaitPayAmount(orderPayDeposit.getAmount().subtract(dto.getPaidAmount()));
        dto.setAmount(orderPayDeposit.getAmount());
        List<DepositOrderLogDto> logs = orderPayDepositLogDao.getByOrderId(orderId);
        dto.setLogs(logs);
        return dto;
    }
}
