package com.rent.dao.marketing.impl;

import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.marketing.AliyunSmsTemplateDao;
import com.rent.dao.marketing.BuyunSmsTemplateDao;
import com.rent.mapper.marketing.AliyunSmsTemplateMapper;
import com.rent.model.marketing.AliyunSmsTemplate;
import org.springframework.stereotype.Repository;

/**
 * BuyunSmsTemplateDao
 *
 * @author youruo
 * @Date 2020-11-06 14:30
 */
@Repository
public class AliyunSmsTemplateDaoImpl extends AbstractBaseDaoImpl<AliyunSmsTemplate, AliyunSmsTemplateMapper>
    implements AliyunSmsTemplateDao {

}
