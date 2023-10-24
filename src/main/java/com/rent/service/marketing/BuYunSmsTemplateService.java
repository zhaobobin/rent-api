
package com.rent.service.marketing;

import com.rent.model.marketing.BuyunSmsTemplate;

/**
 * 布云短信模板Service
 *
 * @author youruo
 * @Date 2020-11-06 14:30
 */
public interface BuYunSmsTemplateService {


    /**
     * 根据tplId查询模板信息
     * @param tplId
     * @return
     */
    BuyunSmsTemplate queryBuYunSmsTemplateDetail(String tplId);

}