package com.rent.dao.components;

import com.rent.common.enums.components.EnumAliPayStatus;
import com.rent.config.mybatis.IBaseDao;
import com.rent.model.components.AlipayTradePay;
import com.rent.model.components.YfbTradePay;

/**
 * AlipayTradePayDao
 *
 * @author xiaoyao
 * @Date 2020-06-24 16:11
 */
public interface YfbTradePayDao extends IBaseDao<YfbTradePay> {

    /**
     * 根据支付宝交易号查询一条记录
     *
     * @param tradeNo 支付宝交易号
     * @param aliPayStatus
     * @return 支付记录
     */
    YfbTradePay getOneByTradeNo(String tradeNo, EnumAliPayStatus aliPayStatus);



    /**
     * 如果没有代扣成功的信息，就返回一个记录。否则返回null
     * 用来同步代扣信息的。
     * @param outTradeNo
     * @return
     */
    YfbTradePay getByTradeNoIfAllFailed(String outTradeNo);


    /**
     * 根据外部订单号查询失败次数
     * @param outTradeNo
     * @return
     */
    int getFailedCountByOutTradeNo(String outTradeNo);

}
