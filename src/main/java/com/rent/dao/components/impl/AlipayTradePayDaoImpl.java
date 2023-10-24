package com.rent.dao.components.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rent.common.enums.components.EnumAliPayStatus;
import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.components.AlipayTradePayDao;
import com.rent.mapper.components.AlipayTradePayMapper;
import com.rent.model.components.AlipayTradePay;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * AlipayTradePayDao
 *
 * @author xiaoyao
 * @Date 2020-06-24 16:11
 */
@Repository
public class AlipayTradePayDaoImpl extends AbstractBaseDaoImpl<AlipayTradePay, AlipayTradePayMapper>
    implements AlipayTradePayDao {

    @Override
    public AlipayTradePay getOneByTradeNo(String tradeNo, EnumAliPayStatus aliPayStatus) {
        return this.getOne(new QueryWrapper<>(AlipayTradePay.builder()
            .tradeNo(tradeNo)
            .status(aliPayStatus)
            .build()));
    }

    @Override
    public AlipayTradePay getByTradeNoIfAllFailed(String outTradeNo) {
        List<AlipayTradePay>  list = list(new QueryWrapper<AlipayTradePay>().eq("out_trade_no",outTradeNo));
        if(CollectionUtil.isEmpty(list)){
            return null;
        }
        for (AlipayTradePay alipayTradePay : list) {
            if(alipayTradePay.getStatus().equals(EnumAliPayStatus.SUCCESS)){
                return null;
            }
        }
        return list.get(0);
    }

    @Override
    public int getFailedCountByOutTradeNo(String outTradeNo) {
        List<AlipayTradePay> list = list(new QueryWrapper<AlipayTradePay>()
                .select("id")
                .eq("out_trade_no",outTradeNo)
                .eq("status",EnumAliPayStatus.FAILED));
        return list.size();
    }
}
