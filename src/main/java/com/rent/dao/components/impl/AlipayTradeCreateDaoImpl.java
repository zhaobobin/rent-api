package com.rent.dao.components.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.components.AlipayTradeCreateDao;
import com.rent.mapper.components.AlipayTradeCreateMapper;
import com.rent.model.components.AlipayTradeCreate;
import org.springframework.stereotype.Repository;

/**
 * AlipayTradeAppPayDao
 *
 * @author xiaoyao
 * @Date 2020-06-24 16:11
 */
@Repository
public class AlipayTradeCreateDaoImpl extends AbstractBaseDaoImpl<AlipayTradeCreate, AlipayTradeCreateMapper> implements AlipayTradeCreateDao {

    @Override
    public AlipayTradeCreate getByTradeNo(String tradeNo) {
        AlipayTradeCreate alipayTradeCreate = getOne(new QueryWrapper<AlipayTradeCreate>().eq("trade_no",tradeNo));
        return alipayTradeCreate;
    }
}
