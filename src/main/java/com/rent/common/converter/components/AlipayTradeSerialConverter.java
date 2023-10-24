package com.rent.common.converter.components;

import com.rent.common.enums.components.EnumAliPayStatus;
import com.rent.common.enums.components.EnumPayType;
import com.rent.common.enums.components.EnumTradeType;
import com.rent.model.components.AlipayTradeSerial;
import com.rent.util.AppParamUtil;

import java.math.BigDecimal;
import java.util.Date;


public class AlipayTradeSerialConverter {

    public static AlipayTradeSerial assemblyAlipayTradeSerial(String outTradeNo, String outRequestNo, Date now,
                                                              String orderId, String uid, BigDecimal amount, EnumPayType payType, EnumTradeType tradeType) {
        AlipayTradeSerial tradeSerial = new AlipayTradeSerial();
        tradeSerial.setOrderId(orderId);
        tradeSerial.setUid(uid);
        tradeSerial.setOutOrderNo(outTradeNo);
        tradeSerial.setSerialNo(outRequestNo);
        tradeSerial.setAmount(amount);
        tradeSerial.setPayType(payType);
        tradeSerial.setTradeType(tradeType);
        tradeSerial.setStatus(EnumAliPayStatus.PAYING);
        tradeSerial.setChannelId(AppParamUtil.getChannelId());
        tradeSerial.setCreateTime(now);
        tradeSerial.setUpdateTime(now);
        return tradeSerial;
    }
}