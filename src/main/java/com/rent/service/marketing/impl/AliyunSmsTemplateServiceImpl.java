package com.rent.service.marketing.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rent.dao.marketing.AliyunSmsTemplateDao;
import com.rent.dao.marketing.BuyunSmsTemplateDao;
import com.rent.model.marketing.AliyunSmsTemplate;
import com.rent.model.marketing.BuyunSmsTemplate;
import com.rent.service.marketing.AliyunSmsTemplateService;
import com.rent.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AliyunSmsTemplateServiceImpl implements AliyunSmsTemplateService {
    private final AliyunSmsTemplateDao aliyunSmsTemplateDao;

    private static final String BUYUN_SMS_TEMPLATE_KEY = "ALIYUN_SMS_TEMPLATE_KEY::_";

    @Override
    public AliyunSmsTemplate queryBuYunSmsTemplateDetail(String tplId) {
        String cacheKey = BUYUN_SMS_TEMPLATE_KEY + tplId;
        Object cacheObject = RedisUtil.get(cacheKey);
        if (cacheObject != null) {
            return JSON.parseObject((String) cacheObject, AliyunSmsTemplate.class);
        }
        AliyunSmsTemplate aliyunSmsTemplate = aliyunSmsTemplateDao.getOne(new QueryWrapper<AliyunSmsTemplate>().eq("tpl_id",tplId));
        if (null != aliyunSmsTemplate) {
            RedisUtil.set(cacheKey, JSON.toJSONString(aliyunSmsTemplate), 36000);
        }
        return aliyunSmsTemplate;
    }
}
