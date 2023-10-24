package com.rent.service.components.impl;

import com.rent.common.constant.SmsConfigConstants;
import com.rent.common.dto.components.dto.SendMsgDto;
import com.rent.common.dto.components.request.SendMsgRequest;
import com.rent.service.components.SendBuYunSmsService;
import com.rent.service.components.SendSmsService;
import com.rent.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xiaoyao
 * @version V1.0
 * @date 2020-7-20 下午 5:13:19
 * @since 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SendSmsServiceImpl implements SendSmsService {

    private final SendBuYunSmsService sendBuYunSmsService;

    @Override
    public Boolean realName(SendMsgDto sendMsgDto) {
        Map<String, Object> data = new HashMap<>();
        data.put("#code#", sendMsgDto.getMsgCode());
        SendMsgRequest sendMsgRequest = new SendMsgRequest();
        sendMsgRequest.setTelephone(sendMsgDto.getTelephone());
        sendMsgRequest.setTemplateId(SmsConfigConstants.REAL_NAME);
        sendMsgRequest.setDataMap(data);
        Boolean result = sendBuYunSmsService.componentSend(sendMsgRequest);
        return result;
    }

    @Override
    public Boolean businessDelivery(SendMsgDto sendMsgDto) {
        Map<String, Object> data = new HashMap<>();
        data.put("#productName#", sendMsgDto.getProductName());
        data.put("#logisticsName#", sendMsgDto.getLogisticsName());
        data.put("#expressNo#", sendMsgDto.getExpressNo());
        SendMsgRequest sendMsgRequest = new SendMsgRequest();
        sendMsgRequest.setTelephone(sendMsgDto.getTelephone());
        sendMsgRequest.setTemplateId(SmsConfigConstants.BUSINESS_DELIVERY);
        sendMsgRequest.setDataMap(data);
        Boolean result = sendBuYunSmsService.componentSend(sendMsgRequest);
        return result;
    }

    @Override
    public Boolean closeOrderBySms(SendMsgDto sendMsgDto) {
        Map<String, Object> data = new HashMap<>();
        data.put("#orderNo#", sendMsgDto.getOrderId().substring(sendMsgDto.getOrderId().length() - 6));
        data.put("#serviceTel#", sendMsgDto.getShopServiceTel());
        SendMsgRequest sendMsgRequest = new SendMsgRequest();
        sendMsgRequest.setTelephone(sendMsgDto.getTelephone());
        String key = SmsConfigConstants.ORDER_PAY_PLANT_CLOSE;
        sendMsgRequest.setTemplateId(key);
        sendMsgRequest.setDataMap(data);
        Boolean result = sendBuYunSmsService.componentSend(sendMsgRequest);
        return result;
    }

    @Override
    public Boolean payedOrder(SendMsgDto sendMsgDto) {
        Map<String, Object> data = new HashMap<>();
        data.put("#orderId#", sendMsgDto.getOrderId().substring(sendMsgDto.getOrderId().length() - 6));
        data.put("#serviceTel#", sendMsgDto.getShopServiceTel());
        SendMsgRequest sendMsgRequest = new SendMsgRequest();
        sendMsgRequest.setTelephone(sendMsgDto.getTelephone());
        String key = SmsConfigConstants.ORDER_PAY_SUCCESS;
        sendMsgRequest.setTemplateId(key);
        sendMsgRequest.setDataMap(data);
        Boolean result = sendBuYunSmsService.componentSend(sendMsgRequest);
        return result;
    }

    @Override
    public Boolean payedBuyOutOrder(SendMsgDto sendMsgDto) {
        Map<String, Object> data = new HashMap<>();
        data.put("#orderId#", sendMsgDto.getOrderId().substring(sendMsgDto.getOrderId().length()-6));

        SendMsgRequest sendMsgRequest = new SendMsgRequest();
        sendMsgRequest.setTelephone(sendMsgDto.getTelephone());
        sendMsgRequest.setTemplateId(SmsConfigConstants.ORDER_BUY_OUT_SUCCESS);
        sendMsgRequest.setDataMap(data);
        Boolean result = sendBuYunSmsService.componentSend(sendMsgRequest);
        return result;
    }

    @Override
    public Boolean payedBuyOutOrderToBusiness(String userName, String buyOutOrderId, String phone, Date createDate) {
        Map<String, Object> businessData = new HashMap<>();
        businessData.put("#userName#", userName);
        businessData.put("#buyOutOrderId#", buyOutOrderId);
        businessData.put("#createDate#", DateUtil.date2String(createDate));
        SendMsgRequest sendMsgShop = new SendMsgRequest();
        sendMsgShop.setTelephone(phone);
        sendMsgShop.setDataMap(businessData);
        sendMsgShop.setTemplateId(SmsConfigConstants.ORDER_BUSINESS_BUY_OUT_SUCCESS);
        return sendBuYunSmsService.componentSend(sendMsgShop);
    }

    @Override
    public Boolean payFailOrder(SendMsgDto sendMsgDto) {
        Map<String, Object> data = new HashMap<>();
        data.put("#orderId#", sendMsgDto.getOrderId().substring(sendMsgDto.getOrderId().length() - 6));
        SendMsgRequest sendMsgRequest = new SendMsgRequest();
        sendMsgRequest.setTelephone(sendMsgDto.getTelephone());
        sendMsgRequest.setTemplateId(SmsConfigConstants.ORDER_PAY_FAIL_CLOSE);
        sendMsgRequest.setDataMap(data);
        Boolean result = sendBuYunSmsService.componentSend(sendMsgRequest);
        return result;
    }

    @Override
    public Boolean platformCloseOrder(SendMsgDto sendMsgDto) {
        Map<String, Object> data = new HashMap<>();
        data.put("#orderNo#", sendMsgDto.getOrderId().substring(sendMsgDto.getOrderId().length() - 6));
        data.put("#serviceTel#", sendMsgDto.getShopServiceTel());
        SendMsgRequest sendMsgRequest = new SendMsgRequest();
        sendMsgRequest.setTelephone(sendMsgDto.getTelephone());
        sendMsgRequest.setTemplateId(SmsConfigConstants.ORDER_PALT_CLOSE);
        sendMsgRequest.setDataMap(data);
        Boolean result = sendBuYunSmsService.componentSend(sendMsgRequest);
        return result;
    }

    @Override
    public Boolean riskCloseOrder(SendMsgDto sendMsgDto) {
        Map<String, Object> data = new HashMap<>();
        data.put("#orderId#", sendMsgDto.getOrderId().substring(sendMsgDto.getOrderId().length() - 6));
        data.put("#serviceTel#", sendMsgDto.getShopServiceTel());
        SendMsgRequest sendMsgRequest = new SendMsgRequest();
        sendMsgRequest.setTelephone(sendMsgDto.getTelephone());
        sendMsgRequest.setTemplateId(SmsConfigConstants.RISK_CLOSE);
        sendMsgRequest.setDataMap(data);
        Boolean result = sendBuYunSmsService.componentSend(sendMsgRequest);
        return result;
    }

    @Override
    public Boolean payedReletOrder(SendMsgDto sendMsgDto) {
        Map<String, Object> data = new HashMap<>();
        data.put("#serviceTel#", sendMsgDto.getShopServiceTel());
        SendMsgRequest sendMsgRequest = new SendMsgRequest();
        sendMsgRequest.setTelephone(sendMsgDto.getTelephone());
        sendMsgRequest.setTemplateId(SmsConfigConstants.RELET_ORDER_PAY_SUCCESS);
        sendMsgRequest.setDataMap(data);
        Boolean result = sendBuYunSmsService.componentSend(sendMsgRequest);
        return result;
    }

    @Override
    public Boolean payedReletBusiness(SendMsgDto sendMsgDto) {
        Map<String, Object> data = new HashMap<>();
        data.put("#userName#", sendMsgDto.getUserName());
        data.put("#OrderId#", sendMsgDto.getOrderId().substring(sendMsgDto.getOrderId().length()-6));
        data.put("#ReletOrderId#", sendMsgDto.getReletOrderId().substring(sendMsgDto.getReletOrderId().length()-6));
        SendMsgRequest sendMsgRequest = new SendMsgRequest();
        sendMsgRequest.setTelephone(sendMsgDto.getTelephone());
        sendMsgRequest.setTemplateId(SmsConfigConstants.RELET_TO_BUSINESS);
        sendMsgRequest.setDataMap(data);
        Boolean result = sendBuYunSmsService.componentSend(sendMsgRequest);
        return result;
    }

    @Override
    public Boolean businessUpdatePassword(SendMsgDto sendMsgDto) {
        Map<String, Object> data = new HashMap<>();
        data.put("#code#", sendMsgDto.getCode());
        SendMsgRequest sendMsgRequest = new SendMsgRequest();
        sendMsgRequest.setTelephone(sendMsgDto.getTelephone());
        sendMsgRequest.setTemplateId(SmsConfigConstants.BUSINESS_UPDATE_PASSWORD);
        sendMsgRequest.setDataMap(data);
        Boolean result = sendBuYunSmsService.componentSend(sendMsgRequest);
        return result;
    }
}
