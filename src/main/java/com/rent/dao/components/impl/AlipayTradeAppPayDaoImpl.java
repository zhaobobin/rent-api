package com.rent.dao.components.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.components.AlipayTradeAppPayDao;
import com.rent.mapper.components.AlipayTradeAppPayMapper;
import com.rent.model.components.AlipayTradeAppPay;
import org.springframework.stereotype.Repository;

/**
 * AlipayTradeAppPayDao
 *
 * @author xiaoyao
 * @Date 2020-06-24 16:11
 */
@Repository
public class AlipayTradeAppPayDaoImpl extends AbstractBaseDaoImpl<AlipayTradeAppPay, AlipayTradeAppPayMapper>
        implements AlipayTradeAppPayDao {

    @Override
    public AlipayTradeAppPay getTtfTradeAppPayByTradeNo(String tradeNo) {
        return getOne(new QueryWrapper<AlipayTradeAppPay>().eq("trade_no", tradeNo).orderByDesc("id").last("limit 1"));
    }
}
