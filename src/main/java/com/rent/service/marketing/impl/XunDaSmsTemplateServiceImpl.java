
package com.rent.service.marketing.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rent.dao.marketing.BuyunSmsTemplateDao;
import com.rent.dao.marketing.XunDaSmsTemplateDao;
import com.rent.model.marketing.BuyunSmsTemplate;
import com.rent.model.marketing.XunDaSmsTemplate;
import com.rent.service.marketing.XunDaSmsTemplateService;
import com.rent.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 布云短信模板Service
 *
 * @author youruo
 * @Date 2020-11-06 14:30
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class XunDaSmsTemplateServiceImpl implements XunDaSmsTemplateService {

    private final XunDaSmsTemplateDao xunDaSmsTemplateDao;

    private static final String BUYUN_SMS_TEMPLATE_KEY = "XUNDA_SMS_TEMPLATE_KEY::_";

    @Override
    public XunDaSmsTemplate queryXunDaSmsTemplateDetail(String tplId) {
        String cacheKey = BUYUN_SMS_TEMPLATE_KEY + tplId;
        Object cacheObject = RedisUtil.get(cacheKey);
        if (cacheObject != null) {
            return JSON.parseObject((String) cacheObject, XunDaSmsTemplate.class);
        }
        XunDaSmsTemplate xunDaSmsTemplate = xunDaSmsTemplateDao.getOne(new QueryWrapper<XunDaSmsTemplate>().eq("tpl_id", tplId));
        if (null != xunDaSmsTemplateDao) {
            RedisUtil.set(cacheKey, JSON.toJSONString(xunDaSmsTemplate), 36000);
        }
        return xunDaSmsTemplate;
    }
}