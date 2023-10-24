package com.rent.dao.components;

import com.rent.config.mybatis.IBaseDao;
import com.rent.model.components.AlipayTradeAppPay;

/**
 * AlipayTradeAppPayDao
 *
 * @author xiaoyao
 * @Date 2020-06-24 16:11
 */
public interface AlipayTradeAppPayDao extends IBaseDao<AlipayTradeAppPay> {


    AlipayTradeAppPay getTtfTradeAppPayByTradeNo(String tradeNo);


}
