package com.rent.dao.marketing.impl;

import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.marketing.BuyunSmsTemplateDao;
import com.rent.dao.marketing.XunDaSmsTemplateDao;
import com.rent.mapper.marketing.BuyunSmsTemplateMapper;
import com.rent.mapper.marketing.XunDaSmsTemplateMapper;
import com.rent.model.marketing.BuyunSmsTemplate;
import com.rent.model.marketing.XunDaSmsTemplate;
import org.springframework.stereotype.Repository;

/**
 * BuyunSmsTemplateDao
 *
 * @author youruo
 * @Date 2020-11-06 14:30
 */
@Repository
public class XunDaSmsTemplateDaoImpl extends AbstractBaseDaoImpl<XunDaSmsTemplate, XunDaSmsTemplateMapper>
    implements XunDaSmsTemplateDao {

}
