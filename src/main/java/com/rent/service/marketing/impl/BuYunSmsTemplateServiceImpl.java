
package com.rent.service.marketing.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rent.dao.marketing.BuyunSmsTemplateDao;
import com.rent.model.marketing.BuyunSmsTemplate;
import com.rent.service.marketing.BuYunSmsTemplateService;
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
public class BuYunSmsTemplateServiceImpl implements BuYunSmsTemplateService {

    private final BuyunSmsTemplateDao buyunSmsTemplateDao;

    private static final String BUYUN_SMS_TEMPLATE_KEY = "BUYUN_SMS_TEMPLATE_KEY::_";

    @Override
    public BuyunSmsTemplate queryBuYunSmsTemplateDetail(String tplId) {
        String cacheKey = BUYUN_SMS_TEMPLATE_KEY + tplId;
        Object cacheObject = RedisUtil.get(cacheKey);
        if (cacheObject != null) {
            return JSON.parseObject((String) cacheObject, BuyunSmsTemplate.class);
        }
        BuyunSmsTemplate buyunSmsTemplate = buyunSmsTemplateDao.getOne(new QueryWrapper<BuyunSmsTemplate>().eq("tpl_id",tplId));
        if (null != buyunSmsTemplate) {
            RedisUtil.set(cacheKey, JSON.toJSONString(buyunSmsTemplate), 36000);
        }
        return buyunSmsTemplate;
    }
}