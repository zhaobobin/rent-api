package com.rent.service.order.impl;

import com.rent.common.dto.components.request.AliPayTradeCreateRequest;
import com.rent.common.dto.components.request.AlipayOrderRefundRequest;
import com.rent.common.dto.components.request.AlipayUnfreezeRequest;
import com.rent.common.dto.components.response.AliPayOperationDetailResponse;
import com.rent.common.dto.components.response.AliPayTradeCreateResponse;
import com.rent.common.enums.components.EnumAliPayStatus;
import com.rent.common.enums.components.EnumTradeType;
import com.rent.dao.components.YfbTradePayDao;
import com.rent.dao.components.YfbTradeSerialDao;
import com.rent.dao.user.UserThirdInfoDao;
import com.rent.model.components.YfbTradePay;
import com.rent.model.components.YfbTradeSerial;
import com.rent.model.order.OrderByStages;
import com.rent.model.order.UserOrders;
import com.rent.model.user.UserThirdInfo;
import com.rent.service.components.AliPayCapitalService;
import com.rent.service.components.SuningOpenApiService;
import com.rent.service.order.CapitalOperateCore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author xiaoyao
 * @version V1.0
 * @date 2020-6-19 14:30
 * @since 1.0
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class CapitalOperateCoreImpl implements CapitalOperateCore {

    private final AliPayCapitalService aliPayCapitalService;
    private final UserThirdInfoDao userThirdInfoDao;

    @Override
    public void alipayUnfreeze(UserOrders userOrders, Integer type, String remark, BigDecimal amount, EnumTradeType tradeType) {
        //查询解冻 进行全额解冻 查询剩余的冻结金额
        AliPayOperationDetailResponse response = aliPayCapitalService.alipayOperationDetailQuery(userOrders.getOrderId(), userOrders.getChannelId());
        if (response == null) {
            return;
        }
        if (type.intValue() == 1) {
            if (Objects.equals("0.00", response.getRestAmount())) {
                return;
            }
            amount = new BigDecimal(response.getRestAmount());
        }

        //解冻
        AlipayUnfreezeRequest unfreezeRequest = new AlipayUnfreezeRequest();
        unfreezeRequest.setOrderId(userOrders.getOrderId());
        unfreezeRequest.setAuthNo(response.getAuthNo());
        unfreezeRequest.setOutRequestNo(response.getOutRequestNo());
        unfreezeRequest.setAmount(amount);
        unfreezeRequest.setRemark(remark);
        unfreezeRequest.setTradeType(tradeType);
        unfreezeRequest.setUid(userOrders.getUid());
        unfreezeRequest.setChannelId(userOrders.getChannelId());
        aliPayCapitalService.alipayOrderUnfreeze(unfreezeRequest);
    }

    /**
     * 退款
     */
    @Override
    public void alipayTradeRefund(OrderByStages orderByStages, UserOrders userOrders, String remark, BigDecimal amount, EnumTradeType tradeType, String uid) {
        AlipayOrderRefundRequest refundRequest = new AlipayOrderRefundRequest();
        refundRequest.setRefundAmount(amount);
        refundRequest.setTradeNo(orderByStages.getTradeNo());
        refundRequest.setOutTradeNo(orderByStages.getOutTradeNo());
        refundRequest.setRefundReason(remark);
        refundRequest.setOrderId(userOrders.getOrderId());
        refundRequest.setChannelId(userOrders.getChannelId());
        refundRequest.setUid(uid);
        refundRequest.setTradeType(tradeType);
        aliPayCapitalService.alipayTradeRefund(refundRequest);
    }

    @Override
    public AliPayTradeCreateResponse alipayTradeCreate(EnumTradeType tradeType, List<String> periodList, String subject, String orderId, String outTradeNo, BigDecimal totalAmount, String uid, String hbPeriodNum, Boolean shopPayHbFee, String productId) {
        UserThirdInfo userThirdInfo = userThirdInfoDao.getByUidAndChannelTypeV1(uid, "ALIPAY");
        AliPayTradeCreateRequest aliPayTradeCreateRequest = new AliPayTradeCreateRequest();
        aliPayTradeCreateRequest.setPeriodList(periodList);
        aliPayTradeCreateRequest.setOutTradeNo(outTradeNo);
        aliPayTradeCreateRequest.setTotalAmount(totalAmount);
        aliPayTradeCreateRequest.setSubject(subject);
        aliPayTradeCreateRequest.setOrderId(orderId);
        aliPayTradeCreateRequest.setTradeType(tradeType);
        aliPayTradeCreateRequest.setHbPeriodNum(hbPeriodNum);
        aliPayTradeCreateRequest.setShopPayHbFee(shopPayHbFee);
        aliPayTradeCreateRequest.setUid(uid);
        aliPayTradeCreateRequest.setBuyerId(userThirdInfo.getThirdId());
        aliPayTradeCreateRequest.setProductId(productId);
        return aliPayCapitalService.alipayTradeCreate(aliPayTradeCreateRequest);
    }
}
