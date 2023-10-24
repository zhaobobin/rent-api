package com.rent.service.components.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rent.common.enums.components.EnumAliPayStatus;
import com.rent.common.enums.order.PaymentMethod;
import com.rent.common.properties.SuningProperties;
import com.rent.common.util.GsonUtil;
import com.rent.dao.components.AlipayTradeCreateDao;
import com.rent.dao.components.YfbTradePayDao;
import com.rent.dao.components.YfbTradeRefundDao;
import com.rent.dao.components.YfbTradeSerialDao;
import com.rent.dao.order.UserOrdersDao;
import com.rent.dao.user.UserBankCardDao;
import com.rent.model.components.YfbTradePay;
import com.rent.model.components.YfbTradeSerial;
import com.rent.service.components.SuningOpenApiNotifyService;
import com.rent.service.order.OrderByStagesService;
import com.rent.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class SuningOpenApiNotifyServiceImpl implements SuningOpenApiNotifyService {

    private final SuningProperties suningProperties;
    private final UserOrdersDao userOrdersDao;
    private final ProductService productService;
    private final UserBankCardDao userBankCardDao;

    private final OrderByStagesService orderByStagesService;

    private final YfbTradeSerialDao yfbTradeSerialDao;
    private final YfbTradePayDao yfbTradePayDao;
    private final YfbTradeRefundDao yfbTradeRefundDao;


    private final AlipayTradeCreateDao alipayTradeCreateDao;

    @Override
    public void stageOrderCallback(Map<String, String> params) {
        JSONArray orders = (JSONArray) JSONObject.parse(params.get("orders"));
        String payDescription = params.get("payDescription");
        String signature = params.get("signature");
        String responseCode = params.get("responseCode");
        JSONObject order = orders.getJSONObject(0);
        String tradeNo = order.getString("outOrderNo");
        String buyerUserNo = order.getString("buyerUserNo");
        String orderTime = order.getString("orderTime");
        String yfbOrderId = order.getString("orderId");
        BigDecimal orderAmount = new BigDecimal(order.getString("orderAmount")).divide(new BigDecimal(100));
        boolean isSuccess = responseCode.equals("0000");
//        String outTradeNo = tradeNo.split("_")
        // TODO 验签
        try {
            //睡眠5s，如果不睡眠会导致事务未提交回调过快查询不到记录
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("【分期租金-已付宝代扣支付回调】- 开始处理,outTradeNo={},responseCode={}", tradeNo, responseCode);
        YfbTradeSerial yfbTradeSerial = yfbTradeSerialDao.selectOneBySerialNo(tradeNo, EnumAliPayStatus.PAYING);
        if (null == yfbTradeSerial || !EnumAliPayStatus.PAYING.equals(yfbTradeSerial.getStatus())) {
            log.error("订单编号：{} 请求流水：{}，交易不存在或已完成.直接返回。", null != yfbTradeSerial ? yfbTradeSerial.getOrderId() : "", tradeNo);
            return;
        }
        YfbTradePay yfbTradePay = yfbTradePayDao.getOneByTradeNo(tradeNo, EnumAliPayStatus.PAYING);
        yfbTradeSerial.setStatus(EnumAliPayStatus.SUCCESS);
        yfbTradeSerial.setUserId(buyerUserNo);
        yfbTradeSerial.setUpdateTime(new Date());
        yfbTradePay.setStatus(EnumAliPayStatus.SUCCESS);
        yfbTradePay.setUpdateTime(new Date());
        yfbTradePay.setResponse(GsonUtil.objectToJsonString(params));
        yfbTradePay.setAmount(orderAmount);
        yfbTradeSerialDao.updateById(yfbTradeSerial);
        yfbTradePayDao.updateById(yfbTradePay);
        log.info("【分期租金-已付宝代扣支付回调】- 保存回调记录,outTradeNo={}", new Object[]{tradeNo});
        if (isSuccess) {
            log.info("【分期租金-已付宝代扣支付回调】- 请求订单系统-更新分期订单状态,outTradeNo={}", tradeNo);
            orderByStagesService.payedOrderByStages(yfbTradeSerial.getOrderId(), yfbOrderId, tradeNo,
                    GsonUtil.jsonToList(yfbTradeSerial.getPeriod(), String.class), orderAmount,
                    EnumAliPayStatus.SUCCESS, PaymentMethod.YFB);
            // TODO 短信通知
//            //失败逾期判断
//            int failedCount = yfbTradePayDao.getFailedCountByOutTradeNo(outTradeNo);
//            if (failedCount > 0) {
//                log.info("【分期租金-冻结转支付回调】- 支付成功-同步订单完成信息到支付宝,outTradeNo={}", outTradeNo);
//                aliPayCapitalService.alipayTradeOrderInfoSync(null, tradeNo, tradeNo, "CREDIT_AUTH", AlipayCallback.TRADE_SYNC_BIZ_INFO_COMPLETE);
//            }
        } else {
            orderByStagesService.payedOrderByStages(yfbTradeSerial.getOrderId(), null, null,
                    GsonUtil.jsonToList(yfbTradeSerial.getPeriod(), String.class), null,
                    EnumAliPayStatus.CANCEL, PaymentMethod.YFB);
            //失败逾期判断
//            int failedCount = aliPayTradePayDao.getFailedCountByOutTradeNo(outTradeNo);
//            log.info("【分期租金-冻结转支付回调】- 支付失败-outTradeNo={},失败次数={}", new Object[]{outTradeNo, failedCount});
//            if (failedCount > AlipayCallback.TRADE_FAILED_SYNC_TIMES) {
//                log.info("【分期租金-冻结转支付回调】- 支付失败-同步逾期信息到支付宝,outTradeNo={},失败次数={}", new Object[]{outTradeNo, failedCount});
//                aliPayCapitalService.alipayTradeOrderInfoSync(null, tradeNo, tradeNo, "CREDIT_AUTH", AlipayCallback.TRADE_SYNC_BIZ_INFO_VIOLATED);
//            }
        }

    }

    @Override
    public void yfbTradeRefundCallback(Map<String, String> params) {
        log.info("【开始处理退款】:{}", JSON.toJSONString(params));
    }
}
