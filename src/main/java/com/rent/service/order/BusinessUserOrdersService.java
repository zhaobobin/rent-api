package com.rent.service.order;


import com.rent.common.dto.backstage.BusinessOrderStaticsDto;
import com.rent.common.dto.order.resquest.BusinessConfirmReturnReqDto;
import com.rent.common.dto.order.resquest.BusinessIssuedStatementsReqDto;

import java.math.BigDecimal;

/**
 * 商家后台订单相关
 *
 * @author boan
 */
public interface BusinessUserOrdersService {

    /**
     * 商家发货
     * @param orderId
     * @param expressId
     * @param expressNo
     * @param operatorName
     * @param costPrice
     * @param serialNum
     * @return
     */
    String orderDelivery(String orderId, Long expressId, String expressNo, String operatorName, BigDecimal costPrice, String serialNum);

    /**
     *
     * @param orderId
     * @return
     */
    String checkOrderIsAuth(String orderId);
    /**
     * 商家结算
     *
     * @param reqDto 入参
     */
    void merchantsIssuedStatements(BusinessIssuedStatementsReqDto reqDto);
    /**
     * 商家确认归还
     * @param reqDto
     */
    void businessConfirmReturnOrder(BusinessConfirmReturnReqDto reqDto);

    /**
     * 商家订单统计
     * @param shopId
     * @return
     */
    BusinessOrderStaticsDto businessOrderStatistics(String shopId);
}
