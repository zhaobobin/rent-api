package com.rent.service.components.sms;

import com.alibaba.fastjson.JSONObject;
import com.rent.common.dto.components.request.SendMsgRequest;

public interface Sender {
    public boolean sendSms(SendMsgRequest sendMsgRequest);
}
