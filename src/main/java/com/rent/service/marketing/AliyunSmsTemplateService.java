package com.rent.service.marketing;

import com.rent.model.marketing.AliyunSmsTemplate;
import com.rent.model.marketing.BuyunSmsTemplate;

public interface AliyunSmsTemplateService {
    /**
     * 根据tplId查询模板信息
     * @param tplId
     * @return
     */
    AliyunSmsTemplate queryBuYunSmsTemplateDetail(String tplId);
}
