package com.rent.service.components.impl;

import com.alibaba.fastjson.JSONObject;
import com.rent.common.dto.components.request.SendMsgRequest;
import com.rent.common.properties.BuYunProperties;
import com.rent.common.properties.SmsSenderProperties;
import com.rent.common.properties.XunDaProperties;
import com.rent.dao.components.BuyunSmsSendSerialDao;
import com.rent.exception.HzsxBizException;
import com.rent.model.components.BuyunSmsSendSerial;
import com.rent.model.marketing.BuyunSmsTemplate;
import com.rent.service.components.SendBuYunSmsService;
import com.rent.service.components.sms.Sender;
import com.rent.service.components.sms.impl.AliyunSenderImpl;
import com.rent.service.components.sms.impl.BuYunSenderImpl;
import com.rent.service.components.sms.impl.XunDaSenderImpl;
import com.rent.service.marketing.BuYunSmsTemplateService;
import com.rent.util.DateUtil;
import com.rent.util.MD5;
import com.rent.util.OkHttpUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: hzsx-rent-parent
 * @description: 短信更换供应商
 * @author: yr
 * @create: 2020-11-06 11:56
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class SendBuYunSmsServiceImpl implements SendBuYunSmsService {


    private final SmsSenderProperties smsSenderProperties;
    private  final AliyunSenderImpl aliyunSender;
    private  final  BuYunSenderImpl buYunSender;
    private  final  XunDaSenderImpl xunDaSender;

    @Override
    public Boolean componentSend(SendMsgRequest sendMsgRequest) {

        //偶尔发现的，用户授权手机号有空白字符串
        if (StringUtils.isEmpty(sendMsgRequest.getTelephone()) || sendMsgRequest.getTelephone().trim().isEmpty()) {
            log.warn("【短信发送号码为空】", sendMsgRequest);
            return Boolean.FALSE;
        }
        Sender sender = null;

        if(smsSenderProperties.getSender().equals("aliyun")){
            sender = aliyunSender;
        } else if (smsSenderProperties.getSender().equals("xunda")) {
            sender = xunDaSender;
        } else if (smsSenderProperties.getSender().equals("buyun")) {
            sender = buYunSender;
        }else {
            throw new HzsxBizException("-1","短信发送平台配置错误");
        }
        return sender.sendSms(sendMsgRequest);
    }


}
