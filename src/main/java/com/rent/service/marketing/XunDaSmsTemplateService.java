
package com.rent.service.marketing;

import com.rent.model.marketing.BuyunSmsTemplate;
import com.rent.model.marketing.XunDaSmsTemplate;

/**
 * 布云短信模板Service
 *
 * @author youruo
 * @Date 2020-11-06 14:30
 */
public interface XunDaSmsTemplateService {


    /**
     * 根据tplId查询模板信息
     * @param tplId
     * @return
     */
    XunDaSmsTemplate queryXunDaSmsTemplateDetail(String tplId);

}