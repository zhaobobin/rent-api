package com.rent.dao.components;


import com.rent.config.mybatis.IBaseDao;
import com.rent.model.components.AlipayTradeCreate;

/**
 * AlipayTradeAppPayDao
 *
 * @author xiaoyao
 * @Date 2020-06-24 16:11
 */
public interface AlipayTradeCreateDao extends IBaseDao<AlipayTradeCreate> {

    /**
     * 根据tradeNo查询一条记录
     * @param tradeNo
     * @return
     */
    AlipayTradeCreate getByTradeNo(String tradeNo);

}
