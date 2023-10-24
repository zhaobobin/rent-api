package com.rent.service.components.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rent.common.constant.AlipayCallback;
import com.rent.common.dto.order.DepositCallBackRequest;
import com.rent.common.dto.order.resquest.PaySettlementCallBackRequest;
import com.rent.common.enums.components.EnumAliPayStatus;
import com.rent.common.enums.components.EnumTradeType;
import com.rent.common.enums.order.EnumOrderType;
import com.rent.common.enums.order.PaymentMethod;
import com.rent.common.util.AsyncUtil;
import com.rent.common.util.GsonUtil;
import com.rent.dao.components.*;
import com.rent.dao.order.UserOrdersDao;
import com.rent.exception.HzsxBizException;
import com.rent.model.components.*;
import com.rent.model.order.UserOrders;
import com.rent.service.components.AliPayCapitalNotifyService;
import com.rent.service.components.AliPayCapitalService;
import com.rent.service.components.AsyncTaskService;
import com.rent.service.order.*;
import com.rent.service.product.OrderPayDepositService;
import com.rent.service.product.ShopFundService;
import com.rent.util.DateUtil;
import com.rent.util.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author xiaoyao
 * @version V1.0
 * @date 2020-6-28 下午 4:24:04
 * @since 1.0
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class AliPayCapitalNotifyServiceImpl implements AliPayCapitalNotifyService {

    private final AlipayFreezeDao alipayFreezeDao;
    private final AlipayTradeSerialDao alipayTradeSerialDao;
    private final AsyncTaskService asyncTaskService;
    private final AlipayTradePayDao aliPayTradePayDao;
    private final AlipayUnfreezeDao alipayUnfreezeDao;
    private final AlipayTradePagePayDao alipayTradePagePayDao;
    private final AliPayCapitalService aliPayCapitalService;
    private final AlipayTradeCreateDao alipayTradeCreateDao;
    private final UserOrdersDao userOrdersDao;
    private final UserOrdersService userOrdersService;
    private final OrderByStagesService orderByStagesService;
    private final UserOrderBuyOutService userOrderBuyOutService;
    private final OrderSettlementService orderSettlementService;
    private final OrderPayDepositService orderPayDepositService;
    private final UserOrderReletService userOrderReletService;
    private final ShopFundService shopFundService;

    @Override
    public void freezeCallBack(String authNo, String outOrderNo, String operationId, String outRequestNo, String amount,
        String status, String payerUserId, String gmtTrans, String preAuthType, String creditAmount,String fundAmount) {

        AlipayTradeSerial alipayTradeSerial = alipayTradeSerialDao.selectOneBySerialNo(outRequestNo,EnumAliPayStatus.PAYING);
        if (null == alipayTradeSerial || !EnumAliPayStatus.PAYING.equals(alipayTradeSerial.getStatus())) {
            log.error("订单编号：{} 请求流水：{}，交易不存在或已完成.直接返回。",null != alipayTradeSerial ? alipayTradeSerial.getOrderId() : "", outRequestNo);
            return;
        }
        AlipayFreeze alipayFreeze = alipayFreezeDao.selectOneByOutRequestNo(outRequestNo);
        if (AlipayCallback.SUCCESS.equals(status)) {
            //授权成功
            log.info("订单编号：{}请求流水：{}授权成功 开始处理", outOrderNo, outRequestNo);
            alipayFreeze.setAuthNo(authNo);
            alipayFreeze.setOperationId(operationId);
            alipayFreeze.setPayerUserId(payerUserId);
            alipayFreeze.setStatus(EnumAliPayStatus.SUCCESS);
            alipayFreeze.setGmtTrans(DateUtil.string2Date(gmtTrans, DateUtil.DATETIME_FORMAT_1));
            alipayFreeze.setPreAuthType(preAuthType);
            alipayFreeze.setCreditAmount(StringUtil.isEmpty(creditAmount) ? BigDecimal.ZERO : new BigDecimal(creditAmount));
            alipayFreeze.setFundAmount(StringUtil.isEmpty(fundAmount) ? BigDecimal.ZERO : new BigDecimal(fundAmount));
            alipayFreeze.setUpdateTime(new Date());
            alipayFreezeDao.updateById(alipayFreeze);
            alipayTradeSerial.setStatus(EnumAliPayStatus.SUCCESS);
            alipayTradeSerial.setUserId(payerUserId);
            alipayTradeSerial.setUpdateTime(new Date());
            alipayTradeSerialDao.updateById(alipayTradeSerial);
            //更新订单状态，支付中
            userOrdersService.userOrdersPaying(alipayTradeSerial.getOrderId(), payerUserId);
            //异步执行预授权转支付
            switch (alipayTradeSerial.getTradeType()) {
                case GENERAL_PLACE_ORDER:
                    userOrdersService.paySuccessUserOrderHandle(EnumOrderType.GENERAL_ORDER, alipayFreeze.getOrderId(), null,DateUtil.string2Date(gmtTrans), null, null, payerUserId, PaymentMethod.ZFB);
                    break;
                case RELET_PLACE_ORDER:
                    AsyncUtil.runAsync(() -> asyncTaskService.alipayTradePay(alipayFreeze, alipayTradeSerial.getTradeType()), "续租订单预授权转支付");
                    break;
            }
        }
    }

    @Override
    public void aliPayCallBack(String tradeNo, String outTradeNo, String buyerLogonId, String totalAmount,
        String receiptAmount, String gmtPayment, String fundBillList, String buyerUserId, String tradeStatus) {
        try {
            //睡眠5s，如果不睡眠会导致事务未提交回调过快查询不到记录
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (AlipayCallback.TRADE_SUCCESS.equals(tradeStatus)) {
            //支付成功处理业务
            log.info(" 预授权转支付成功回调 修改订单状态");
            AlipayTradeSerial alipayTradeSerial = alipayTradeSerialDao.selectOneBySerialNo(tradeNo, EnumAliPayStatus.PAYING);
            if (null == alipayTradeSerial || !EnumAliPayStatus.PAYING.equals(alipayTradeSerial.getStatus())) {
                log.error("订单编号：{} 请求流水：{}，交易不存在或已完成.直接返回。",null != alipayTradeSerial ? alipayTradeSerial.getOrderId() : "", tradeNo);
                return;
            }
            log.info("开始更新支付状态");
            AlipayTradePay alipayTradePay = aliPayTradePayDao.getOneByTradeNo(tradeNo, EnumAliPayStatus.PAYING);
            alipayTradeSerial.setStatus(EnumAliPayStatus.SUCCESS);
            alipayTradeSerial.setUserId(buyerUserId);
            alipayTradeSerial.setUpdateTime(new Date());
            alipayTradePay.setStatus(EnumAliPayStatus.SUCCESS);
            alipayTradePay.setUpdateTime(new Date());
            alipayTradePay.setAmount(new BigDecimal(totalAmount));
            alipayTradeSerialDao.updateById(alipayTradeSerial);
            aliPayTradePayDao.updateById(alipayTradePay);
            log.info("结束 新增支付回调记录");
            UserOrders userOrdersDto = userOrdersDao.selectOneByOrderId(alipayTradePay.getOrderId());
            //更新订单状态，代发货，分期订单状态 已支付 发送钉钉消息
            EnumOrderType orderType = null;
            switch (alipayTradeSerial.getTradeType()) {
                case GENERAL_PLACE_ORDER:
                    orderType = EnumOrderType.GENERAL_ORDER;
                    userOrdersService.paySuccessUserOrderHandle(orderType, userOrdersDto.getOrderId(), tradeNo,DateUtil.string2Date(gmtPayment), outTradeNo,new BigDecimal(totalAmount), buyerUserId, PaymentMethod.ZFB);
                    break;
                case RELET_PLACE_ORDER:
                    userOrderReletService.payedReletOrderSuccess(alipayTradeSerial.getOrderId(),tradeNo,DateUtil.string2Date(gmtPayment),outTradeNo, new BigDecimal(totalAmount),buyerUserId,null);
                    break;
            }
        }
    }


    @Override
    public void alipayTradePagePayCallback(String serialNo, String tradeNo, String outTradeNo, String buyerLogonId,
        String totalAmount, String receiptAmount, String gmtPayment, String fundBillList, String buyerUserId,
        String tradeStatus) {
        if (AlipayCallback.TRADE_SUCCESS.equals(tradeStatus)) {
            //支付成功处理业务
            log.info("PAGE支付成功回调 修改订单状态");
            AlipayTradeSerial alipayTradeSerial = alipayTradeSerialDao.selectOneBySerialNo(serialNo,EnumAliPayStatus.PAYING);
            if (null == alipayTradeSerial || !EnumAliPayStatus.PAYING.equals(alipayTradeSerial.getStatus())) {
                log.error("订单编号：{} 请求流水：{}，交易不存在或已完成.直接返回。",null != alipayTradeSerial ? alipayTradeSerial.getOrderId() : "", serialNo);
                return;
            }
            log.info("App支付交易类型为:{}开始更新支付状态", alipayTradeSerial.getTradeType().getDescription());
            //更新流水状态并更新交易号和流水号
            AlipayTradePagePay tradePagePay = alipayTradePagePayDao.getOne(new QueryWrapper<>(
                AlipayTradePagePay.builder()
                    .tradeNo(serialNo)
                    .build()));
            alipayTradeSerial.setStatus(EnumAliPayStatus.SUCCESS);
            alipayTradeSerial.setSerialNo(tradeNo);
            alipayTradeSerial.setUserId(buyerUserId);
            alipayTradeSerial.setUpdateTime(new Date());
            tradePagePay.setStatus(EnumAliPayStatus.SUCCESS);
            tradePagePay.setUpdateTime(new Date());
            tradePagePay.setTradeNo(tradeNo);
            tradePagePay.setAmount(new BigDecimal(totalAmount));
            alipayTradeSerialDao.updateById(alipayTradeSerial);
            alipayTradePagePayDao.updateById(tradePagePay);
            //根据不同的交易回调响应的业务
            switch (alipayTradeSerial.getTradeType()) {
                case SHOP_RECHARGE:
                    shopFundService.rechargeCallBack(outTradeNo,tradeNo,buyerUserId);
                    break;
                default:
                    throw new HzsxBizException("999999", "不支持的交易类型", this.getClass());
            }
        } else if (AlipayCallback.TRADE_CLOSED.equals(tradeStatus)) {
            Date now = new Date();
            AlipayTradePagePay tradePagePay = alipayTradePagePayDao.getOne(new QueryWrapper<>(
                AlipayTradePagePay.builder()
                    .tradeNo(serialNo)
                    .build()));
            if (tradePagePay == null) {
                tradePagePay = alipayTradePagePayDao.getOne(new QueryWrapper<>(AlipayTradePagePay.builder()
                    .tradeNo(tradeNo)
                    .build()));
            }
            tradePagePay.setStatus(EnumAliPayStatus.CANCEL);
            tradePagePay.setUpdateTime(now);
            alipayTradePagePayDao.updateById(tradePagePay);
            AlipayTradeSerial alipayTradeSerial = alipayTradeSerialDao.selectOneBySerialNo(serialNo);
            if (alipayTradeSerial == null) {
                alipayTradeSerial = alipayTradeSerialDao.selectOneBySerialNo(tradeNo);
            }
            alipayTradeSerial.setStatus(EnumAliPayStatus.CANCEL);
            alipayTradeSerial.setUpdateTime(now);
            alipayTradeSerialDao.updateById(alipayTradeSerial);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void aliPayTradeCreateCallBack(String serialNo, String tradeNo, String outTradeNo, String buyerLogonId,
        String totalAmount, String receiptAmount, String gmtPayment, String fundBillList, String buyerUserId,
        String tradeStatus) {

        if (AlipayCallback.TRADE_SUCCESS.equals(tradeStatus)) {
            //支付成功处理业务
            log.info("App支付成功回调 修改订单状态");
            AlipayTradeSerial alipayTradeSerial = alipayTradeSerialDao.selectOneBySerialNo(serialNo,EnumAliPayStatus.PAYING);
            if (null == alipayTradeSerial || !EnumAliPayStatus.PAYING.equals(alipayTradeSerial.getStatus())) {
                log.error("订单编号：{} 请求流水：{}，交易不存在或已完成.直接返回。", null != alipayTradeSerial ? alipayTradeSerial.getOrderId() : "", serialNo);
                return;
            }
            log.info("App支付交易类型为:{}开始更新支付状态", alipayTradeSerial.getTradeType().getDescription());
            //更新流水状态并更新交易号和流水号
            AlipayTradeCreate alipayTradeCreate = alipayTradeCreateDao.getByTradeNo(serialNo);
            alipayTradeSerial.setStatus(EnumAliPayStatus.SUCCESS);
            alipayTradeSerial.setSerialNo(tradeNo);
            alipayTradeSerial.setUserId(buyerUserId);
            alipayTradeSerial.setUpdateTime(new Date());
            alipayTradeCreate.setStatus(EnumAliPayStatus.SUCCESS);
            alipayTradeCreate.setUpdateTime(new Date());
            alipayTradeCreate.setTradeNo(tradeNo);
            alipayTradeCreate.setAmount(new BigDecimal(totalAmount));
            alipayTradeSerialDao.updateById(alipayTradeSerial);
            alipayTradeCreateDao.updateById(alipayTradeCreate);
            //创建公共参数
            //根据不同的交易回调响应的业务
            switch (alipayTradeSerial.getTradeType()) {
                case USER_PAY_BILL:
                    //当前期数账单是否存在批扣失败的订单，存在就同步订单信息为完成
                    List<AlipayTradeSerial> failTradeSerial = alipayTradeSerialDao.list(
                        new QueryWrapper<AlipayTradeSerial>().eq("order_id", alipayTradeSerial.getOrderId())
                            .eq("trade_type", EnumTradeType.BILL_WITHHOLD)
                            .in("status", Arrays.asList(EnumAliPayStatus.FAILED, EnumAliPayStatus.PAYING)));

                    List<String> repayPeriodList = GsonUtil.jsonToList(alipayTradeSerial.getPeriod(), String.class);
                    if (CollectionUtil.isNotEmpty(failTradeSerial)) {
                        failTradeSerial.stream()
                            .collect(Collectors.collectingAndThen(Collectors.toCollection(
                                () -> new TreeSet<>(Comparator.comparing(AlipayTradeSerial::getPeriod))),
                                ArrayList::new))
                            .forEach(a -> {
                                List<String> failList = GsonUtil.jsonToList(a.getPeriod(), String.class);
                                if (repayPeriodList.contains(failList.get(0))) {
                                    aliPayCapitalService.alipayTradeOrderInfoSync(alipayTradeSerial.getOrderId(),a.getSerialNo(), tradeNo, "CREDIT_AUTH",AlipayCallback.TRADE_SYNC_BIZ_INFO_COMPLETE);
                                }
                            });
                    }
                    orderByStagesService.payedOrderByStages(alipayTradeSerial.getOrderId(), tradeNo, outTradeNo,
                             GsonUtil.jsonToList(alipayTradeSerial.getPeriod(), String.class), new BigDecimal(totalAmount), EnumAliPayStatus.SUCCESS,null);
                    break;
                case BUY_OUT:
                    userOrderBuyOutService.buyOutPayedCallBack(outTradeNo,alipayTradeSerial.getOrderId(),tradeNo,EnumAliPayStatus.SUCCESS);
                    break;
                case SETTLEMENT_PAY:
                    PaySettlementCallBackRequest request = PaySettlementCallBackRequest.builder()
                            .orderId(alipayTradeSerial.getOrderId())
                            .payStatus(EnumAliPayStatus.SUCCESS)
                            .outTradeNo(outTradeNo)
                            .tradeNo(tradeNo)
                            .paymentTime(DateUtil.string2Date(gmtPayment))
                            .build();
                    orderSettlementService.paySettlementCallBack(request);
                    break;
                case FIRST_PERIOD_RELET:
                    userOrderReletService.payedReletOrderSuccess(alipayTradeSerial.getOrderId(),tradeNo,DateUtil.string2Date(gmtPayment),outTradeNo,new BigDecimal(totalAmount),buyerUserId,null);
                    break;
                case DEPOSIT_PAY:
                    DepositCallBackRequest depositCallBackRequest = new DepositCallBackRequest();
                    depositCallBackRequest.setOrderId(alipayTradeSerial.getOrderId());
                    depositCallBackRequest.setTradeNo(tradeNo);
                    depositCallBackRequest.setOutTradeNo(outTradeNo);
                    depositCallBackRequest.setBuyerUserId(buyerUserId);
                    orderPayDepositService.orderDepositPayCallBack(depositCallBackRequest);
                    break;
                default:
                    throw new HzsxBizException("999999", "不支持的交易类型", this.getClass());
            }
        } else if (AlipayCallback.TRADE_CLOSED.equals(tradeStatus)) {
            Date now = new Date();
            AlipayTradeCreate alipayTradeCreate = alipayTradeCreateDao.getByTradeNo(serialNo);
            if (alipayTradeCreate == null) {
                alipayTradeCreate = alipayTradeCreateDao.getByTradeNo(tradeNo);
            }
            alipayTradeCreate.setStatus(EnumAliPayStatus.CANCEL);
            alipayTradeCreate.setUpdateTime(now);
            alipayTradeCreateDao.updateById(alipayTradeCreate);
            AlipayTradeSerial alipayTradeSerial = alipayTradeSerialDao.selectOneBySerialNo(serialNo);
            if (alipayTradeSerial == null) {
                alipayTradeSerial = alipayTradeSerialDao.selectOneBySerialNo(tradeNo);
            }
            alipayTradeSerial.setStatus(EnumAliPayStatus.CANCEL);
            alipayTradeSerial.setUpdateTime(now);
            alipayTradeSerialDao.updateById(alipayTradeSerial);
        }
    }

    @Override
    public void stageOrderAlipayCallback(String outTradeNo, String tradeStatus, String buyerUserId, String fundBillList,
        String gmtPayment, String totalAmount, String receiptAmount, String tradeNo) {
        try {
            //睡眠5s，如果不睡眠会导致事务未提交回调过快查询不到记录
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("【分期租金-冻结转支付回调】- 开始处理,outTradeNo={},tradeStatus={}", outTradeNo, tradeStatus);
        AlipayTradeSerial alipayTradeSerial = alipayTradeSerialDao.selectOneBySerialNo(tradeNo,EnumAliPayStatus.PAYING);
        if (null == alipayTradeSerial || !EnumAliPayStatus.PAYING.equals(alipayTradeSerial.getStatus())) {
            log.error("订单编号：{} 请求流水：{}，交易不存在或已完成.直接返回。",null != alipayTradeSerial ? alipayTradeSerial.getOrderId() : "", tradeNo);
            return;
        }
        AlipayTradePay alipayTradePay = aliPayTradePayDao.getOneByTradeNo(tradeNo, EnumAliPayStatus.PAYING);
        alipayTradeSerial.setStatus(EnumAliPayStatus.SUCCESS);
        alipayTradeSerial.setUpdateTime(new Date());
        alipayTradePay.setStatus(EnumAliPayStatus.SUCCESS);
        alipayTradePay.setUpdateTime(new Date());
        alipayTradePay.setAmount(new BigDecimal(totalAmount));
        alipayTradeSerialDao.updateById(alipayTradeSerial);
        aliPayTradePayDao.updateById(alipayTradePay);

        log.info("【分期租金-冻结转支付回调】- 保存回调记录,outTradeNo={}", new Object[] {outTradeNo});
        //创建公共参数
        if (AlipayCallback.TRADE_SUCCESS.equals(tradeStatus)) {
            log.info("【分期租金-冻结转支付回调】- 请求订单系统-更新分期订单状态,outTradeNo={}", outTradeNo);
            orderByStagesService.payedOrderByStages(alipayTradeSerial.getOrderId(), tradeNo, outTradeNo, GsonUtil.jsonToList(alipayTradeSerial.getPeriod(), String.class),new BigDecimal(receiptAmount), EnumAliPayStatus.SUCCESS, PaymentMethod.ZFB);
            //失败逾期判断
            int failedCount = aliPayTradePayDao.getFailedCountByOutTradeNo(outTradeNo);
            if(failedCount>0){
                log.info("【分期租金-冻结转支付回调】- 支付成功-同步订单完成信息到支付宝,outTradeNo={}", outTradeNo);
                aliPayCapitalService.alipayTradeOrderInfoSync(null, tradeNo, tradeNo, "CREDIT_AUTH", AlipayCallback.TRADE_SYNC_BIZ_INFO_COMPLETE);
            }
        } else {
            orderByStagesService.payedOrderByStages(alipayTradeSerial.getOrderId(), null, null, GsonUtil.jsonToList(alipayTradeSerial.getPeriod(), String.class), null, EnumAliPayStatus.CANCEL, PaymentMethod.ZFB);
            //失败逾期判断
            int failedCount = aliPayTradePayDao.getFailedCountByOutTradeNo(outTradeNo);
            log.info("【分期租金-冻结转支付回调】- 支付失败-outTradeNo={},失败次数={}", new Object[] {outTradeNo, failedCount});
            if (failedCount > AlipayCallback.TRADE_FAILED_SYNC_TIMES) {
                log.info("【分期租金-冻结转支付回调】- 支付失败-同步逾期信息到支付宝,outTradeNo={},失败次数={}", new Object[] {outTradeNo, failedCount});
                aliPayCapitalService.alipayTradeOrderInfoSync(null, tradeNo, tradeNo, "CREDIT_AUTH", AlipayCallback.TRADE_SYNC_BIZ_INFO_VIOLATED);
            }
        }
    }

    @Override
    public void alipayUnFreezeCallBack(String authNo, String outOrderNo, String operationId, String outRequestNo,String amount, String status) {
        log.info("解冻回调 成功 - 开始处理，outOrderN0:{},outRequestNo:{}", outOrderNo, outRequestNo);
        AlipayTradeSerial alipayTradeSerial = alipayTradeSerialDao.selectOneBySerialNo(outRequestNo, EnumAliPayStatus.PAYING);
        if (null == alipayTradeSerial || !EnumAliPayStatus.PAYING.equals(alipayTradeSerial.getStatus())) {
            log.error("订单编号：{} 请求流水：{}，交易不存在或已完成.直接返回。",null != alipayTradeSerial ? alipayTradeSerial.getOrderId() : "", outRequestNo);
            return;
        }
        if (AlipayCallback.SUCCESS.equals(status)) {
            Date now = new Date();
            AlipayUnfreeze alipayUnfreeze = alipayUnfreezeDao.getByUnfreezeRequestNo(outRequestNo);
            alipayTradeSerial.setUpdateTime(now);
            alipayTradeSerial.setStatus(EnumAliPayStatus.SUCCESS);
            alipayUnfreeze.setUpdateTime(now);
            alipayUnfreeze.setStatus(EnumAliPayStatus.SUCCESS);
            alipayTradeSerialDao.updateById(alipayTradeSerial);
            alipayUnfreezeDao.updateById(alipayUnfreeze);
        }
    }

}
