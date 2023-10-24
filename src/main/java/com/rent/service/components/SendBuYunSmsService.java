package com.rent.service.components;

import com.rent.common.dto.components.request.SendMsgRequest;

/**
 * @author xiaoyao
 * @version V1.0
 * @date 2020-7-20 下午 5:13:02
 * @since 1.0
 */
public interface SendBuYunSmsService {
    /**
     * 通用发送短信
     * @param sendMsgRequest 请求
     * @return 结果
     */
    Boolean componentSend(SendMsgRequest sendMsgRequest) ;

}
