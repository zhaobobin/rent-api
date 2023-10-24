package com.rent.dao.components.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.components.YfbTradeRefundDao;
import com.rent.mapper.components.YfbTradeRefundMapper;
import com.rent.model.components.YfbTradeRefund;
import org.springframework.stereotype.Repository;

/**
 * AlipayTradeRefundDao
 *
 * @author xiaoyao
 * @Date 2020-07-02 10:57
 */
@Repository
public class YfbTradeRefundDaoImpl extends AbstractBaseDaoImpl<YfbTradeRefund, YfbTradeRefundMapper>
        implements YfbTradeRefundDao {

    @Override
    public YfbTradeRefund getYfbTradeRefundByTradeNo(String tradeNo) {
        return getOne(new QueryWrapper<YfbTradeRefund>().eq("trade_no", tradeNo).orderByDesc("id").last("limit 1"));
    }
}
