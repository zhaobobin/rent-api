package com.rent.dao.components;

import com.rent.config.mybatis.IBaseDao;
import com.rent.model.components.AlipayTradeRefund;
import com.rent.model.components.YfbTradeRefund;

/**
 * AlipayTradeRefundDao
 *
 * @author xiaoyao
 * @Date 2020-07-02 10:57
 */
public interface YfbTradeRefundDao extends IBaseDao<YfbTradeRefund> {


    YfbTradeRefund getYfbTradeRefundByTradeNo(String tradeNo);
}
