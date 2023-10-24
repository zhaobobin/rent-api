package com.rent.dao.components;

import com.rent.config.mybatis.IBaseDao;
import com.rent.model.components.AlipayTradeRefund;

/**
 * AlipayTradeRefundDao
 *
 * @author xiaoyao
 * @Date 2020-07-02 10:57
 */
public interface AlipayTradeRefundDao extends IBaseDao<AlipayTradeRefund> {


    AlipayTradeRefund getAlipayTradeRefundByTradeNo(String tradeNo);
}
