package com.rent.dao.components;

import com.rent.common.enums.components.EnumAliPayStatus;
import com.rent.common.enums.components.EnumPayType;
import com.rent.common.enums.components.EnumTradeType;
import com.rent.config.mybatis.IBaseDao;
import com.rent.model.components.AlipayTradeSerial;
import com.rent.model.components.YfbTradeSerial;

/**
 * AlipayTradeSerialDao
 *
 * @author xiaoyao
 * @Date 2020-06-24 16:11
 */
public interface YfbTradeSerialDao extends IBaseDao<YfbTradeSerial> {

    /**
     * 根据请求流水号查询一条交易流水
     *
     * @param serialNo
     * @param aliPayStatus
     * @return
     */
    YfbTradeSerial selectOneBySerialNo(String serialNo, EnumAliPayStatus aliPayStatus);

    YfbTradeSerial selectOneBySerialNoByOrderId(String orderId, EnumAliPayStatus aliPayStatus, EnumTradeType tradeType, EnumPayType payType);


    /**
     * 根据请求流水号查询一条交易流水
     *
     * @param serialNo
     * @return
     */
    YfbTradeSerial selectOneBySerialNo(String serialNo);
}
