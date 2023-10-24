package com.rent.service.components.sms.impl;

import com.alibaba.fastjson.JSON;
import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.dysmsapi20170525.models.SendSmsResponseBody;
import com.aliyun.tea.TeaException;
import com.aliyun.teaopenapi.models.Config;
import com.rent.common.dto.components.request.SendMsgRequest;
import com.rent.common.properties.SmsAliyunProperties;
import com.rent.dao.components.AliyunSmsSendSerialDao;
import com.rent.exception.HzsxBizException;
import com.rent.model.components.AliyunSmsSendSerial;
import com.rent.model.marketing.AliyunSmsTemplate;
import com.rent.service.components.sms.Sender;
import com.rent.service.marketing.AliyunSmsTemplateService;
import com.rent.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class AliyunSenderImpl implements Sender {
    private final SmsAliyunProperties smsAliyunProperties;
    private final AliyunSmsTemplateService aliyunSmsTemplateService;
    private final AliyunSmsSendSerialDao aliyunSmsSendSerialDao;


    @Override
    public boolean sendSms(SendMsgRequest sendMsgRequest) {

        AliyunSmsTemplate aliyunSmsTemplate = aliyunSmsTemplateService.queryBuYunSmsTemplateDetail(sendMsgRequest.getTemplateId());
        if (null == aliyunSmsTemplate) {
            log.warn("【短信模板消息未配置】sendMsgRequest：{}", sendMsgRequest);
            return Boolean.FALSE;
        }
        if (StringUtils.isEmpty(aliyunSmsTemplate.getAutograph())
                && StringUtils.isEmpty(aliyunSmsTemplate.getContent())
                && StringUtils.isEmpty(aliyunSmsTemplate.getTplCode())
        ) {
            log.warn("【短信模板消息签名和发送内容为空】", sendMsgRequest);
            return Boolean.FALSE;
        }

        try {

            Config config = new Config()
                    // 必填，您的 AccessKey ID
                    .setAccessKeyId(smsAliyunProperties.getAccessKey())
                    // 必填，您的 AccessKey Secret
                    .setAccessKeySecret(smsAliyunProperties.getAccessSecret());
            config.endpoint = "dysmsapi.aliyuncs.com";

            // 请确保代码运行环境设置了环境变量 ALIBABA_CLOUD_ACCESS_KEY_ID 和 ALIBABA_CLOUD_ACCESS_KEY_SECRET。
            // 工程代码泄露可能会导致 AccessKey 泄露，并威胁账号下所有资源的安全性。以下代码示例使用环境变量获取 AccessKey 的方式进行调用，仅供参考，建议使用更安全的 STS 方式，更多鉴权访问方式请参见：https://help.aliyun.com/document_detail/378657.html
            Client client = new Client(config);
            Map<String, Object> paramsMap = new HashMap<>();
            sendMsgRequest.getDataMap().forEach((k1, v1) -> {
                k1 = k1.replace("#", "");
                paramsMap.put(k1, v1);
            });
            String string = JSON.toJSONString(paramsMap);

            SendSmsRequest sendSmsRequest = new SendSmsRequest()
                    .setSignName(aliyunSmsTemplate.getAutograph())
                    .setTemplateCode(aliyunSmsTemplate.getTplCode())
                    .setPhoneNumbers(sendMsgRequest.getTelephone())
                    .setTemplateParam(string);
            com.aliyun.teautil.models.RuntimeOptions runtime = new com.aliyun.teautil.models.RuntimeOptions();

            // 复制代码运行请自行打印 API 的返回值
            SendSmsResponse resp = client.sendSmsWithOptions(sendSmsRequest, runtime);
            SendSmsResponseBody body = resp.getBody();

            if (body == null) {
                throw new HzsxBizException("-1", "阿里云短信发送异常");
            }

            String autograph = aliyunSmsTemplate.getAutograph();
            String buYunContent = aliyunSmsTemplate.getContent();
            StringBuffer sb = new StringBuffer();
            sb.append("【").append(autograph).append("】").append(buYunContent);
            String content = sb.toString();
            if (null != sendMsgRequest.getDataMap()) {
                Map<String, Object> dataMap = sendMsgRequest.getDataMap();
                for (String key : dataMap.keySet()) {
                    String k2 = "${" + key.replace("#", "") + "}";
                    String data = dataMap.get(key) == null ? "" : dataMap.get(key).toString();
                    content = content.replace(k2, data);
                }
            }

            // 记录发送结果
            if (null != body && body.getBizId() != null) {
                AliyunSmsSendSerial aliyunSmsSendSerial = new AliyunSmsSendSerial();
                aliyunSmsSendSerial.setChannelId(autograph);
                aliyunSmsSendSerial.setSmsCode(sendMsgRequest.getTemplateId());
                aliyunSmsSendSerial.setCreateTime(DateUtil.getNowDate());
                aliyunSmsSendSerial.setMobile(sendMsgRequest.getTelephone());
                aliyunSmsSendSerial.setParams(content);
                aliyunSmsSendSerial.setResult(body.getRequestId());
                aliyunSmsSendSerial.setStatus(body.getCode());
                aliyunSmsSendSerial.setBizId(body.getBizId());
                aliyunSmsSendSerialDao.save(aliyunSmsSendSerial);
                if (!body.getCode().equals("OK")) {
                    throw new HzsxBizException("-1", "阿里云短信发送异常:" + body.getMessage());
                }
            } else {
                throw new HzsxBizException("-1", "阿里云短信发送异常:" + body.getMessage());
            }

        } catch (TeaException error) {
            // 如有需要，请打印 error
            com.aliyun.teautil.Common.assertAsString(error.message);
            return Boolean.FALSE;
        } catch (Exception _error) {
            TeaException error = new TeaException(_error.getMessage(), _error);
            // 如有需要，请打印 error
            com.aliyun.teautil.Common.assertAsString(error.message);
            log.warn("【阿里云短信发送异常】", _error.getMessage());
            return Boolean.FALSE;
        }

        return Boolean.TRUE;
    }
}
