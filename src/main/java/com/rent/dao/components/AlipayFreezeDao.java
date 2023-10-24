package com.rent.dao.components;

import com.rent.common.enums.components.EnumAliPayStatus;
import com.rent.config.mybatis.IBaseDao;
import com.rent.model.components.AlipayFreeze;

/**
 * AlipayFreezeDao
 *
 * @author xiaoyao
 * @Date 2020-06-24 16:11
 */
public interface AlipayFreezeDao extends IBaseDao<AlipayFreeze> {

    /**
     * 根据支付宝交易号查询一条记录
     *
     * @param outRequestNo 请求流水号
     * @return
     */
    AlipayFreeze selectOneByOutRequestNo(String outRequestNo);

    /**
     * 根据
     *
     * @param orderId 订单编号
     * @param status 状态
     * @return 冻结记录
     */
    AlipayFreeze selectOneByOrderId(String orderId, EnumAliPayStatus status);

}
