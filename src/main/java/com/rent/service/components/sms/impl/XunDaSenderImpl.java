package com.rent.service.components.sms.impl;

import com.alibaba.fastjson.JSONObject;
import com.rent.common.dto.components.request.SendMsgRequest;
import com.rent.common.properties.XunDaProperties;
import com.rent.dao.components.XunDaSmsSendSerialDao;
import com.rent.exception.HzsxBizException;
import com.rent.model.components.XunDaSmsSendSerial;
import com.rent.model.marketing.XunDaSmsTemplate;
import com.rent.service.components.sms.Sender;
import com.rent.service.marketing.XunDaSmsTemplateService;
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

@Slf4j
@Service
@RequiredArgsConstructor
public class XunDaSenderImpl implements Sender {
    public static final String RT = "json";
    public static final String SEND_URL = "http://47.96.236.136:7862/sms?action=send";

    private final XunDaSmsTemplateService xunDaSmsTemplateService;
    private final XunDaSmsSendSerialDao xunDaSmsSendSerialDao;

    private final XunDaProperties xunDaProperties;

    @Override
    public boolean sendSms(SendMsgRequest sendMsgRequest) {

        XunDaSmsTemplate xunDaSmsTemplate = xunDaSmsTemplateService.queryXunDaSmsTemplateDetail(sendMsgRequest.getTemplateId());
        if (null == xunDaSmsTemplate) {
            log.warn("【短信模板消息未配置】sendMsgRequest：{}", sendMsgRequest);
            return Boolean.FALSE;
        }
        if (StringUtils.isEmpty(xunDaSmsTemplate.getAutograph()) && StringUtils.isEmpty(xunDaSmsTemplate.getContent())) {
            log.warn("【短信模板消息签名和发送内容为空】", sendMsgRequest);
            return Boolean.FALSE;
        }

        String autograph = xunDaSmsTemplate.getAutograph();
        String buYunContent = xunDaSmsTemplate.getContent();
        StringBuffer sb = new StringBuffer();
        sb.append("【").append(autograph).append("】").append(buYunContent);
        String content = sb.toString();
        if (null != sendMsgRequest.getDataMap()) {
            Map<String, Object> dataMap = sendMsgRequest.getDataMap();
            for (String key : dataMap.keySet()) {
                String data = dataMap.get(key) == null ? "" : dataMap.get(key).toString();
                content = content.replace(key, data);
            }
        }
        JSONObject resp = sendSms(sendMsgRequest.getTelephone(), content);
        if (null != resp) {
            XunDaSmsSendSerial xunDaSmsSendSerial = new XunDaSmsSendSerial();
            xunDaSmsSendSerial.setChannelId(autograph);
            xunDaSmsSendSerial.setSmsCode(sendMsgRequest.getTemplateId());
            xunDaSmsSendSerial.setCreateTime(DateUtil.getNowDate());
            xunDaSmsSendSerial.setMobile(sendMsgRequest.getTelephone());
            xunDaSmsSendSerial.setParams(content);
            xunDaSmsSendSerial.setResult(resp.toString());
            if (resp.containsKey("balance")) {
                xunDaSmsSendSerial.setBalance(resp.getString("balance"));
            }
            if (resp.containsKey("status")) {
                xunDaSmsSendSerial.setStatus(resp.getString("status"));
            }
            xunDaSmsSendSerialDao.save(xunDaSmsSendSerial);
            if (null != xunDaSmsSendSerial.getStatus() && Integer.valueOf(xunDaSmsSendSerial.getStatus()) == 15) {
                throw new HzsxBizException("-1", "迅达短信发送余额不足");
            }
        }
        return Boolean.TRUE;
    }

    //发送短信
    private JSONObject sendSms(String telephone, String content) {
        log.info("迅达开始给用户:{}发送短信，短信参数为:{}", telephone, content);
        try {
            String password = MD5.getMD5(xunDaProperties.getPassword() + xunDaProperties.getExtNo() + content + telephone);
            content = URLEncoder.encode(content, "UTF-8");
            StringBuffer sb = new StringBuffer();
            sb.append(SEND_URL)
                    .append("&account=").append(xunDaProperties.getAccount())
                    .append("&password=").append(password)
                    .append("&mobile=").append(telephone)
                    .append("&content=").append(content)
                    .append("&extno=").append(xunDaProperties.getExtNo())
                    .append("&rt=").append(RT);
            log.info("访问链接 url:{}", sb);
            Map<String, String> map = new HashMap<>();
            map.put("a", "a");
            return OkHttpUtil.postFormData(sb.toString(), map, null);
        } catch (Exception e) {
            log.error("给用户:{}短信发送异常", telephone, e);
            return null;
        }
    }
}
