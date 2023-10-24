package com.rent.dao.components.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.components.AlipayTradeRefundDao;
import com.rent.mapper.components.AlipayTradeRefundMapper;
import com.rent.model.components.AlipayTradeRefund;
import org.springframework.stereotype.Repository;

/**
 * AlipayTradeRefundDao
 *
 * @author xiaoyao
 * @Date 2020-07-02 10:57
 */
@Repository
public class AlipayTradeRefundDaoImpl extends AbstractBaseDaoImpl<AlipayTradeRefund, AlipayTradeRefundMapper>
        implements AlipayTradeRefundDao {

    @Override
    public AlipayTradeRefund getAlipayTradeRefundByTradeNo(String tradeNo) {
        return getOne(new QueryWrapper<AlipayTradeRefund>().eq("trade_no", tradeNo).orderByDesc("id").last("limit 1"));
    }
}
