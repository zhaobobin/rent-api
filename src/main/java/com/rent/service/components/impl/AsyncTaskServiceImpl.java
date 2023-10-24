package com.rent.service.components.impl;

import com.alipay.api.response.AlipayTradeQueryResponse;
import com.rent.common.dto.components.request.AlipayTradePayQueryRequest;
import com.rent.common.dto.components.response.AliPayTradePayResponse;
import com.rent.common.enums.components.EnumAliPayStatus;
import com.rent.common.enums.components.EnumTradeType;
import com.rent.common.enums.order.EnumOrderCloseType;
import com.rent.common.util.GsonUtil;
import com.rent.config.outside.AliPayNotifyFactory;
import com.rent.dao.components.AlipayTradePayDao;
import com.rent.dao.components.AlipayTradeSerialDao;
import com.rent.dao.order.UserOrderCashesDao;
import com.rent.dao.order.UserOrdersDao;
import com.rent.model.components.AlipayFreeze;
import com.rent.model.components.AlipayTradePay;
import com.rent.model.components.AlipayTradeSerial;
import com.rent.model.order.UserOrderCashes;
import com.rent.model.order.UserOrders;
import com.rent.service.components.AliPayCapitalService;
import com.rent.service.components.AsyncTaskService;
import com.rent.service.order.UserOrdersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class AsyncTaskServiceImpl implements AsyncTaskService {

    private final UserOrdersDao userOrdersDao;
    private final AliPayCapitalService aliPayCapitalService;
    private final AlipayTradeSerialDao alipayTradeSerialDao;
    private final AlipayTradePayDao alipayTradePayDao;
    private final UserOrderCashesDao userOrderCashesDao;
    private final UserOrdersService userOrdersService;

    // @Async
    @Override
    public void alipayTradePay(AlipayFreeze alipayFreeze, EnumTradeType tradeType) {
        try {
            log.info("开始进行冻结转支付操作，冻结信息 ： {}", GsonUtil.objectToJsonString(alipayFreeze));
            UserOrders userOrders = userOrdersDao.selectOneByOrderId(alipayFreeze.getOrderId());
            UserOrderCashes userOrderCashes = userOrderCashesDao.selectOneByOrderId(alipayFreeze.getOrderId());
            if (userOrderCashes == null) {
                log.info("轮询支付宝 异常userOrderCashes 是null");
            } else {
                log.info("轮询支付宝 异常userOrderCashes 正常");
            }
            BigDecimal totalAmount = userOrderCashes.getFreezePrice().subtract(userOrderCashes.getDeposit());
            for (int i = 0; i < 6; i++) {
                try {
                    log.info(" 第 " + i + "次");
                    //做转支付处理 如果成功就推出如果是 20次失败关单解冻处理
                    AliPayTradePayResponse resp = aliPayCapitalService.aliPayTradePay(alipayFreeze.getOrderId(),
                        userOrders.getRequestNo(), alipayFreeze.getAuthNo(),
                        "冻结转支付流水号订单 :" + alipayFreeze.getOrderId(), AliPayNotifyFactory.aliTradePayCallbackUrl, totalAmount,
                        alipayFreeze.getPayerUserId(), Collections.singletonList("1"), tradeType);
                    switch (resp.getTradeResult()) {
                        case FAILED:
                            log.info("启动了线程睡觉30秒");
                            Thread.sleep(30 * 1000);
                            break;
                        case SUCCESS:
                            log.info("线程 支付轮询成功开始退出 更新订单");
                            return;
                        case LIMIT_AMOUNT:
                            //轮询交易结果
                            AlipayTradePayQueryRequest request = new AlipayTradePayQueryRequest();
                            request.setChannelId(userOrders.getChannelId());
                            request.setTradeNo(resp.getTradeNo());
                            request.setOutTradeNo(resp.getOutTradeNo());
                            request.setOrderId(userOrders.getOrderId());
                            //轮询支付结果
                            int count = 20;
                            while (count-- > 0) {
                                try {
                                    AlipayTradeQueryResponse alipayTradeQueryResponse = aliPayCapitalService.alipayTradeQuery(request);
                                    if (!alipayTradeQueryResponse.isSuccess()) {
                                        log.info("查询失败继续查询");
                                    } else {
                                        if ("WAIT_BUYER_PAY".equals(alipayTradeQueryResponse.getTradeStatus())) {
                                            //用户支付宝 超限额 风控未过
                                            log.info("处理中，等待1s继续查询" + alipayTradeQueryResponse.getBody());
                                            Thread.sleep(2000);
                                        } else if ("TRADE_SUCCESS".equals(alipayTradeQueryResponse.getTradeStatus())) {
                                            return;
                                        } else if ("TRADE_CLOSED".equals(alipayTradeQueryResponse.getTradeStatus())) {
                                            break;
                                        }
                                    }
                                } catch (InterruptedException e) {
                                    log.error("轮序订单状态线程睡眠异常");
                                }
                            }
                            //将流水修改为失败状态
                            AlipayTradeSerial alipayTradeSerial = alipayTradeSerialDao.selectOneBySerialNo(resp.getTradeNo(), EnumAliPayStatus.PAYING);
                            AlipayTradePay oneByTradeNo = alipayTradePayDao.getOneByTradeNo(resp.getTradeNo(), EnumAliPayStatus.PAYING);
                            if (null!= alipayTradeSerial) {
                                alipayTradeSerial.setStatus(EnumAliPayStatus.FAILED);
                                alipayTradeSerial.setUpdateTime(new Date());
                                alipayTradeSerialDao.updateById(alipayTradeSerial);
                            }
                            if (null != oneByTradeNo) {
                                oneByTradeNo.setStatus(EnumAliPayStatus.FAILED);
                                oneByTradeNo.setUpdateTime(new Date());
                                alipayTradePayDao.updateById(oneByTradeNo);
                            }
                            break;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("启动了线程  " + i + "" + alipayFreeze.getOutRequestNo());
            }
            //6次还没成功  执行关单
            //设置线程上下文
            userOrdersService.payFailedCloseOrder(alipayFreeze.getOrderId(), EnumOrderCloseType.PAY_FAILED,EnumOrderCloseType.PAY_FAILED.getDescription());

        } catch (Exception e) {
            log.info("异步轮询异常  113 " + e.getMessage());
        }
    }
}
