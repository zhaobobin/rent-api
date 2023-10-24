package com.rent.dao.components.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rent.common.enums.components.EnumAliPayStatus;
import com.rent.config.mybatis.AbstractBaseDaoImpl;
import com.rent.dao.components.AlipayTradePayDao;
import com.rent.dao.components.YfbTradePayDao;
import com.rent.mapper.components.AlipayTradePayMapper;
import com.rent.mapper.components.YfbTradePayMapper;
import com.rent.model.components.AlipayTradePay;
import com.rent.model.components.YfbTradePay;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * AlipayTradePayDao
 *
 * @author xiaoyao
 * @Date 2020-06-24 16:11
 */
@Repository
public class YfbTradePayDaoImpl extends AbstractBaseDaoImpl<YfbTradePay, YfbTradePayMapper>
    implements YfbTradePayDao {

    @Override
    public YfbTradePay getOneByTradeNo(String tradeNo, EnumAliPayStatus aliPayStatus) {
        return this.getOne(new QueryWrapper<>(YfbTradePay.builder()
            .tradeNo(tradeNo)
            .status(aliPayStatus)
            .build()));
    }

    @Override
    public YfbTradePay getByTradeNoIfAllFailed(String outTradeNo) {
        List<YfbTradePay>  list = list(new QueryWrapper<YfbTradePay>().eq("out_trade_no",outTradeNo));
        if(CollectionUtil.isEmpty(list)){
            return null;
        }
        for (YfbTradePay yfbTradePay : list) {
            if(yfbTradePay.getStatus().equals(EnumAliPayStatus.SUCCESS)){
                return null;
            }
        }
        return list.get(0);
    }

    @Override
    public int getFailedCountByOutTradeNo(String outTradeNo) {
        List<YfbTradePay> list = list(new QueryWrapper<YfbTradePay>()
                .select("id")
                .eq("out_trade_no",outTradeNo)
                .eq("status",EnumAliPayStatus.FAILED));
        return list.size();
    }
}
