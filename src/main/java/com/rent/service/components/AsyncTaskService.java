package com.rent.service.components;

import com.rent.common.enums.components.EnumTradeType;
import com.rent.model.components.AlipayFreeze;

public interface AsyncTaskService {

    /**
     * 预授权转支付
     *
     * @param alipayFreeze 冻结信息
     * @param tradeType 订单类型
     */
    void alipayTradePay(AlipayFreeze alipayFreeze, EnumTradeType tradeType);

}
