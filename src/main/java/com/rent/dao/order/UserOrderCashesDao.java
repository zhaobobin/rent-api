package com.rent.dao.order;

import com.rent.common.dto.components.dto.AlipayTradeDiscountDto;
import com.rent.config.mybatis.IBaseDao;
import com.rent.model.order.UserOrderCashes;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * UserOrderCashesDao
 *
 * @author xiaoyao
 * @Date 2020-06-15 17:08
 */
public interface UserOrderCashesDao extends IBaseDao<UserOrderCashes> {

    /**
     * 根据订单编号查询是否存在记录
     *
     * @param orderId 订单编号
     * @return 是否存在记录
     */
    boolean checkExitsByOrderId(String orderId);

    /**
     * 根据订单id查询订单金额
     *
     * @param orderId 订单id
     * @return 订单金额信息
     */
    UserOrderCashes selectOneByOrderId(String orderId);

    /**
     * 根据订单id列表查询金额信息
     *
     * @param orderIdList Map<orderId,UserOrderCashes>
     * @return
     */
    Map<String, UserOrderCashes> queryListByOrderIds(List<String> orderIdList);

    /**
     * 根据订单id列表查询金额信息
     *
     * @param orderIdList Map<orderId,UserOrderCashes>
     * @return
     */
    List<UserOrderCashes> queryByOrderIds(List<String> orderIdList);

    /**
     * 修复订单数据
     * @return
     */
    Boolean repairOrderCashes(AlipayTradeDiscountDto tradeDiscountDto);

    BigDecimal getAllOrdersTotolRent(List<String> orderIds);
}
